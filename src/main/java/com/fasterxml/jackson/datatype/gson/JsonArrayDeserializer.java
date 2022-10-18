package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;

import java.io.IOException;

public class JsonArrayDeserializer extends StdDeserializer<JsonArray> {
    private static final long serialVersionUID = 1L;

    public final static JsonArrayDeserializer instance = new JsonArrayDeserializer();

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

        JsonArray array = new JsonArray();
        JsonToken t;
        while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
            switch (t) {
                case START_ARRAY:
                    array.add(deserialize(p, ctxt));
                    continue;
                case START_OBJECT:
                    array.add(JsonObjectDeserializer.instance.deserialize(p, ctxt));
                    continue;
                case VALUE_STRING:
                    array.add(p.getText());
                    continue;
                case VALUE_NULL:
                    array.add(JsonNull.INSTANCE);
                    continue;
                case VALUE_TRUE:
                    array.add(Boolean.TRUE);
                    continue;
                case VALUE_FALSE:
                    array.add(Boolean.FALSE);
                    continue;
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    array.add(p.getNumberValue());
                    continue;
                case VALUE_EMBEDDED_OBJECT:
                    array.add(p.getValueAsString());
                    continue;
                default:
                    return (JsonArray) ctxt.handleUnexpectedToken(handledType(), p);
            }
        }
        return array;
    }
}
