
package ru.javafx.multitenant;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource(value = "classpath:multitenant.properties", ignoreResourceNotFound = true),
	@PropertySource(value = "classpath:config/multitenant.properties", ignoreResourceNotFound = true)
})
public class MultitenantFilterRegistrationConfiguration {
    
    @Autowired
    private MultitenantProperties multitenantProperties;
    
    @Autowired
    private MultitenantFilter multitenantFilter;
    
    @Bean
    public FilterRegistrationBean multitenantFilterRegistration(){             
        FilterRegistrationBean registration = new FilterRegistrationBean();     
        registration.setFilter(multitenantFilter);
        multitenantProperties.getPaths().forEach(path -> registration.addUrlPatterns(multitenantProperties.getBasePath() + path));       
        multitenantProperties.getFullPaths().forEach(path -> registration.addUrlPatterns(path)); 
        return registration;
    }

}
