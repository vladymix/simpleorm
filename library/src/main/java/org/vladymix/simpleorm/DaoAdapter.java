package org.vladymix.simpleorm;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Fabricio Altamirano on 30/6/17.
 */

interface DaoAdapter<T> {

    T createInstance();

    T fromCursor(Cursor c, T object);

    ContentValues toContentValues(ContentValues values, T object);

    ContentValues createContentValues();

    String[] getProjection();

    String[] getWritableColumns();
}