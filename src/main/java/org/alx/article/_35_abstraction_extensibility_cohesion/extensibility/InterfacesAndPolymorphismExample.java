package org.alx.article._35_abstraction_extensibility_cohesion.extensibility;

public class InterfacesAndPolymorphismExample {
    class Shape {
        void draw() {
            // Base implementation
        }
    }

    class Circle extends Shape {
        void draw() {
            // Custom implementation for Circle
        }
    }

    class Rectangle extends Shape {
        void draw() {
            // Custom implementation for Rectangle
        }
    }
}
