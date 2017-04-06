package ru.javafx.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import ru.javafx.entity.Authority;
import ru.javafx.entity.User;
import ru.javafx.entity.VerificationToken;

@Validated
public interface UserService extends UserDetailsService {

    @Override
    User loadUserByUsername(String username);

    void save(User user);
    
    Authority getAuthority(String authority);
    
    boolean isUserExist(User user);
    
    boolean isMailExist(User user);
    
    List<User> findUsersByIp(User user);
    
    User findUserByToken(String verificationToken);
    
    String createVerificationToken(User user);
 
    VerificationToken getVerificationToken(String VerificationToken);
     
}
