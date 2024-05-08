package org.alx.article._24_solid.interface_segregation_principle.dependency_inversion_principle.notification_example;

public class PaymentService {
    private NotificationService notificationService;

    public PaymentService() {
        this.notificationService = new NotificationService(); // Direct dependency
    }

    public void processPayment(double amount) {
        // Logic to process payment
        System.out.println("Payment processed: $" + amount);
        notificationService.sendNotification("Payment of $" + amount + " processed successfully");
    }
}