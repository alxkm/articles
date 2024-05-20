package org.alx.article._35_abstraction_extensibility_cohesion.extensibility;

public class DesignPatternsExample {
    // Factory Pattern
    interface Shape {
        void draw();
    }

    class Circle implements Shape {
        public void draw() {
            // Draw a circle
        }
    }

    class Rectangle implements Shape {
        public void draw() {
            // Draw a rectangle
        }
    }

    class ShapeFactory {
        Shape createShape(String type) {
            if (type.equals("circle")) {
                return new Circle();
            } else if (type.equals("rectangle")) {
                return new Rectangle();
            }
            return null;
        }
    }
}
