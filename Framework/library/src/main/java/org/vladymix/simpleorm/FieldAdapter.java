package org.vladymix.simpleorm;

import android.content.ContentValues;
import android.database.Cursor;

import java.lang.reflect.Field;

/**
 * Created by Fabricio Altamirano on 30/6/17.
 */

abstract class FieldAdapter {

    final Field mField;

    FieldAdapter(Field field) {
        mField = field;
    }

    public abstract void setValueFromCursor(Cursor inCursor, Object outTarget)
            throws IllegalArgumentException, IllegalAccessException;

    public void putToContentValues(Object inObject, ContentValues outValues) throws IllegalAccessException {
        Object value = inObject != null ? mField.get(inObject) : null;
        putValueToContentValues(value, outValues);
    }

    protected abstract void putValueToContentValues(Object value, ContentValues outValues);

    public abstract String[] getColumnNames();

    public abstract String[] getWritableColumnNames();
}
