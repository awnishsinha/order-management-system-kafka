package com.awnish.dto;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public record InventoryRejectedEvent(
        AtomicInteger eventId,
        String eventType,
        Integer eventVersion,
        LocalDateTime occurredAt,
        Integer inventoryId,
        Integer reservedQuantity,
        Integer availableQuantity,
        Integer productId
){}
