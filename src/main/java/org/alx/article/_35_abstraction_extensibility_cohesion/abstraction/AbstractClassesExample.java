package org.alx.article._35_abstraction_extensibility_cohesion.abstraction;

public class AbstractClassesExample {
    abstract class Shape {
        abstract double area(); // Abstract method
        abstract double perimeter(); // Abstract method
    }

    class Circle extends Shape {
        double radius;

        Circle(double radius) {
            this.radius = radius;
        }

        @Override
        double area() {
            return Math.PI * radius * radius;
        }

        @Override
        double perimeter() {
            return 2 * Math.PI * radius;
        }
    }
}
