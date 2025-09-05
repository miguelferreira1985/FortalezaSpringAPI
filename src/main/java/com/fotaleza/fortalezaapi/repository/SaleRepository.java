package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.enums.PaymentType;
import com.fotaleza.fortalezaapi.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sales s WHERE s.cashStart.id = :cashStartId AND s.paymentType = :paymentType")
    BigDecimal sumTotalAmountByCashStartIdAndPaymentType(@Param("cashStartId") Integer cashStartId, @Param("paymentType")PaymentType paymentType);

}
