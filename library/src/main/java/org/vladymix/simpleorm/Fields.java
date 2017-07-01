package org.vladymix.simpleorm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Fabricio Altamirano on 30/6/17.
 */

final class Fields {
    private Fields() {
    }

    static List<Field> allFieldsIncludingPrivateAndSuper(Class<?> klass) {
        List<Field> fields =new ArrayList<>();
        while (!klass.equals(Object.class)) {
            Collections.addAll(fields, klass.getDeclaredFields());
            klass = klass.getSuperclass();
        }
        return fields;
    }
}