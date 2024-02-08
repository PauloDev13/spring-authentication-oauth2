package com.devpgm.springoauth2.controller;

import com.devpgm.springoauth2.entity.Order;
import com.devpgm.springoauth2.entity.OrderItem;
import com.devpgm.springoauth2.repositoriy.OrderItemRepository;
import com.devpgm.springoauth2.repositoriy.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    public OrderController(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping // manager access
    @RequestMapping("/{restaurantId}/list")
    public List<Order> getOrders(@PathVariable Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    @GetMapping // manager can access
    @RequestMapping("/{orderId}")
    public Order getOrderDetails(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setOrderItems(orderItemRepository.findByOrderId(order.getId()));
        return order;
    }

    @PostMapping // authenticated users can access
    public Order createOrder(@RequestBody Order order) {
        orderRepository.save(order);
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem -> {
            orderItem.setId(order.id);
            orderItemRepository.save(orderItem);
        });

        return order;
    }


}
