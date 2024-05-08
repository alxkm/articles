package org.alx.article._24_solid.interface_segregation_principle.area_fixed_example;

interface AreaCalculatable {
    double calculateArea();
}

interface Shape {
    // Other shape-related methods
}

class Circle implements AreaCalculatable, Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle implements AreaCalculatable, Shape {
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

class Square implements AreaCalculatable, Shape {
    private final double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }
}

public class AreaFixedExample {
}
