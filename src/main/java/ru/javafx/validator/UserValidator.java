
package ru.javafx.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javafx.entity.User;
import ru.javafx.mail.RegistrationMailService;
import ru.javafx.multitenant.MultitenantCreateService;
import ru.javafx.repository.UserRepository;
import ru.javafx.service.UserService;

@Component
public class UserValidator extends BaseRequestValidator {

    private final int MAX_COUNT_IP_FOR_USER = 200;    
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegistrationMailService registrationMailService;
    @Autowired
    private MultitenantCreateService multitenantCreateService;
    
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }
    
    @Override
    protected void validatePostRequest(Object target, Errors errors) {
        //logger.info("validate user Post: {}", target);
        
        User user = (User) target;         
        user.setIp(request.getRemoteAddr());
       
        if (userService.isUserExist(user)) {
            errors.rejectValue("username", "error.user.username.duplicate");
        }
        if (userService.isMailExist(user)) {
            errors.rejectValue("mail", "error.user.mail.duplicate");
        }
        if (userService.findUsersByIp(user).size() > MAX_COUNT_IP_FOR_USER) {
            errors.rejectValue("ip", "error.user.ip.duplicate");
        }
        
        if (!errors.hasErrors()) {       
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            // изменить после подтверждения регистрации по почте
            user.setEnabled(false);           
            user.getAuthorities().add(userService.getAuthority("USER"));            
            userService.save(user);
            
            registrationMailService.sendHtmlMessage(user);
            //registrationMailService.sendSimpleMessage(user);           
                    
            multitenantCreateService.createTenant(user.getId());
        }  
    }
    
    @Override
    protected void validatePutRequest(Object target, Errors errors) {
        //logger.info("validate user Put: {}", target);
        
        User user = (User) target;
        User newUser = userRepository.findByUsername(user.getUsername());
        if (newUser != null && !user.getId().equals(newUser.getId())) {
            errors.rejectValue("username", "error.user.username.duplicate");
        }
        newUser = userRepository.findByMail(user.getMail());
        if (newUser != null && !user.getId().equals(newUser.getId())) {
            errors.rejectValue("mail", "error.user.mail.duplicate");
        }
        if (!errors.hasErrors()) {
            userService.save(user);
        }
        
    }
    
    @Override
    protected void validateDeleteRequest(Object target, Errors errors) {
        
    }
}
