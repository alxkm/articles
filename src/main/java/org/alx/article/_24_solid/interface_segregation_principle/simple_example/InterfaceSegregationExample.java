package org.alx.article._24_solid.interface_segregation_principle.simple_example;

interface Shape {
    double calculateArea();
}

class Circle implements Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle implements Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }
}

class Square implements Shape {
    private final double side;

    public Square(double side) {
        this.side = side;
    }

    // Not applicable for all shapes, violating the principle
    @Override
    public double calculateArea() {
        return side * side;
    }
}

public class InterfaceSegregationExample {
}
