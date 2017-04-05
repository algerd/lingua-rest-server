
package ru.javafx.validator;

import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class BaseRequestValidator implements Validator {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected HttpServletRequest request;
    
    @Override
    public void validate(Object target, Errors errors) {
        validateConstraintViolations(target, errors);
        
        switch (request.getMethod()) {
            case "POST" : 
                validatePostRequest(target, errors);
                break;
            case "PUT" : 
                validatePutRequest(target, errors);
                break;    
            case "DELETE":
                validateDeleteRequest(target, errors);
                break;
            default:
                break;
        }                 
    } 
    
    private void validateConstraintViolations(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(target);       
        //logger.info("ConstraintViolations: {}", constraintViolations);       
        for (ConstraintViolation<?> violation : constraintViolations) {
            errors.rejectValue(violation.getPropertyPath().toString(), violation.getMessage());
        } 
    }
    
    protected abstract void validatePostRequest(Object target, Errors errors);
    
    protected abstract void validatePutRequest(Object target, Errors errors);
    
    protected abstract void validateDeleteRequest(Object target, Errors errors);

}
