package com.fasterxml.jackson.datatype.gson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.google.gson.*;

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

        try {
            switch (p.getCurrentToken()) {
                case START_ARRAY:
                    JsonArray array = new JsonArray();
                    while (p.nextToken() != JsonToken.END_ARRAY) {
                        array.add(deserialize(p, ctxt));
                    }
                    return array;
                case START_OBJECT:
                    JsonObject ob = new JsonObject();
                    for (JsonToken t = p.nextToken(); t == JsonToken.FIELD_NAME; t = p.nextToken()) {
                        String fieldName = p.getCurrentName();
                        p.nextToken();
                        ob.add(fieldName, deserialize(p, ctxt));
                    }
                    return ob;
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
