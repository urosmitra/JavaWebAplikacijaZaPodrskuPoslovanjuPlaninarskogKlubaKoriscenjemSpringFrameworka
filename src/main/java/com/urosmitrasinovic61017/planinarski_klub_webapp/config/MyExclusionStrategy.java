package com.urosmitrasinovic61017.planinarski_klub_webapp.config;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.annotations.ExcludeGson;

public class MyExclusionStrategy implements ExclusionStrategy {


    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getAnnotation(ExcludeGson.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
