package com.awnish.controller;

import com.awnish.kafka.OrderProducer;
import com.awnish.modal.Orders;
import com.awnish.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;
    private final OrderProducer orderProducer;

    public OrderController(OrderService orderService,OrderProducer orderProducer){
        this.orderService=orderService;
        this.orderProducer=orderProducer;
    }

    @PostMapping("/saveOrder")
    public ResponseEntity<String> saveOrder(@RequestBody Orders order) throws Exception {
        try{
            orderService.saveOrder(order);
            orderProducer.submitOrderEvent(order);

        }catch (Exception exception){
            throw new Exception(exception);
        }

        return new ResponseEntity<>("Order Saved Successfully",HttpStatus.OK);
    }
}
