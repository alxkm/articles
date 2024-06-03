package org.alx.article._40_decorator;

public class MilkDecorator extends CoffeeDecorator {

    public MilkDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    @Override
    public double cost() {
        return super.cost() + 0.5; // $0.5 for milk
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Milk";
    }
}