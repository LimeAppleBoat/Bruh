package com.jab125.limeappleboat.bruh;

public class ImmutableRegistry<T> {
    private final T object;
    public ImmutableRegistry(T object) {
        this.object = object;
    }

    public T get() {
        return object;
    }
}
