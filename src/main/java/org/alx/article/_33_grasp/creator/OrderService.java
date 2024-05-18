package org.alx.article._33_grasp.creator;

import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(Customer customer, List<Product> products) {
        // Create a new order object
        Order order = new Order(customer, products);

        // Save the order to the repository
        orderRepository.saveOrder(order);
    }
}