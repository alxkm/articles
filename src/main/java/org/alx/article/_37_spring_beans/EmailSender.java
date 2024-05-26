package org.alx.article._37_spring_beans;

public class EmailSender implements Sender {
    @Override
    public void send(String message, String address) {
        System.out.println("Email sent to " + address);
    }

    public void init() {
    }

    public void destroy() {
    }
}
