package org.alx.article._35_abstraction_extensibility_cohesion.extensibility;

public class AbstractClassesExample {
    abstract class Animal {
        abstract void makeSound();
    }

    class Dog extends Animal {
        void makeSound() {
            // Implementation for Dog's sound
        }
    }

    class Cat extends Animal {
        void makeSound() {
            // Implementation for Cat's sound
        }
    }
}
