package com.komputama.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

    @Id
    private String id;

    @Column(name = "name_user")
    private String nameUser;

    @Column(name = "date_buy")
    private Date dateBuy;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

}
