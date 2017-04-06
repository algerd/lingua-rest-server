
package ru.javafx.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.javafx.entity.Word;
import ru.javafx.repository.WordRepository;

@Component
public class WordValidator extends BaseRestValidator {
       
    @Autowired
    private WordRepository wordRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Word.class.equals(clazz);
    }

    @Override
    protected void validatePostRequest(Object target, Errors errors) {
        
        Word word = (Word) target;       
        if (wordRepository.findByWord(word.getWord()) != null) {
            errors.rejectValue("word", "error.word.word.duplicate");
        }
              
    }
    
    @Override
    protected void validatePutRequest(Object target, Errors errors) {
        
    }
    
    @Override
    protected void validateDeleteRequest(Object target, Errors errors) {
        
    }

}
