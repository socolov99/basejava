package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Resume resume = new Resume();

        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        field.set(resume, "new_uuid");
        System.out.println(field.get(resume) + "\n");

        // invoking resume.toString via reflection
        Method method = resume.getClass().getMethod("toString");
        method.setAccessible(true);
        System.out.println(method.getName());
        System.out.println(method.invoke(resume));
    }
}
