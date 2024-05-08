package org.alx.article._24_solid.interface_segregation_principle.dependency_inversion_principle.notification_fixed_example;

public class Main {
    public static void main(String[] args) {
        NotificationSender notificationSender = new NotificationService();
        PaymentService paymentService = new PaymentService(notificationSender);
        paymentService.processPayment(100.0);
    }
}