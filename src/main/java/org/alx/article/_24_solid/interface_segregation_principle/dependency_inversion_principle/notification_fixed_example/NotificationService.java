package org.alx.article._24_solid.interface_segregation_principle.dependency_inversion_principle.notification_fixed_example;

public class NotificationService implements NotificationSender {
    @Override
    public void sendNotification(String message) {
        // Logic to send notification
        System.out.println("Notification sent: " + message);
    }
}