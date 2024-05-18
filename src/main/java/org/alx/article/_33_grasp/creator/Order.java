package org.alx.article._33_grasp.creator;

import java.util.List;

public class Order {
    private final Customer customer;
    private final List<Product> products;

    public Order(Customer customer, List<Product> products) {
        this.customer = customer;
        this.products = products;
    }
}
