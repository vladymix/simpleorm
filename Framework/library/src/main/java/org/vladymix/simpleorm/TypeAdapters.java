package org.vladymix.simpleorm;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Fabricio Altamirano on 30/6/17.
 */

final class TypeAdapters {
    private TypeAdapters() {
    }

    public static class StringAdapter implements TypeAdapter<String> {
        @Override
        public String fromCursor(Cursor c, String columnName) {
            return c.getString(c.getColumnIndexOrThrow(columnName));
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, String object) {
            values.put(columnName, object);
        }
    }

    public static class ShortAdapter implements TypeAdapter<Short> {
        @Override
        public Short fromCursor(Cursor c, String columnName) {
            return c.getShort(c.getColumnIndexOrThrow(columnName));
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Short object) {
            values.put(columnName, object);
        }
    }

    public static class IntegerAdapter implements TypeAdapter<Integer> {
        @Override
        public Integer fromCursor(Cursor c, String columnName) {
            return c.getInt(c.getColumnIndexOrThrow(columnName));
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Integer object) {
            values.put(columnName, object);
        }
    }

    public static class LongAdapter implements TypeAdapter<Long> {
        @Override
        public Long fromCursor(Cursor c, String columnName) {
            return c.getLong(c.getColumnIndexOrThrow(columnName));
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Long object) {
            values.put(columnName, object);
        }
    }

    public static class FloatAdapter implements TypeAdapter<Float> {
        @Override
        public Float fromCursor(Cursor c, String columnName) {
            return c.getFloat(c.getColumnIndexOrThrow(columnName));
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Float object) {
            values.put(columnName, object);
        }
    }

    public static class DoubleAdapter implements TypeAdapter<Double> {
        @Override
        public Double fromCursor(Cursor c, String columnName) {
            return c.getDouble(c.getColumnIndexOrThrow(columnName));
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Double object) {
            values.put(columnName, object);
        }
    }

    public static class ArrayAdapter implements TypeAdapter<byte[]> {
        @Override
        public byte[] fromCursor(Cursor c, String columnName) {
            return c.getBlob(c.getColumnIndexOrThrow(columnName));
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, byte[] object) {
            values.put(columnName, object);
        }
    }

    public static class BooleanAdapter implements TypeAdapter<Boolean> {
        @Override
        public Boolean fromCursor(Cursor c, String columnName) {
            return c.getInt(c.getColumnIndexOrThrow(columnName)) == 1;
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, Boolean object) {
            values.put(columnName, object);
        }
    }

    public interface TypeAdapter<T> {

        /**
         * Reads a column from cursor and converts it to a Java object. Returns the
         * converted object.
         *
         * @param c cursor containing the column
         * @param columnName name of the column containing data representing the Java
         * object
         * @return the converted Java object. May be null.
         */
        public T fromCursor(Cursor c, String columnName);

        /**
         * Converts Java object into type that can be put into {@link ContentValues}
         * and puts it into supplied {@code values} instance under {@code columnName}
         * key.
         *
         * @param values {@link ContentValues} to be filled with data from
         * {@code object}
         * @param columnName name of the target column for converted {@code object}
         * @param object an object to be converted
         */
        public void toContentValues(ContentValues values, String columnName, T object);
    }
}
