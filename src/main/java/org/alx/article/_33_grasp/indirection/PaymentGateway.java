package org.alx.article._33_grasp.indirection;

public class PaymentGateway {
    private PaymentProcessor paymentProcessor;

    public PaymentGateway(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void processPayment(PaymentRequest paymentRequest) {
        // Validate payment request
        if (isValidPaymentRequest(paymentRequest)) {
            // Delegate payment processing to PaymentProcessor
            paymentProcessor.processPayment(paymentRequest);
        } else {
            throw new IllegalArgumentException("Invalid payment request");
        }
    }

    private boolean isValidPaymentRequest(PaymentRequest paymentRequest) {
        // Validation logic for payment request
        return paymentRequest != null && paymentRequest.getAmount() > 0;
    }
}
