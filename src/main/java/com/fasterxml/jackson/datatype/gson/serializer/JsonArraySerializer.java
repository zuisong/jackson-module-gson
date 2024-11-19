package com.fasterxml.jackson.datatype.gson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;

public class JsonArraySerializer extends StdSerializer<JsonArray> {
    public final static JsonArraySerializer instance = new JsonArraySerializer();
    private static final long serialVersionUID = 1L;

    public JsonArraySerializer() {
        super(JsonArray.class);
    }

    @Override // since 2.6
    public boolean isEmpty(SerializerProvider provider, JsonArray value) {
        return (value == null) || value.isEmpty();
    }

    @Override
    public void serialize(JsonArray value, JsonGenerator g, SerializerProvider provider) throws IOException {
        g.writeStartArray();
        serializeContents(value, g, provider);
        g.writeEndArray();
    }

    private static void serializeContents(JsonArray value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        for (int i = 0, len = value.size(); i < len; ++i) {
            JsonElement ob = value.get(i);
            JsonElementSerializer.instance.serialize(ob, g, provider);
        }
    }
}
