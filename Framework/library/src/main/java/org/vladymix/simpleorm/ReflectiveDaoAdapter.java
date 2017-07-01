package org.vladymix.simpleorm;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabricio Altamirano on 30/6/17.
 */
class ReflectiveDaoAdapter<T> implements DaoAdapter<T> {

    private ClassFactory<T> mClassFactory;
    private List<FieldAdapter> mFieldAdapters;

    ReflectiveDaoAdapter(Class<T> klass, List<FieldAdapter> fieldAdapters){

        mClassFactory = ClassFactory.get(klass);

        this.mFieldAdapters = fieldAdapters;
        List<String[]> projectionBuilder = new ArrayList<>();
        List<String[]> writableColumnsBuilder = new ArrayList<>();

        for (FieldAdapter fieldAdapter : fieldAdapters) {
            projectionBuilder.add(fieldAdapter.getColumnNames());
            writableColumnsBuilder.add(fieldAdapter.getWritableColumnNames());
        }
    }

    @Override
    public T createInstance() {
        try{
            T instance = mClassFactory.newInstance();
            return   instance;
        }catch (Exception ex){
            return null;
        }

    }

    @Override
    public T fromCursor(Cursor c, T object) {
        try {
            for (FieldAdapter fieldAdapter : mFieldAdapters) {
                fieldAdapter.setValueFromCursor(c, object);
            }
            return object;
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public ContentValues toContentValues(ContentValues values, T object) {
        return null;
    }

    @Override
    public ContentValues createContentValues() {
        return null;
    }

    @Override
    public String[] getProjection() {
        return new String[0];
    }

    @Override
    public String[] getWritableColumns() {
        return new String[0];
    }
}
