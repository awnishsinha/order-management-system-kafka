package com.awnish.service;

import com.awnish.dto.OrderCreatedEvent;
import com.awnish.kafka.OrderProducer;
import com.awnish.modal.OrderItems;
import com.awnish.modal.Orders;
import com.awnish.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final AtomicInteger eventId = new AtomicInteger(0);

    public OrderService(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;

    }

    public void saveOrder(Orders order) {
        for (OrderItems item : order.getItems()) {
            item.setOrder(order);
        }
        Orders or = orderRepository.save(order);
        int eventIdd = eventId.addAndGet(1);
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(eventIdd, "OrderCreated", 1, LocalDateTime.now(), or.getId(), or.getUser_id(), or.getStatus(), or.getItems());
        orderProducer.submitOrderEvent(orderCreatedEvent);
    }



}


