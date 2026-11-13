package com.awnish.kafka;

import com.awnish.dto.InventoryRejectedEvent;
import com.awnish.dto.InventoryReservedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryRejectedProducer {

    private final KafkaTemplate<String, InventoryRejectedEvent> kafkaTemplate;


    public InventoryRejectedProducer(KafkaTemplate<String, InventoryRejectedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceInventoryRejectedEvent(InventoryRejectedEvent event){
        kafkaTemplate.send("inventory-rejected",event);
    }


}
