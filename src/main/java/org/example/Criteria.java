package org.example;

public interface Criteria<T> {
    boolean isMatch(T n);
}
