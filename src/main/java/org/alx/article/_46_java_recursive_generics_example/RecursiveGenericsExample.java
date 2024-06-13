package org.alx.article._46_java_recursive_generics_example;

public class RecursiveGenericsExample {
    public static void main(String[] args) {
        Person person = Person.builder()
                .firstName("John")
                .lastName("Doe")
                .age(30)
                .build();

        System.out.println(person);
    }
}