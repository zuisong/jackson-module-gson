package com.fasterxml.jackson.datatype.gson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.io.IOException;

public class JsonElementSerializer extends StdSerializer<JsonElement> {
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

    public void serializeContents(JsonElement value, JsonGenerator g, SerializerProvider provider)
            throws IOException {
        if (value.isJsonNull()) {
            g.writeNull();
        }
        if (value.isJsonObject()) {
            JsonObjectSerializer.instance.serialize(value.getAsJsonObject(), g, provider);
        }
        if (value.isJsonArray()) {
            JsonArraySerializer.instance.serialize((JsonArray) value, g, provider);
        }
        if (value.isJsonPrimitive()) {

            final JsonPrimitive ob1 = value.getAsJsonPrimitive();

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
