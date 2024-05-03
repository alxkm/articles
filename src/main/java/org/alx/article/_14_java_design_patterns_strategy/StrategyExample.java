package org.alx.article._14_java_design_patterns_strategy;

public class StrategyExample {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // Use credit card payment
        cart.setPaymentStrategy(new CreditCardPaymentStrategy("1234 5678 9012 3456", "12/24", "123"));
        cart.checkout(100.0);

        // Use PayPal payment
        cart.setPaymentStrategy(new PayPalPaymentStrategy("example@example.com", "password"));
        cart.checkout(200.0);
    }
}
