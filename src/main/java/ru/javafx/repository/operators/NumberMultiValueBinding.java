
package ru.javafx.repository.operators;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberPath;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.data.querydsl.binding.MultiValueBinding;

public class NumberMultiValueBinding<T extends Number & Comparable<?>> implements MultiValueBinding<NumberPath<T>, T> {
    @Override
    public Predicate bind(NumberPath<T> path, Collection<? extends T> value) {
        Iterator<? extends T> it = value.iterator();
        T val = it.next();
        if (it.hasNext()) {
            return path.between(val, it.next());
        } else {
            return path.eq(val);
        }
    }
}
