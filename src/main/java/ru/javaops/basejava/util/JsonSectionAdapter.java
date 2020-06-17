package ru.javaops.basejava.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonSectionAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive jsonPrimitive = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = jsonPrimitive.getAsString();
        try {
            Class<?> clazz = Class.forName(className);
            return ctx.deserialize(jsonObject.get(INSTANCE), clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public JsonElement serialize(T section, Type type, JsonSerializationContext ctx) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, section.getClass().getName());
        JsonElement jsonElement = ctx.serialize(section);
        jsonObject.add(INSTANCE, jsonElement);
        return jsonObject;
    }
}
