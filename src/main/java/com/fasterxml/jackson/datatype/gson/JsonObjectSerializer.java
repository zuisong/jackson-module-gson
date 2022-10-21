package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JsonObjectSerializer extends JsonBaseSerializer<JsonObject> {
    public final static JsonObjectSerializer instance = new JsonObjectSerializer();
    private static final long serialVersionUID = 1L;

    public JsonObjectSerializer() {
        super(JsonObject.class);
    }

    @Override // since 2.6
    public boolean isEmpty(SerializerProvider provider, JsonObject value) {
        return (value == null) || value.size() == 0;
    }

    @Override
    public void serialize(JsonObject value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        g.writeStartObject(value);
        serializeContents(value, g, provider);
        g.writeEndObject();
    }

    protected void serializeContents(JsonObject value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        Iterator<java.util.Map.Entry<String, com.google.gson.JsonElement>> it = value.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<String, JsonElement> entry = it.next();
            String key = entry.getKey();
            g.writeFieldName(key);
            JsonElementSerializer.instance.serialize(entry.getValue(), g, provider);
        }
    }
}
