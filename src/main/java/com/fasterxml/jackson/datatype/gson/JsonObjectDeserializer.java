package com.fasterxml.jackson.datatype.gson;

import java.io.IOException;

import com.fasterxml.jackson.core.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;


public class JsonObjectDeserializer extends StdDeserializer<JsonObject>
{
    private static final long serialVersionUID = 1L;

    public final static JsonObjectDeserializer instance = new JsonObjectDeserializer();

    public JsonObjectDeserializer()
    {
        super(JsonObject.class);
    }

    @Override
    public LogicalType logicalType() {
        return LogicalType.Untyped;
    }

    @Override
    public JsonObject deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException
    {
        JsonObject ob = new JsonObject();
        JsonToken t = p.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = p.nextToken();
        }
        for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
            String fieldName = p.getCurrentName();
            t = p.nextToken();
            try {
                switch (t) {
                case START_ARRAY:
                    ob.add(fieldName, JsonArrayDeserializer.instance.deserialize(p, ctxt));
                    continue;
                case START_OBJECT:
                    ob.add(fieldName, deserialize(p, ctxt));
                    continue;
                case VALUE_STRING:
                    ob.addProperty(fieldName, p.getText());
                    continue;
                case VALUE_NULL:
                    ob.add(fieldName, JsonNull.INSTANCE);
                    continue;
                case VALUE_TRUE:
                    ob.addProperty(fieldName, Boolean.TRUE);
                    continue;
                case VALUE_FALSE:
                    ob.addProperty(fieldName, Boolean.FALSE);
                    continue;
                case VALUE_NUMBER_INT:
                    ob.addProperty(fieldName, p.getNumberValue());
                    continue;
                case VALUE_NUMBER_FLOAT:
                    ob.addProperty(fieldName, p.getNumberValue());
                    continue;
                case VALUE_EMBEDDED_OBJECT:
                    ob.addProperty(fieldName, p.getText());
                    continue;
                default:
                }
            } catch (Exception e) {
                throw ctxt.instantiationException(handledType(), e);
            }
            return (JsonObject) ctxt.handleUnexpectedToken(JsonObject.class, p);
        }
        return ob;
    }
}
