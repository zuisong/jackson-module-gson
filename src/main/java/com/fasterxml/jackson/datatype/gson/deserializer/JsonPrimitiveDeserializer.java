package com.fasterxml.jackson.datatype.gson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.io.IOException;

public class JsonPrimitiveDeserializer extends StdDeserializer<JsonPrimitive> {
    public final static JsonPrimitiveDeserializer instance = new JsonPrimitiveDeserializer();
    private static final long serialVersionUID = 1L;

    public JsonPrimitiveDeserializer() {
        super(JsonPrimitive.class);
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Untyped;
    }

    @Override
    public JsonPrimitive deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        final JsonToken t = p.currentToken();
        JsonElement element = JsonElementDeserializer.instance.deserialize(p, ctxt);
        if (element.isJsonPrimitive()) {
            return element.getAsJsonPrimitive();
        }
        return (JsonPrimitive) ctxt.handleUnexpectedToken(handledType(), t, p,
                "Unexpected token (%s), expected START_ARRAY for %s value",
                t, ClassUtil.nameOf(handledType()));
    }
}
