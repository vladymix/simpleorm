package org.vladymix.simpleorm;

import android.content.ContentValues;
import android.database.Cursor;

import org.vladymix.simpleorm.annotations.Column;

import java.lang.reflect.Field;

/**
 * Created by Fabricio Altamirano on 30/6/17.
 */

class ColumnFieldAdapter extends FieldAdapter {

    private static final String[] EMPTY_ARRAY = new String[0];

    private final String mColumnName;
    private final String[] mColumnNames;
    private final TypeAdapters.TypeAdapter<?> mTypeAdapter;
    private final boolean mTreatNullAsDefault;
    private final boolean mReadonly;

    ColumnFieldAdapter(Field field, TypeAdapters.TypeAdapter<?> typeAdapter) {
        super(field);
        mTypeAdapter = typeAdapter;

        Column columnAnnotation = field.getAnnotation(Column.class);
        mColumnName = columnAnnotation.value();
        mColumnNames = new String[] { mColumnName };
        mTreatNullAsDefault = columnAnnotation.treatNullAsDefault();
        mReadonly = columnAnnotation.readonly();
    }

    @Override
    public void setValueFromCursor(Cursor inCursor, Object outTarget) throws IllegalArgumentException, IllegalAccessException {
        mField.set(outTarget, mTypeAdapter.fromCursor(inCursor, mColumnName));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void putValueToContentValues(Object fieldValue, ContentValues outValues) {
        boolean skipColumn = mReadonly || (mTreatNullAsDefault && fieldValue == null);
        if (!skipColumn) {
            ((TypeAdapters.TypeAdapter<Object>) mTypeAdapter).toContentValues(outValues, mColumnName, fieldValue);
        }
    }

    @Override
    public String[] getColumnNames() {
        return mColumnNames;
    }

    @Override
    public String[] getWritableColumnNames() {
        return mReadonly
                ? EMPTY_ARRAY
                : getColumnNames();
    }
}