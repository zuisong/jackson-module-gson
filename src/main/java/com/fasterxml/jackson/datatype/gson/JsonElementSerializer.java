package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonElementSerializer extends JsonBaseSerializer<JsonElement> {
    public final static JsonElementSerializer instance = new JsonElementSerializer();
    private static final long serialVersionUID = 1L;

    public JsonElementSerializer() {
        super(JsonElement.class);
    }

    @Override // since 2.6
    public boolean isEmpty(SerializerProvider provider, JsonElement value) {
        return value == null;
    }

    @Override
    public void serialize(JsonElement value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        serializeContents(value, g, provider);
    }

    @Override
    public void serializeWithType(JsonElement value, JsonGenerator g, SerializerProvider provider,
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

    public void serializeContents(JsonElement value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        JsonElement ob = value;
        if (ob.isJsonNull()) {
            g.writeNull();
        }
        if (ob.isJsonObject()) {
            JsonObjectSerializer.instance.serialize(ob.getAsJsonObject(), g, provider);
        }
        if (ob.isJsonArray()) {
            JsonArraySerializer.instance.serialize((JsonArray) ob, g, provider);
        }
        if (ob.isJsonPrimitive()) {

            final JsonPrimitive ob1 = ob.getAsJsonPrimitive();

            if (ob1.isBoolean()) {
                g.writeBoolean(ob1.getAsBoolean());
            }

            if (ob1.isString()) {
                g.writeString(ob1.getAsString());
            }

            if (ob1.isNumber()) {
                final Number num = ob1.getAsNumber();
                g.writeNumber(num.toString());
            }
        }
    }
}
