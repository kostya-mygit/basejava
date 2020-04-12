package ru.javaops.basejava;

import ru.javaops.basejava.model.Resume;

import java.lang.reflect.Field;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume("uuid1");

        Field field = r.getClass().getDeclaredFields()[0];
        System.out.println("field name: " + field.getName());
        field.setAccessible(true);
        System.out.println("field value: " + field.get(r));
        field.set(r, "uuid2");
        System.out.println("new field value: " + field.get(r));
    }
}