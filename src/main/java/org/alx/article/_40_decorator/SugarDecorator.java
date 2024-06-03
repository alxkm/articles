package org.alx.article._40_decorator;

public class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    @Override
    public double cost() {
        return super.cost() + 0.5; // $0.5 for sugar
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Sugar";
    }
}
