package com.example.backendlab.util;

import lombok.Getter;

@Getter
public class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(T firstElement, U secondElement) {
        this.first = firstElement;
        this.second = secondElement;
    }

    public static <T, U> Pair<T, U> of(T firstElement, U secondElement) {
        return new Pair<>(firstElement, secondElement);
    }
}
