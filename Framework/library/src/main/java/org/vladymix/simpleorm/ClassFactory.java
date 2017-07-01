package org.vladymix.simpleorm;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Fabricio Altamirano on 30/6/17.
 */

abstract class ClassFactory<T> {
    abstract T newInstance() throws InvocationTargetException, IllegalAccessException, InstantiationException;
    public static <T> ClassFactory<T> get(final Class<?> rawType) {
        // Try to find a no-args constructor. May be any visibility including private.
        try {
            final Constructor<?> constructor = rawType.getDeclaredConstructor();
            constructor.setAccessible(true);
            return new ClassFactory<T>() {
                @SuppressWarnings("unchecked") // T is the same raw type as is requested
                @Override
                public T newInstance() throws IllegalAccessException, InvocationTargetException, InstantiationException {
                    Object[] args = null;
                    return (T) constructor.newInstance(args);
                }

                @Override
                public String toString() {
                    return rawType.getName();
                }
            };
        } catch (NoSuchMethodException ignored) {
            // No no-args constructor. Fall back to something more magical...
        }
       return null;
    }


}