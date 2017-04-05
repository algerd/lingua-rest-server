
package ru.javafx.repository.operators;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;

public enum StringOperator {
    
    CONTAINS("contains") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.contains(value);
        }       
    },
    CONTAINS_IGNORE_CASE("containsIgnoreCase") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.containsIgnoreCase(value);
        }       
    },
    ENDS_WITH("endsWith") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.endsWith(value);
        }       
    },
    ENDS_WITH_IGNORE_CASE("endsWithIgnoreCase") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.endsWithIgnoreCase(value);
        }       
    },
    EQ("eq") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.eq(value);
        }       
    },
    EQUALS_IGNORE_CASE("equalsIgnoreCase") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.equalsIgnoreCase(value);
        }       
    },   
    LIKE("like") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.like(value);
        }       
    },
    LIKE_IGNORE_CASE("likeIgnoreCase") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.likeIgnoreCase(value);
        }       
    },
    NOT_EQUALS_IGNORE_CASE("notEqualsIgnoreCase") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.notEqualsIgnoreCase(value);
        }       
    },
    NOT_LIKE("notLike") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.notLike(value);
        }       
    },
    STARTS_WITH("startsWith") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.startsWith(value);
        }       
    },
    STARTS_WITH_IGNORE_CASE("startsWithIgnoreCase") {
        @Override
        public BooleanExpression toPredicate(StringPath path, String value) {
            return path.startsWithIgnoreCase(value);
        }       
    };
 
    public abstract BooleanExpression toPredicate(StringPath path, String value);
    
    private final String operator;

    private StringOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return operator;
    }
    
}
