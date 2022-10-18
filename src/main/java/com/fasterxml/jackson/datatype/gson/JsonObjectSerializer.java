package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

public class JsonObjectSerializer extends JSONBaseSerializer<JsonObject> {
    private static final long serialVersionUID = 1L;

    public final static JsonObjectSerializer instance = new JsonObjectSerializer();

    public JsonObjectSerializer() {
        super(JsonObject.class);
    }

    @Override // since 2.6
    public boolean isEmpty(SerializerProvider provider, JsonObject value) {
        return (value == null) || value.size() == 0;
    }

    @Override
    public void serialize(JsonObject value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        g.writeStartObject(value);
        serializeContents(value, g, provider);
        g.writeEndObject();
    }

    @Override
    public void serializeWithType(JsonObject value, JsonGenerator g, SerializerProvider provider,
                                  TypeSerializer typeSer) throws IOException {
        g.setCurrentValue(value);
        WritableTypeId typeIdDef = typeSer.writeTypePrefix(g,
                typeSer.typeId(value, JsonToken.START_OBJECT));
        serializeContents(value, g, provider);
        typeSer.writeTypeSuffix(g, typeIdDef);

    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
            throws JsonMappingException {
        return createSchemaNode("object", true);
    }

    protected void serializeContents(JsonObject value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        Iterator<java.util.Map.Entry<String, com.google.gson.JsonElement>> it = value.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<String, JsonElement> entry = it.next();
            String key = entry.getKey();
            JsonElement ob = entry.getValue();
            if (ob == null || ob == JsonNull.INSTANCE) {
                if (provider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES)) {
                    g.writeNullField(key);
                }
                continue;
            }
            g.writeFieldName(key);
            JsonArraySerializer.serializeJsonElement(ob, g, provider);
        }
    }
}
