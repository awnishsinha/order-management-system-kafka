package com.awnish.kafka;

import com.awnish.dto.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer {

   @KafkaListener(id = "inventoryListener", groupId = "inventory-group", topics = "order-created")
    public void listenOrder(OrderCreatedEvent event){
       System.out.println(event);
    }
}
