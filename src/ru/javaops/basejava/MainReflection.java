package ru.javaops.basejava;

import ru.javaops.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("uuid1");

        Field field = r.getClass().getDeclaredFields()[0];
        System.out.println("field name: " + field.getName());
        field.setAccessible(true);
        System.out.println("field value: " + field.get(r));
        field.set(r, "uuid2");
        System.out.println("new field value: " + field.get(r));

        Method method = r.getClass().getMethod("toString");
        System.out.println("method name: " + method.getName());
        System.out.println("method invocation: " + method.invoke(r));
    }
}