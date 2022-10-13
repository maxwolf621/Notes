package com.streampractice;

@FunctionalInterface
public interface converter<F,T> {
    T convert(F from);
}
