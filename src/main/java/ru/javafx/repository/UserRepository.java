package ru.javafx.repository;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.javafx.entity.QUser;
import ru.javafx.entity.User;

@Transactional
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends 
        PagingAndSortingRepository<User, Long>, 
        QueryDslPredicateExecutor<User>,
        QuerydslBinderCustomizer<QUser> {
        
    final static Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    
    @Override
    default void customize(QuerydslBindings bindings, QUser user) { 
        //bindings.bind(String.class).all(new StringMultiValueBinding());
        //bindings.bind(Integer.class).all(new NumberMultiValueBinding<>()); 
    }
    
    User findByUsername(@Param("username") String username);
    
    User findByMail(String mail);
    
    List<User> findByIp(String ip);
    
}
