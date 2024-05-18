package org.alx.article._33_grasp.indirection;

public class PaymentRequest {
    private final int amount;

    public PaymentRequest(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
