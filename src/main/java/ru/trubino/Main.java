package ru.trubino;

import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(Injector.class.getClassLoader().getResourceAsStream("dependencies.properties"));

        SomeBean sb = (new Injector(properties)).inject(new SomeBean());
        sb.foo();
    }
}