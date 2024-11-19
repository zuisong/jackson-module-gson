package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.gson.deserializer.JsonArrayDeserializer;
import com.fasterxml.jackson.datatype.gson.deserializer.JsonElementDeserializer;
import com.fasterxml.jackson.datatype.gson.deserializer.JsonObjectDeserializer;
import com.fasterxml.jackson.datatype.gson.serializer.JsonArraySerializer;
import com.fasterxml.jackson.datatype.gson.serializer.JsonElementSerializer;
import com.fasterxml.jackson.datatype.gson.serializer.JsonObjectSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class GsonModule extends SimpleModule {
    private static final long serialVersionUID = 1;

    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */

    public GsonModule() {
        super(PackageVersion.VERSION);
        addDeserializer(JsonArray.class, JsonArrayDeserializer.instance);
        addDeserializer(JsonObject.class, JsonObjectDeserializer.instance);
        addDeserializer(JsonElement.class, JsonElementDeserializer.instance);


        addSerializer(JsonArraySerializer.instance);
        addSerializer(JsonObjectSerializer.instance);
        addSerializer(JsonElementSerializer.instance);
    }
}
