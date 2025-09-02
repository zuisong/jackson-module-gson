package com.fasterxml.jackson.datatype.gson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.util.Map;

public class JsonElementSerializer extends StdSerializer<JsonElement> {
    public final static JsonElementSerializer instance = new JsonElementSerializer();
    private static final long serialVersionUID = 1L;

    public JsonElementSerializer() {
        super(JsonElement.class);
    }

    @Override // since 2.6
    public boolean isEmpty(SerializerProvider provider, JsonElement value) {
        return value == null;
    }

    @Override
    public void serialize(JsonElement value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        serializeContents(value, g, provider);
    }

    private void serializeContents(JsonElement value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        if (value.isJsonNull()) {
            g.writeNull();
            return;
        }
        if (value.isJsonObject()) {
            JsonObject value1 = value.getAsJsonObject();
            g.writeStartObject(value);
            for (Map.Entry<String, JsonElement> entry : value1.entrySet()) {
                String key = entry.getKey();
                g.writeFieldName(key);
                serializeContents(entry.getValue(), g, provider);
            }
            g.writeEndObject();
            return;
        }

        if (value.isJsonArray()) {
            JsonArray value1 = value.getAsJsonArray();
            g.writeStartArray();
            for (int i = 0, len = value1.size(); i < len; ++i) {
                JsonElement ob = value1.get(i);
                serializeContents(ob, g, provider);
            }
            g.writeEndArray();
            return;
        }

        if (value.isJsonPrimitive()) {

            final JsonPrimitive ob1 = value.getAsJsonPrimitive();

            if (ob1.isBoolean()) {
                g.writeBoolean(ob1.getAsBoolean());
                return;
            }

            if (ob1.isString()) {
                g.writeString(ob1.getAsString());
                return;
            }

            if (ob1.isNumber()) {
                final Number num = ob1.getAsNumber();
                g.writeNumber(num.toString());
            }
        }
    }
}
