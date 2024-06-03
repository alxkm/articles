package org.alx.article._40_decorator;

public class SimpleCoffee implements Coffee {

    @Override
    public double cost() {
        return 1.0; // $1 for a simple coffee
    }

    @Override
    public String getDescription() {
        return "Simple Coffee";
    }
}