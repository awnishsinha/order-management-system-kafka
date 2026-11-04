package com.awnish.service;

import com.awnish.modal.OrderItems;
import com.awnish.modal.Orders;
import com.awnish.repository.OrderItemsRepository;
import com.awnish.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;

    public OrderService(OrderRepository orderRepository,OrderItemsRepository orderItemsRepository) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository=orderItemsRepository;
    }

    public void saveOrder(Orders order){
        orderRepository.save(order);

    }

    public void saveOrderItem(OrderItems items){
        orderItemsRepository.save(items);
    }


}


