package org.alx.article._37_spring_beans;

public class EmailNotificationService {
    private final Sender sender;

    public EmailNotificationService(Sender sender) {
        this.sender = sender;
    }

    public void send(String message, String email) {
        sender.send(message, email);
    }
}