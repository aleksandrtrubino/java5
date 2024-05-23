package ru.trubino;

import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {
    private final Properties properties;


    public Injector(Properties properties) {
        this.properties = properties;
    }

    public <T> T inject(T obj) throws Exception {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                Class<?> fieldType = field.getType();
                String implementationClassName = properties.getProperty(fieldType.getName());

                if (implementationClassName != null) {
                    Class<?> implementationClass = Class.forName(implementationClassName);
                    Object implementationInstance = implementationClass.getDeclaredConstructor().newInstance();

                    field.setAccessible(true);
                    field.set(obj, implementationInstance);
                }
            }
        }

        return obj;
    }


}