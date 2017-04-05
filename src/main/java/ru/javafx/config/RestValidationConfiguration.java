
package ru.javafx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import ru.javafx.validator.UserValidator;
import ru.javafx.validator.WordValidator;

@Configuration
public class RestValidationConfiguration extends RepositoryRestConfigurerAdapter {
    
    @Autowired
    private WordValidator wordValidator;
    @Autowired
    private UserValidator userValidator;

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        /*
            beforeCreate - POST request in spring data rest
            beforeSave - PUT request in spring data rest
            beforeDelete - DELETE request in spring data rest
        */
        
        validatingListener.addValidator("beforeCreate", wordValidator);
        validatingListener.addValidator("beforeSave", wordValidator);
        validatingListener.addValidator("beforeDelete", wordValidator);
        
        validatingListener.addValidator("beforeCreate", userValidator);
        validatingListener.addValidator("beforeSave", userValidator);
        validatingListener.addValidator("beforeDelete", userValidator);
        
    }
    
}