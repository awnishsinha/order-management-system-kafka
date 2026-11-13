package com.awnish.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private Integer reservedQuantity;
    private Integer availableQuantity;
    private LocalDateTime occurredAt;

}
