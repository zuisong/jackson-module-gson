package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;


public class SimpleReadTest extends ModuleTestBase {
    public void testReadObject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new GsonModule());

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

    public void testReadArray() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new GsonModule());

        JsonArray array = mapper.readValue("[null, 13, false, 1.25, \"abc\", {\"a\":13}, [ ] ]",
                JsonArray.class);
        assertEquals(7, array.size());
        assertTrue(array.get(0).isJsonNull());
        assertEquals(13, array.get(1).getAsInt());
        assertFalse(array.get(2).getAsBoolean());
        assertEquals(1.25, array.get(3).getAsDouble());
        assertEquals("abc", array.get(4).getAsString());
        JsonObject ob = array.get(5).getAsJsonObject();
        assertEquals(1, ob.size());
        assertEquals(13, ob.get("a").getAsInt());
        JsonArray array2 = array.get(6).getAsJsonArray();
        assertEquals(0, array2.size());
    }

    public void testReadJsonElement_Object() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new GsonModule());

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

    public void testReadJsonElement_Array() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new GsonModule());

        JsonElement ele = mapper
                .readValue("[null, 13, false, 1.25, \"abc\", {\"a\":13}, [ ] ]",
                        JsonElement.class);
        JsonArray array = ele.getAsJsonArray();
        assertEquals(7, array.size());
        assertTrue(array.get(0).isJsonNull());
        assertEquals(13, array.get(1).getAsInt());
        assertFalse(array.get(2).getAsBoolean());
        assertEquals(1.25, array.get(3).getAsDouble());
        assertEquals("abc", array.get(4).getAsString());
        JsonObject ob = array.get(5).getAsJsonObject();
        assertEquals(1, ob.size());
        assertEquals(13, ob.get("a").getAsInt());
        JsonArray array2 = array.get(6).getAsJsonArray();
        assertEquals(0, array2.size());
    }
}
