package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;


public class JsonObjectDeserializer extends StdDeserializer<JsonObject> {
    public final static JsonObjectDeserializer instance = new JsonObjectDeserializer();
    private static final long serialVersionUID = 1L;

    public JsonObjectDeserializer() {
        super(JsonObject.class);
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Untyped;
    }

    @Override
    public JsonObject deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {


        if (!p.isExpectedStartObjectToken()) {
            final JsonToken t = p.currentToken();
            return (JsonObject) ctxt.handleUnexpectedToken(handledType(), t, p,
                    "Unexpected token (%s), expected START_OBJECT for %s value",
                    t, ClassUtil.nameOf(handledType()));
        }

        JsonObject ob = new JsonObject();
        JsonToken t = p.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = p.nextToken();
        }

        for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
            String fieldName = p.getCurrentName();
            t = p.nextToken();
            ob.add(fieldName, JsonElementDeserializer.instance.deserialize(p, ctxt));
        }
        return ob;
    }
}
