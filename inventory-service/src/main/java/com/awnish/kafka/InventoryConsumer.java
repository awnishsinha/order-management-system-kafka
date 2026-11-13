package com.awnish.kafka;

import com.awnish.dto.OrderCreatedEvent;
import com.awnish.modal.OrderItems;
import com.awnish.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryConsumer {

    private final InventoryService inventoryService;

    public InventoryConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }




    @KafkaListener(id = "inventoryListener", groupId = "inventory-group", topics = "order-created")
    public void listenOrder(OrderCreatedEvent event) throws Exception {
        inventoryService.isItemAvailable(event);
    }



}
