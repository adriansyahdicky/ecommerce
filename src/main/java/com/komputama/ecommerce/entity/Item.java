package com.komputama.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item {

    @Id
    private String id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "transaction_id")
    private String transactionId;

    private BigDecimal price;

    private int qty;

}
