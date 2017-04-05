
package ru.javafx.repository;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.security.RolesAllowed;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.javafx.entity.Word;
import ru.javafx.entity.QWord;
import ru.javafx.repository.operators.NumberMultiValueBinding;
import ru.javafx.repository.operators.StringMultiValueBinding;

@Transactional
@RepositoryRestResource(collectionResourceRel = "words", path = "words")
//@RolesAllowed({"USER"})
public interface WordRepository extends 
        PagingAndSortingRepository<Word, Long>, 
        QueryDslPredicateExecutor<Word>,
        QuerydslBinderCustomizer<QWord> {
    
    final static Logger LOGGER = LoggerFactory.getLogger(WordRepository.class);
    
    @Override
    default void customize(QuerydslBindings bindings, QWord word) { 
        bindings.bind(String.class).all(new StringMultiValueBinding());
        bindings.bind(Integer.class).all(new NumberMultiValueBinding<>()); 
    }
    
    List<Word> findAll();
    
    Word findByWord(String word);
    
}
