package com.awnish.dto;

import com.awnish.modal.OrderItems;

import java.time.LocalDateTime;
import java.util.List;

public record OrderCreatedEvent (
        Integer eventId,
        String eventType,
        Integer eventVersion,
        LocalDateTime occurredAt,
        Integer orderId,
        Integer userId,
        String status,
        List<OrderItems> item

){

}
