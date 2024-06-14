package org.alx.article._47_chain_of_responsibility;

// Define the base interface for request handlers
interface PurchaseApprover {
    void setNextApprover(PurchaseApprover nextApprover);
    void processRequest(PurchaseRequest request);
}

// Concrete implementation of PurchaseApprover
class Manager implements PurchaseApprover {
    private double purchasingLimit;
    private PurchaseApprover nextApprover;

    public Manager(double purchasingLimit) {
        this.purchasingLimit = purchasingLimit;
    }

    @Override
    public void setNextApprover(PurchaseApprover nextApprover) {
        this.nextApprover = nextApprover;
    }

    @Override
    public void processRequest(PurchaseRequest request) {
        if (request.getAmount() <= purchasingLimit) {
            System.out.println("Manager approved purchase of $" + request.getAmount());
        } else if (nextApprover != null) {
            nextApprover.processRequest(request);
        } else {
            System.out.println("Purchase request for $" + request.getAmount() + " exceeds approval limit.");
        }
    }
}

// Purchase request class
class PurchaseRequest {
    private double amount;

    public PurchaseRequest(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

public class ChainOfResponsibilityExample {
    public static void main(String[] args) {
        // Create chain of approvers
        PurchaseApprover manager = new Manager(1000);
        PurchaseApprover director = new Manager(5000);
        PurchaseApprover vp = new Manager(10000);

        // Set up the chain of responsibility
        manager.setNextApprover(director);
        director.setNextApprover(vp);

        // Process purchase requests
        manager.processRequest(new PurchaseRequest(500));
        manager.processRequest(new PurchaseRequest(2500));
        manager.processRequest(new PurchaseRequest(7500));
        manager.processRequest(new PurchaseRequest(15000));
    }
}
