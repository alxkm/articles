package org.alx.article._12_variance_in_java.contravariant_super_syntax;

import java.util.ArrayList;
import java.util.List;

class Animal {
    public void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}

public class ContravariantSuperSyntaxExample {
    public static void main(String[] args) {
        List<? super Dog> animals = new ArrayList<Animal>(); // List of a supertype of Dog
        animals.add(new Dog()); // This is valid
        //animals.add(new Animal()); // This is not valid
        // Dog d = animals.get(0); // Compilation error, can't guarantee the exact type
        Object o = animals.get(0); // Valid, since any supertype of Dog can be assigned to Object
    }
}