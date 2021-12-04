package com.komputama.ecommerce.repository;

import com.komputama.ecommerce.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    @Query(value = "select * from item where transaction_id=?1 and product_id=?2", nativeQuery = true)
    Item findByTransactionIdAndProductId(String transactionId, String productId);

    List<Item> findByTransactionId(String transactionId);
}
