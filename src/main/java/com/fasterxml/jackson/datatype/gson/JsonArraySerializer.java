package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonArraySerializer extends JsonBaseSerializer<JsonArray> {
    public final static JsonArraySerializer instance = new JsonArraySerializer();
    private static final long serialVersionUID = 1L;

    public JsonArraySerializer() {
        super(JsonArray.class);
    }

    @Override // since 2.6
    public boolean isEmpty(SerializerProvider provider, JsonArray value) {
        return (value == null) || value.size() == 0;
    }

    @Override
    public void serialize(JsonArray value, JsonGenerator g, SerializerProvider provider) throws IOException {
        g.writeStartArray();
        serializeContents(value, g, provider);
        g.writeEndArray();
    }

    @Override
    public void serializeWithType(JsonArray value, JsonGenerator g,
                                  SerializerProvider provider,
                                  TypeSerializer typeSer) throws IOException {
        g.setCurrentValue(value);
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g, typeSer.typeId(value, JsonToken.START_ARRAY));
        serializeContents(value, g, provider);
        typeSer.writeTypeSuffix(g, typeIdDef);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
            throws JsonMappingException {
        return createSchemaNode("array", true);
    }

    protected void serializeContents(JsonArray value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        for (int i = 0, len = value.size(); i < len; ++i) {
            JsonElement ob = value.get(i);
            JsonElementSerializer.instance.serialize(ob, g, provider);
        }
    }
}
