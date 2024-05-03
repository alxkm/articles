package org.alx.article._12_variance_in_java.covariant.types;

import java.util.ArrayList;
import java.util.List;

class Animal {
    public String getType() {
        return "Animal";
    }
}

class Dog extends Animal {
    @Override
    public String getType() {
        return "Dog";
    }
}

public class CovariantTypesExample {
    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dog());

        // Output: Dog
        for (Animal animal : animals) {
            System.out.println(animal.getType());
        }
    }
}