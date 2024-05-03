package org.alx.article._12_variance_in_java.covariant.simple;
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

public class CovariantExample {
    // Method that accepts a collection of Animal
    public static void processAnimals(List<? extends Animal> animals) {
        for (Animal animal : animals) {
            animal.makeSound();
        }
    }

    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog());

        // Pass a List<Dog> to a method that accepts List<? extends Animal>
        processAnimals(dogs);
    }
}