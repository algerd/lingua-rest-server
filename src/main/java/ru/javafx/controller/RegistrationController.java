
package ru.javafx.controller;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.javafx.entity.User;
import ru.javafx.entity.VerificationToken;
import ru.javafx.multitenant.MultitenantCreateService;
import ru.javafx.repository.UserRepository;
import ru.javafx.repository.VerificationTokenRepository;
import ru.javafx.service.UserService;

@RestController
public class RegistrationController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MultitenantCreateService multitenantCreateService;
    
    @RequestMapping(value = "/confirmRegistration/{token}", method = RequestMethod.GET)
    public ResponseEntity<String> confirmRegistration(@PathVariable("token") String token) {
        
        Locale locale = LocaleContextHolder.getLocale(); 
        String message;
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            message = messageSource.getMessage("error.auth.invalidToken", null, "error.auth.invalidToken", locale);
        }      
        else if (verificationToken.getExpiryDate().minusDays(1).isAfter(verificationToken.getExpiryDate())) {
            message = messageSource.getMessage("error.auth.expired", null, "error.auth.expired", locale);
            verificationTokenRepository.delete(verificationToken);
        }
        else {          
            User user = verificationToken.getUser();
            if (!user.isEnabled()) {
                user.setEnabled(true); 
                userRepository.save(user);
                multitenantCreateService.createTenant(user.getId());
                verificationTokenRepository.delete(verificationToken);             
            }  
            message = messageSource.getMessage("info.auth.success", null, "info.auth.success", locale);
        }           
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
