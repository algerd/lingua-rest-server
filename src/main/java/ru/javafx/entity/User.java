package ru.javafx.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;

@Entity
@Table(
    name="users",    
    uniqueConstraints = {@UniqueConstraint(name = "uk_username", columnNames = "username")
})
public class User implements UserDetails, CredentialsContainer, Cloneable {

    private static final long serialVersionUID = 1L;  
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    @NotNull(message = "error.user.username.empty")
    @Size(min = 4, max = 64, message = "error.user.username.size")
    @Column(name="username")
    private String username;
    
    @NotNull(message = "error.user.password.empty")
    @Size(min = 4, max = 64, message = "error.user.password.size")  
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="password")
    private String password;
    
    @NotNull(message = "error.user.mail.empty")
    @Size(min = 4, max = 64, message = "error.user.mail.size")
    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
        "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
        "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
        "+(?:[a-zA-Z]){2,}\\.?)$", 
        message = "error.user.mail.invalid")
    @Column(name="mail")
    private String mail;
    
    @Column(name="ip")
    private String ip;
    
    @Column(name="account_non_expired")
    private boolean accountNonExpired;
    
    @Column(name="account_non_locked")
    private boolean accountNonLocked;
    
    @Column(name="credentials_non_expired")
    private boolean credentialsNonExpired;
    
    @Column(name="enabled")
    private boolean enabled;
    
    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now();
    
    @Column(name = "last_visited", nullable = false)
    private LocalDateTime lastVisited = LocalDateTime.now();
          
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "users_authorities", 
        joinColumns = {@JoinColumn(name = "users_id", nullable = false, updatable = false)},
		inverseJoinColumns = {@JoinColumn(name = "authorities_id", nullable = false, updatable = false)}
    )
    private Set<Authority> authorities = new HashSet<>();
    
    public boolean containsAuthority(String role) {
        return authorities.contains(new Authority(role));
    }
      
    public User() {      
    }
    
    public Long getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
    
    @Override
    public Set<Authority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public LocalDateTime getCreated() {
        return created;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDateTime getLastVisited() {
        return lastVisited;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setLastVisited(LocalDateTime lastVisited) {
        this.lastVisited = lastVisited;
    } 

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof User && Objects.equals(((User) other).id, this.id);
    }

    @Override
    @SuppressWarnings("CloneDoesntDeclareCloneNotSupportedException")
    protected User clone() throws CloneNotSupportedException {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // not possible
        }
    }

    @Override
    public String toString() {
        return this.username;
    }
}
