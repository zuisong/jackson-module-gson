package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;

public class JsonArraySerializer extends JsonBaseSerializer<JsonArray> {
    public final static JsonArraySerializer instance = new JsonArraySerializer();
    private static final long serialVersionUID = 1L;

    public JsonArraySerializer() {
        super(JsonArray.class);
    }

    @Override // since 2.6
    public boolean isEmpty(SerializerProvider provider, JsonArray value) {
        return (value == null) || value.size() == 0;
    }

    @Override
    public void serialize(JsonArray value, JsonGenerator g, SerializerProvider provider) throws IOException {
        g.writeStartArray();
        serializeContents(value, g, provider);
        g.writeEndArray();
    }

    protected void serializeContents(JsonArray value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        for (int i = 0, len = value.size(); i < len; ++i) {
            JsonElement ob = value.get(i);
            JsonElementSerializer.instance.serialize(ob, g, provider);
        }
    }
}
