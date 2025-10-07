package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.enums.EPurchaseOrderStatus;
import com.fotaleza.fortalezaapi.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    List<PurchaseOrder> findByOrderStatus(EPurchaseOrderStatus orderStatus);
    List<PurchaseOrder> findBySupplierId(Integer supplierId);

}
