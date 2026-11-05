package com.awnish.modal;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer user_id;
    private String status;
    private Double total_amount;
    // 'mappedBy' tells JPA that the 'order' field in the OrderItem class owns this relationship.
    // 'cascade' ensures that if you save/delete an Order, all its items are saved/deleted too.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> items = new ArrayList<>();
    private LocalDateTime created_at = LocalDateTime.now();


}
