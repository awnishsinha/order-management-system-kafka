package com.awnish.kafka;

import com.awnish.modal.Orders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private final KafkaTemplate<String, Orders> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, Orders> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void submitOrderEvent(Orders order){
        kafkaTemplate.send("order-created",order);
    }
}
