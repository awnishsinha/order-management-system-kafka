package com.awnish.service;

import com.awnish.dto.InventoryRejectedEvent;
import com.awnish.dto.InventoryReservedEvent;
import com.awnish.dto.OrderCreatedEvent;
import com.awnish.kafka.InventoryRejectedProducer;
import com.awnish.kafka.InventoryReservedProducer;
import com.awnish.modal.Inventory;
import com.awnish.modal.OrderItems;
import com.awnish.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final AtomicInteger eventId = new AtomicInteger(0);
    private final InventoryReservedProducer reservedProducer;
    private final InventoryRejectedProducer rejectedProducer;
    private final AtomicInteger inventRejectedEventId = new AtomicInteger(0);

    public InventoryService(InventoryRepository inventoryRepository,InventoryReservedProducer producer,InventoryRejectedProducer rejectedProducer) {
        this.inventoryRepository = inventoryRepository;
        this.reservedProducer=producer;
        this.rejectedProducer=rejectedProducer;
    }


    public String isItemAvailable(OrderCreatedEvent event){
        event.item().forEach(item -> {
            int id = item.getProduct_id();
            log.info("Product id is:- {}", id);
            Optional<Inventory> inventory = inventoryRepository.findByProductId(id);
            System.out.print(inventory.orElseThrow(() -> new RuntimeException("Product not found")));

            AtomicInteger reservedItem = new AtomicInteger(inventory.get().getReservedQuantity());
            log.info("Initial ReservedQuantity: {}", reservedItem);

            int availableItem = inventory.get().getAvailableQuantity();
            log.info("Initial AvailableItem:- {}", availableItem);
            int quantity = item.getQuantity();
            int temp = reservedItem.addAndGet(quantity);
            log.info("temp is:- {}", temp);

            if (temp > availableItem) {
                reservedItem.addAndGet(-quantity);

                generateInventoryRejectedEvent(inventory.get());
                log.info("reservedItem value is:- {}", reservedItem.get());
                log.info("item not available:- {}", inventory.get().getId()) ;
                return ;
            }

            log.info("inventory {}", id);
            log.info("reserved {}", reservedItem);
            inventory.get().setReservedQuantity(reservedItem.get());
            Inventory inventoryData = inventoryRepository.save(inventory.get());

            try {
                generateInventoryReservedEvent(inventoryData);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return "";
    }

    private void generateInventoryReservedEvent(Inventory inventory) throws Exception {
        try {
            eventId.addAndGet(1);
            InventoryReservedEvent inventoryReservedEvent = new InventoryReservedEvent(eventId, "inventory-reserved",
                    1, LocalDateTime.now(), inventory.getId(),
                    inventory.getReservedQuantity(), inventory.getAvailableQuantity(),
                    inventory.getProductId());

            reservedProducer.produceInventoryReservedEvent(inventoryReservedEvent);
        } catch (RuntimeException exception) {
            throw new Exception(exception);
        }

    }

    private void generateInventoryRejectedEvent(Inventory inventory) {

            inventRejectedEventId.addAndGet(1);
            InventoryRejectedEvent inventoryRejectedEvent = new InventoryRejectedEvent(inventRejectedEventId, "inventory-rejected",
                    1, LocalDateTime.now(), inventory.getId(),
                    inventory.getReservedQuantity(), inventory.getAvailableQuantity(),
                    inventory.getProductId());

            rejectedProducer.produceInventoryRejectedEvent(inventoryRejectedEvent);

    }

}
