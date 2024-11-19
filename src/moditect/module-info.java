// Generated 27-Mar-2019 using Moditect maven plugin
module com.fasterxml.jackson.datatype.jsonorg {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    requires static com.google.gson;

    exports com.fasterxml.jackson.datatype.gson;

    provides com.fasterxml.jackson.databind.Module with
            com.fasterxml.jackson.datatype.gson.GsonModule;

}
