
package ru.javafx.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="verification_token")
public class VerificationToken implements Serializable  {
    
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
	private Long id;
    
    @Column(name="token")
    private String token;
    
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "users_id")
    private User user;
    
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate = LocalDateTime.now();
    
    public VerificationToken() {       
    }
    
    public VerificationToken(String token, User user) {     
        this.token = token;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "VerificationToken{" + "id=" + id + ", token=" + token + ", user=" + user + ", expiry_date=" + expiryDate + '}';
    }
        
}
