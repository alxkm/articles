package org.alx.article._24_solid.interface_segregation_principle.dependency_inversion_principle.notification_fixed_example;

public class PaymentService {
    private NotificationSender notificationSender;

    public PaymentService(NotificationSender notificationSender) {
        this.notificationSender = notificationSender; // Dependency injection via constructor
    }

    public void processPayment(double amount) {
        // Logic to process payment
        System.out.println("Payment processed: $" + amount);
        notificationSender.sendNotification("Payment of $" + amount + " processed successfully");
    }
}