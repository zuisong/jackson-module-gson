package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class GsonModule extends SimpleModule
{
    private static final long serialVersionUID = 1;

    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */

    public GsonModule()
    {
        super(PackageVersion.VERSION);
        addDeserializer(JsonArray.class, JsonArrayDeserializer.instance);
        addDeserializer(JsonObject.class, JsonObjectDeserializer.instance);
        addSerializer(JsonArraySerializer.instance);
        addSerializer(JsonObjectSerializer.instance);
    }
}
