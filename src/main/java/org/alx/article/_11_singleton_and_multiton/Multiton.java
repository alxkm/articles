package org.alx.article._11_singleton_and_multiton;

import java.util.HashMap;
import java.util.Map;

public class Multiton {
    private static Map<String, Multiton> instances = new HashMap<>();
    private String name;

    private Multiton(String name) {
        this.name = name;
    }

    public static Multiton getInstance(String name) {
        if (!instances.containsKey(name)) {
            synchronized (Multiton.class) {
                if (!instances.containsKey(name)) {
                    instances.put(name, new Multiton(name));
                }
            }
        }
        return instances.get(name);
    }

    public void doSomething() {
        System.out.println("I am " + name);
    }
}