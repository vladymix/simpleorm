package org.vladymix.simpleorm;

import android.content.ContentValues;
import android.database.Cursor;
import org.vladymix.simpleorm.TypeAdapters.TypeAdapter;

/**
 * Created by Fabricio Altamirano on 30/6/17.
 */

public class OptionalTypeAdapter<T> implements TypeAdapter<T> {

    private final TypeAdapter<T> mWrappedAdapter;

    public OptionalTypeAdapter(TypeAdapter<T> wrappedAdapter) {
        mWrappedAdapter = wrappedAdapter;
    }

    @Override
    public T fromCursor(Cursor c, String columnName) {
        return c.isNull(c.getColumnIndexOrThrow(columnName))
                ? null
                : mWrappedAdapter.fromCursor(c, columnName);
    }

    @Override
    public void toContentValues(ContentValues values, String columnName, T object) {
        if (object != null) {
            mWrappedAdapter.toContentValues(values, columnName, object);
        } else {
            values.putNull(columnName);
        }
    }
}