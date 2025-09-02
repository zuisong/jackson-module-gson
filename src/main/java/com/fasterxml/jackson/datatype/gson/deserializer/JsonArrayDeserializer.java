package com.fasterxml.jackson.datatype.gson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.google.gson.JsonArray;

import java.io.IOException;

public class JsonArrayDeserializer extends StdDeserializer<JsonArray> {
    public final static JsonArrayDeserializer instance = new JsonArrayDeserializer();
    private static final long serialVersionUID = 1L;

    public JsonArrayDeserializer() {
        super(JsonArray.class);
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Untyped;
    }

    @Override
    public JsonArray deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        // 07-Jan-2019, tatu: As per [datatype-json-org#15], need to verify it's an Array
        if (!p.isExpectedStartArrayToken()) {
            final JsonToken t = p.currentToken();
            return (JsonArray) ctxt.handleUnexpectedToken(handledType(), t, p,
                    "Unexpected token (%s), expected START_ARRAY for %s value",
                    t, ClassUtil.nameOf(handledType()));
        }
        return JsonElementDeserializer.instance.deserialize(p, ctxt).getAsJsonArray();
    }
}
