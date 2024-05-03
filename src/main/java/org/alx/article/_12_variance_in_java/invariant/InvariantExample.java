package org.alx.article._12_variance_in_java.invariant;

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

public class InvariantExample {
    public static void main(String[] args) {
        Animal[] animals = new Dog[1]; // Compiler error
        animals[0] = new Animal(); // This would cause a runtime error
    }
}