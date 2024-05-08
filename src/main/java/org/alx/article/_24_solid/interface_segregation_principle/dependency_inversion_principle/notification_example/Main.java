package org.alx.article._24_solid.interface_segregation_principle.dependency_inversion_principle.notification_example;

public class Main {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService();
        paymentService.processPayment(100.0);
    }
}