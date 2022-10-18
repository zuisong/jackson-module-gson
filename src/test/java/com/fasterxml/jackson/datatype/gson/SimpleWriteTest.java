package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;

public class SimpleWriteTest extends ModuleTestBase
{
    public void testWriteObject() throws Exception
    {
        final ObjectMapper mapper = newMapper();

        // Ok: let's create JSONObject from JSON text
        String JSON = "{\"a\":{\"b\":3}}";
        JsonObject ob = JsonParser.parseString(JSON).getAsJsonObject();
        assertEquals(JSON, mapper.writeValueAsString(ob));

        // And for [Issue#2], with null(s):
        JSON = "{\"a\":null}";
        ob = JsonParser.parseString(JSON).getAsJsonObject();
        assertEquals(JSON, mapper.writeValueAsString(ob));
    }

    public void testWriteArray() throws Exception
    {
        final ObjectMapper mapper = newMapper();

        // Ok: let's create JSONObject from JSON text
        String JSON = "[1,true,\"text\",[null,3],{\"a\":[1.25]}]";
        JsonArray ob = JsonParser.parseString(JSON).getAsJsonArray();
        assertEquals(JSON, mapper.writeValueAsString(ob));
    }
}
