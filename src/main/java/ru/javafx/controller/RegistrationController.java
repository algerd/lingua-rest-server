
package ru.javafx.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
@PropertySources({
	@PropertySource(value="classpath:mail.properties", ignoreResourceNotFound=true),
	@PropertySource(value="classpath:config/mail.properties", ignoreResourceNotFound=true)
})
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
     
    @RequestMapping(value = "/${mail.verification-link}/{token}", method = RequestMethod.GET)
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
    /*
    @RequestMapping(value = "/user/resendRegistrationToken/{token}", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse resendRegistrationToken(@PathVariable("token") String token) {
        VerificationToken newToken = userService.generateNewVerificationToken(token);

        User user = userService.getUser(newToken.getToken());
        String appUrl = 
          "http://" + request.getServerName() + 
          ":" + request.getServerPort() + 
          request.getContextPath();
        SimpleMailMessage email = 
          constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
        mailSender.send(email);

        return new GenericResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
    }
    */
}
