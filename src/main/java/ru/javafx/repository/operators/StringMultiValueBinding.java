
package ru.javafx.repository.operators;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;
import java.util.Collection;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.querydsl.binding.MultiValueBinding;

public class StringMultiValueBinding implements MultiValueBinding<StringPath, String> {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public Predicate bind(StringPath path, Collection<? extends String> value) {
        Iterator<? extends String> it = value.iterator();
        String operator = it.next().trim().toLowerCase();
        //logger.info("operator :{}", operator);
        if (it.hasNext()) {
            String val = it.next().trim();
            //logger.info("value :{}", val);
            for (StringOperator stringOperator : StringOperator.values()) {
                if (operator.equals(stringOperator.toString())) {
                    return stringOperator.toPredicate(path, val);
                }
            }
            return path.containsIgnoreCase(val);
        } else {
            return path.eq(operator);
        }
    }  
}
