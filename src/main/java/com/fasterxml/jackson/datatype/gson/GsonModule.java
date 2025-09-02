package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.gson.deserializer.*;
import com.fasterxml.jackson.datatype.gson.serializer.JsonElementSerializer;
import com.google.gson.*;


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
        addDeserializer(JsonPrimitive.class, JsonPrimitiveDeserializer.instance);


        addSerializer(JsonElementSerializer.instance);

    }
}
