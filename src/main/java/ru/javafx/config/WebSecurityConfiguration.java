
package ru.javafx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
   
    @Bean
    protected SessionRegistry sessionRegistryImpl() {
        return new SessionRegistryImpl();
    }
    
    //To enable authentication annotation support
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Value("${spring.data.rest.basePath}")
    private String basePath;
    
    @Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {      
        httpSecurity.csrf().disable()
            .authorizeRequests()       
            //.antMatchers(basePath + "/authorities/**").hasAuthority("ADMIN")            
            .antMatchers(basePath + "/users/**", basePath + "/users**").permitAll()    
            .anyRequest().authenticated() 
            //для тестирования  
            //.anyRequest().permitAll()    
        .and()
            .httpBasic();
   
                       
        /*
        httpSecurity
                .authorizeRequests()
                .antMatchers("/api/**")
                .permitAll()          
            .and().logout()
                .logoutUrl("/logout").logoutSuccessUrl("/login?loggedOut")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .permitAll()
            .and().sessionManagement()
                .sessionFixation().changeSessionId()
                .maximumSessions(1).maxSessionsPreventsLogin(true)
                .sessionRegistry(this.sessionRegistryImpl())
            .and().and()
                .csrf().requireCsrfProtectionMatcher(r -> {
                    String m = r.getMethod();
                    return !r.getServletPath().startsWith("/api/")
                        && ("POST".equals(m) || "PUT".equals(m)
                        || "DELETE".equals(m) || "PATCH".equals(m));
                });
        */
	}

}
