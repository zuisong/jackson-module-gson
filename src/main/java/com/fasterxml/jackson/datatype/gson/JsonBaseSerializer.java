package com.fasterxml.jackson.datatype.gson;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;

abstract class JsonBaseSerializer<T> extends StdSerializer<T> {
    private static final long serialVersionUID = 1L;

    protected JsonBaseSerializer(Class<T> cls) {
        super(cls);
    }
}
