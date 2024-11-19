package com.fasterxml.jackson.datatype.gson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.IOException;


public class JsonElementDeserializer extends StdDeserializer<JsonElement> {
    public final static JsonElementDeserializer instance = new JsonElementDeserializer();
    private static final long serialVersionUID = 1L;

    public JsonElementDeserializer() {
        super(JsonObject.class);
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Untyped;
    }

    @Override
    public JsonElement deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        JsonToken t = p.getCurrentToken();

        try {
            switch (t) {
                case START_ARRAY:
                    return JsonArrayDeserializer.instance.deserialize(p, ctxt);
                case START_OBJECT:
                    return JsonObjectDeserializer.instance.deserialize(p, ctxt);
                case VALUE_STRING:
                case VALUE_EMBEDDED_OBJECT:
                    return new JsonPrimitive(p.getText());
                case VALUE_NULL:
                    return JsonNull.INSTANCE;
                case VALUE_TRUE:
                    return new JsonPrimitive(Boolean.TRUE);
                case VALUE_FALSE:
                    return new JsonPrimitive(Boolean.FALSE);
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    return new JsonPrimitive(p.getNumberValue());
                default:
            }
        } catch (Exception e) {
            throw ctxt.instantiationException(handledType(), e);
        }
        return (JsonObject) ctxt.handleUnexpectedToken(JsonObject.class, p);
    }
}
