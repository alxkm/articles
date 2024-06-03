package org.alx.article._40_decorator;

public class Main {
    public static void main(String[] args) {
        Coffee simpleCoffee = new SimpleCoffee();
        System.out.println("Cost: $" + simpleCoffee.cost());
        System.out.println("Description: " + simpleCoffee.getDescription());

        // Decorate with milk
        Coffee milkCoffee = new MilkDecorator(simpleCoffee);
        System.out.println("Cost: $" + milkCoffee.cost());
        System.out.println("Description: " + milkCoffee.getDescription());

        // Decorate with milk and sugar
        Coffee milkAndSugarCoffee = new SugarDecorator(new MilkDecorator(simpleCoffee));
        System.out.println("Cost: $" + milkAndSugarCoffee.cost());
        System.out.println("Description: " + milkAndSugarCoffee.getDescription());
    }
}