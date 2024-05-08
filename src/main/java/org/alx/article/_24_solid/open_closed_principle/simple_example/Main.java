package org.alx.article._24_solid.open_closed_principle.simple_example;

// Interface defining a shape
interface Shape {
    double area();
}

// Concrete implementation of Shape for a rectangle
class Rectangle implements Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }
}

// Concrete implementation of Shape for a circle
class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

// Utility class to calculate total area of shapes
class AreaCalculator {
    public static double calculateTotalArea(Shape[] shapes) {
        double totalArea = 0.0;
        for (Shape shape : shapes) {
            totalArea += shape.area();
        }
        return totalArea;
    }
}

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[] {
                new Rectangle(5, 4),
                new Circle(3)
        };

        double totalArea = AreaCalculator.calculateTotalArea(shapes);
        System.out.println("Total area of shapes: " + totalArea);
    }
}