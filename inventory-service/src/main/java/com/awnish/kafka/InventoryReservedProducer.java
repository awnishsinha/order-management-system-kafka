package com.awnish.kafka;

import com.awnish.dto.InventoryReservedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryReservedProducer {

    private final KafkaTemplate<String, InventoryReservedEvent> kafkaTemplate;


    public InventoryReservedProducer(KafkaTemplate<String, InventoryReservedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceInventoryReservedEvent(InventoryReservedEvent event){
        kafkaTemplate.send("inventory-reserved",event);
    }


}
