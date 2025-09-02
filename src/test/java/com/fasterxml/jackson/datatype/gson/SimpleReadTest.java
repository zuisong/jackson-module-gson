package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.junit.Test;

import static org.junit.Assert.*;


public class SimpleReadTest extends ModuleTestBase {

    final ObjectMapper mapper = newMapper();

    @Test
    public void testReadObject() throws Exception {
        JsonObject ob = mapper.readValue("{\"a\":{\"b\":3}, \"c\":[9, -4], \"d\":null, \"e\":true}",
                JsonObject.class);
        assertEquals(4, ob.size());
        JsonObject ob2 = ob.get("a").getAsJsonObject();
        assertEquals(1, ob2.size());
        assertEquals(3, ob2.getAsJsonPrimitive("b").getAsInt());
        JsonArray array = ob.getAsJsonArray("c");
        assertEquals(2, array.size());
        assertEquals(9, array.get(0).getAsInt());
        assertEquals(-4, array.get(1).getAsInt());
        assertTrue(ob.get("d").isJsonNull());
        assertTrue(ob.get("e").getAsBoolean());
    }

    @Test
    public void testReadArray() throws Exception {
        JsonArray array = mapper.readValue("[null, 13, false, 1.25, \"abc\", {\"a\":13}, [ ] ]",
                JsonArray.class);
        assertEquals(7, array.size());
        assertTrue(array.get(0).isJsonNull());
        assertEquals(13, array.get(1).getAsInt());
        assertFalse(array.get(2).getAsBoolean());
        assertEquals(1.25, array.get(3).getAsDouble(), 0.0001);
        assertEquals("abc", array.get(4).getAsString());
        JsonObject ob = array.get(5).getAsJsonObject();
        assertEquals(1, ob.size());
        assertEquals(13, ob.get("a").getAsInt());
        JsonArray array2 = array.get(6).getAsJsonArray();
        assertEquals(0, array2.size());
    }

    @Test
    public void testReadJsonElement_Object() throws Exception {
        JsonElement ele = mapper.readValue("{\"a\":{\"b\":3}, \"c\":[9, -4], \"d\":null, \"e\":true}",
                JsonElement.class);
        final JsonObject ob = ele.getAsJsonObject();
        assertEquals(4, ob.size());
        JsonObject ob2 = ob.get("a").getAsJsonObject();
        assertEquals(1, ob2.size());
        assertEquals(3, ob2.getAsJsonPrimitive("b").getAsInt());
        JsonArray array = ob.getAsJsonArray("c");
        assertEquals(2, array.size());
        assertEquals(9, array.get(0).getAsInt());
        assertEquals(-4, array.get(1).getAsInt());
        assertTrue(ob.get("d").isJsonNull());
        assertTrue(ob.get("e").getAsBoolean());
    }

    @Test
    public void testReadJsonElement_Array() throws Exception {
        JsonElement ele = mapper
                .readValue("[null, 13, false, 1.25, \"abc\", {\"a\":13}, [ ] ]",
                        JsonElement.class);
        JsonArray array = ele.getAsJsonArray();
        assertEquals(7, array.size());
        assertTrue(array.get(0).isJsonNull());
        assertEquals(13, array.get(1).getAsInt());
        assertFalse(array.get(2).getAsBoolean());
        assertEquals(1.25, array.get(3).getAsDouble(), 0.0001);
        assertEquals("abc", array.get(4).getAsString());
        JsonObject ob = array.get(5).getAsJsonObject();
        assertEquals(1, ob.size());
        assertEquals(13, ob.get("a").getAsInt());
        JsonArray array2 = array.get(6).getAsJsonArray();
        assertEquals(0, array2.size());
    }


    @Test
    public void testReadJsonPrimitive_primitive() throws Exception {
        JsonPrimitive ele = mapper
                .readValue("1",
                        JsonPrimitive.class);
        assertEquals(new JsonPrimitive(1), ele);
        assertNotEquals(new JsonPrimitive("1"), ele);
    }

    @Test
    public void testReadJsonPrimitive_primitive_string() throws Exception {
        JsonPrimitive ele = mapper
                .readValue("\"1\"",
                        JsonPrimitive.class);
        assertEquals(new JsonPrimitive("1"), ele);
    }

    @Test
    public void testReadJsonPrimitive_primitive_boolean() throws Exception {
        JsonPrimitive ele = mapper
                .readValue("true",
                        JsonPrimitive.class);
        assertEquals(new JsonPrimitive(true), ele);
    }

    @Test
    public void testReadJsonPrimitive_primitive_number() throws Exception {
        JsonPrimitive ele = mapper
                .readValue("1.250",
                        JsonPrimitive.class);
        assertEquals(new JsonPrimitive(1.25), ele);
    }


}