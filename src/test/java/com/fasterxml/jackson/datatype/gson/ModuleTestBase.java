package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.Arrays;

public abstract class ModuleTestBase
{
    public ObjectMapper newMapper() {
        return new ObjectMapper()
                .registerModule(new GsonModule());
    }

    public JsonMapper.Builder newMapperBuilder() {
        return JsonMapper.builder()
                .addModule(new GsonModule());
    }

    protected void verifyException(Throwable e, String... matches)
    {
        String msg = e.getMessage();
        String lmsg = (msg == null) ? "" : msg.toLowerCase();
        for (String match : matches) {
            String lmatch = match.toLowerCase();
            if (lmsg.contains(lmatch)) {
                return;
            }
        }
        throw new Error("Expected an exception with one of substrings ("+Arrays.asList(matches)+"): got one with message \""+msg+"\"");
    }
}
