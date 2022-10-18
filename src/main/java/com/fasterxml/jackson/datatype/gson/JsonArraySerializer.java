package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonArraySerializer extends JSONBaseSerializer<JsonArray> {
    private static final long serialVersionUID = 1L;

    public final static JsonArraySerializer instance = new JsonArraySerializer();

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

    @Override
    public void serializeWithType(JsonArray value, JsonGenerator g, SerializerProvider provider,
                                  TypeSerializer typeSer) throws IOException {
        g.setCurrentValue(value);
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g,
                typeSer.typeId(value, JsonToken.START_ARRAY));
        serializeContents(value, g, provider);
        typeSer.writeTypeSuffix(g, typeIdDef);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
            throws JsonMappingException {
        return createSchemaNode("array", true);
    }

    protected void serializeContents(JsonArray value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        for (int i = 0, len = value.size(); i < len; ++i) {
            JsonElement ob = value.get(i);
            serializeJsonElement(ob, g, provider);
        }
    }


    static void serializeJsonElement(JsonElement value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        JsonElement ob = value;
        if (ob.isJsonNull()) {
            g.writeNull();
        }
        if (ob.isJsonObject()) {
            JsonObjectSerializer.instance.serialize(ob.getAsJsonObject(), g, provider);
        }
        if (ob.isJsonArray()) {
            JsonArraySerializer.instance.serialize((JsonArray) ob, g, provider);
        }
        if (ob.isJsonPrimitive()) {

            final JsonPrimitive ob1 = ob.getAsJsonPrimitive();

            if (ob1.isBoolean()) {
                g.writeBoolean(ob1.getAsBoolean());
            }

            if (ob1.isString()) {
                g.writeString(ob1.getAsString());
            }

            if (ob1.isNumber()) {
                final Number num = ob1.getAsNumber();
                final Class<? extends Number> cls = num.getClass();
                if (cls == Long.class) {
                    g.writeNumber(num.longValue());
                }
                if (cls == Double.class) {
                    g.writeNumber(num.doubleValue());
                }
            }
        }
    }
}
