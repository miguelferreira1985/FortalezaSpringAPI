package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Integer> {

    List<PurchaseOrderItem> findByPurchaseOrderId(Integer purchaseOrderId);

}
