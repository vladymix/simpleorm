package org.vladymix.simpleorm;

import android.database.Cursor;


import org.vladymix.simpleorm.annotations.Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fabricio Altamirano on 30/6/17.
 */

public class SimpleOrm {

    private static final HashMap<Class<?>, TypeAdapters.TypeAdapter<?>> TYPE_ADAPTERS;
    private static final HashMap<Class<?>, DaoAdapter<?>> mDaoAdapterCache = new HashMap<>();
    private final HashMap<Class<?>, TypeAdapters.TypeAdapter<?>> mTypeAdapters;

    static {

        HashMap<Class<?>, TypeAdapters.TypeAdapter<?>> typeAdapters = new HashMap<>();

        typeAdapters.put(short.class, new TypeAdapters.ShortAdapter());
        typeAdapters.put(int.class, new TypeAdapters.IntegerAdapter());
        typeAdapters.put(long.class, new TypeAdapters.LongAdapter());
        typeAdapters.put(boolean.class, new TypeAdapters.BooleanAdapter());
        typeAdapters.put(float.class, new TypeAdapters.FloatAdapter());
        typeAdapters.put(double.class, new TypeAdapters.DoubleAdapter());
        typeAdapters.put(byte[].class, new TypeAdapters.ArrayAdapter());

        typeAdapters.put(Short.class, new OptionalTypeAdapter<>(new TypeAdapters.ShortAdapter()));
        typeAdapters.put(Integer.class, new OptionalTypeAdapter<>(new TypeAdapters.IntegerAdapter()));
        typeAdapters.put(Long.class, new OptionalTypeAdapter<>(new TypeAdapters.LongAdapter()));
        typeAdapters.put(Boolean.class, new OptionalTypeAdapter<>(new TypeAdapters.BooleanAdapter()));
        typeAdapters.put(Float.class, new OptionalTypeAdapter<>(new TypeAdapters.FloatAdapter()));
        typeAdapters.put(Double.class, new OptionalTypeAdapter<>(new TypeAdapters.DoubleAdapter()));
        typeAdapters.put(Byte[].class, new OptionalTypeAdapter<>(new TypeAdapters.ArrayAdapter()));

        typeAdapters.put(String.class, new OptionalTypeAdapter<>(new TypeAdapters.StringAdapter()));

        TYPE_ADAPTERS = typeAdapters;
    }

    public SimpleOrm()
    {
        this(TYPE_ADAPTERS);
    }

    private SimpleOrm(HashMap<Class<?>, TypeAdapters.TypeAdapter<?>> typeAdapters) {
        mTypeAdapters = typeAdapters;
    }

    public <T> T fromCursor(Cursor c, Class<T> klass) {
        DaoAdapter<T> adapter = getAdapter(klass);
        return adapter.fromCursor(c, adapter.createInstance());
    }

    public <T> SimpleOrm registerTypeAdapter(Class<T> klass, TypeAdapters.TypeAdapter<T> typeAdapter) {
        mTypeAdapters.put(klass, typeAdapter);
        return this;
    }

    private <T> DaoAdapter<T> buildDaoAdapter(Class<T> klass) {
        List<FieldAdapter> fieldAdapters = new ArrayList<>();
        for (Field field : Fields.allFieldsIncludingPrivateAndSuper(klass)) {
            field.setAccessible(true);
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (columnAnnotation != null) {
                if (field.getType().isPrimitive() && columnAnnotation.treatNullAsDefault()) {
                    throw new IllegalArgumentException("Cannot set treatNullAsDefault on primitive members");
                }
                if (columnAnnotation.treatNullAsDefault() && columnAnnotation.readonly()) {
                    throw new IllegalArgumentException("It doesn't make sense to set treatNullAsDefault on readonly column");
                }
                ColumnFieldAdapter fieldAdapter = new ColumnFieldAdapter(field, mTypeAdapters.get(field.getType()));

                fieldAdapters.add(fieldAdapter);
            }

        }
        return new ReflectiveDaoAdapter<>(klass,fieldAdapters);
    }

    private <T> DaoAdapter<T> getAdapter(Class<T> klass) {
        DaoAdapter<?> cached = mDaoAdapterCache.get(klass);
        if (cached != null) {
            return (DaoAdapter<T>) cached;
        }

        DaoAdapter<T> adapter = buildDaoAdapter(klass);
        mDaoAdapterCache.put(klass, adapter);
        return adapter;
    }

}
