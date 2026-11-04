package com.awnish.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import javax.sound.midi.Sequence;

@Data
@Entity
@Table(name="orderitems")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // Maps the foreign key column 'order_id' to the Order object
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders order;
    private Integer product_id;
    private Integer quantity;
    private Double price;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
