package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

public class ConvertTest extends ModuleTestBase {
    static class TestDomain {
        public Integer id;
        public String name;
        public Double da;
        public Map<String, Object> ldt;
        public Map<String, Object> ld;
        public Map<String, Object> lt;
        public JsonObject jsn;
        public JsonArray jsa;
    }

    private final ObjectMapper MAPPER = newMapper();

    @Test
    public void testIssue15() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zpj");
        map.put("id", 111);
        map.put("jsn", null);
        map.put("jsa", "[1, 34, 32, \"zpj\", {\"age\": 18, \"name\": \"zpj\", \"child\": {\"name\": \"zzy\", \"gender\": \"nan\"}}, {\"url\": \"test\", \"name\": \"suhu\"}]");
        final String Json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        System.out.println(Json);
        try {
            MAPPER.readValue(Json, TestDomain.class);
            fail("Should not pass");
        } catch (MismatchedInputException e) {
            verifyException(e, "Unexpected token");
            verifyException(e, "com.google.gson.JsonArray");
            verifyException(e, "START_ARRAY");
        }
    }
}
