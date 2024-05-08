package org.alx.article._24_solid.open_closed_principle.strategy_example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface Shape {
    // Define common methods for shapes
}

interface ShapeAreaStrategy {
    double calculateArea(Shape shape);
}

class ShapeManager {
    private final Map<Class<? extends Shape>, ShapeAreaStrategy> strategies;

    public ShapeManager(Map<Class<? extends Shape>, ShapeAreaStrategy> strategies) {
        this.strategies = strategies;
    }

    public double getMinArea(List<? extends Shape> shapes) {
        return shapes.stream()
                .map(shape -> {
                    ShapeAreaStrategy strategy = strategies.get(shape.getClass());
                    if (strategy != null) {
                        return strategy.calculateArea(shape);
                    }
                    throw new IllegalArgumentException("Could not find Strategy for " + shape.getClass().getSimpleName());
                })
                .min(Double::compare)
                .orElseThrow(() -> new IllegalArgumentException("List of shapes is empty"));
    }
}

class Rect implements Shape {
    private final double width;
    private final double height;

    public Rect(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}

class Square implements Shape {
    private final double width;

    public Square(double width) {
        this.width = width;
    }

    public double getWidth() {
        return width;
    }
}

class Circle implements Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}

class ShapeAreaStrategyRect implements ShapeAreaStrategy {
    @Override
    public double calculateArea(Shape shape) {
        Rect rect = (Rect) shape;
        return rect.getWidth() * rect.getHeight();
    }
}

class ShapeAreaStrategySquare implements ShapeAreaStrategy {
    @Override
    public double calculateArea(Shape shape) {
        Square square = (Square) shape;
        return Math.pow(square.getWidth(), 2);
    }
}

class ShapeAreaStrategyCircle implements ShapeAreaStrategy {
    @Override
    public double calculateArea(Shape shape) {
        Circle circle = (Circle) shape;
        return Math.PI * Math.pow(circle.getRadius(), 2);
    }
}

public class Main {
    public static void main(String[] args) {
        Map<Class<? extends Shape>, ShapeAreaStrategy> strategies = new HashMap<>();
        strategies.put(Rect.class, new ShapeAreaStrategyRect());
        strategies.put(Square.class, new ShapeAreaStrategySquare());
        strategies.put(Circle.class, new ShapeAreaStrategyCircle());

        List<Shape> shapes = List.of(
                new Rect(1, 2),
                new Square(1),
                new Circle(1)
        );

        ShapeManager shapeManager = new ShapeManager(strategies);
        System.out.println(shapeManager.getMinArea(shapes));
    }
}
