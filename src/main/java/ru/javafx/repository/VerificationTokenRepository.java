
package ru.javafx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javafx.entity.User;
import ru.javafx.entity.VerificationToken;

@Transactional
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);   
    
}
