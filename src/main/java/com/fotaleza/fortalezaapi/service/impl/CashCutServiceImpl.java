package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.CashCutReportDTO;
import com.fotaleza.fortalezaapi.dto.CashCutRequestDTO;
import com.fotaleza.fortalezaapi.enums.PaymentType;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.CashCutMapper;
import com.fotaleza.fortalezaapi.model.CashCut;
import com.fotaleza.fortalezaapi.model.CashOutFlow;
import com.fotaleza.fortalezaapi.model.CashStart;
import com.fotaleza.fortalezaapi.repository.CashCutRepository;
import com.fotaleza.fortalezaapi.repository.CashOutFlowRepository;
import com.fotaleza.fortalezaapi.repository.CashStartRepository;
import com.fotaleza.fortalezaapi.repository.SaleRepository;
import com.fotaleza.fortalezaapi.service.ICashCutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CashCutServiceImpl implements ICashCutService {

    private final CashStartRepository cashStartRepository;
    private final CashCutRepository cashCutRepository;
    private final SaleRepository saleRepository;
    private final CashOutFlowRepository cashOutFlowRepository;
    private final CashCutMapper cashCutMapper;

    @Override
    @Transactional
    public CashCutReportDTO performCashCut(CashCutRequestDTO cashCutRequestDTO) {

        CashStart activeCashStart = cashStartRepository.findByEndDateTimeIsNull()
                .orElseThrow(() -> new ResourceNotFoundException("No hay caja activa."));

        // 1. Calcular totales de ventas por tipo de pago
        BigDecimal totalCashSales = saleRepository.sumTotalAmountByCashStartIdAndPaymentType(activeCashStart.getId(), PaymentType.EFECTIVO);
        BigDecimal totalCardSales = saleRepository.sumTotalAmountByCashStartIdAndPaymentType(activeCashStart.getId(), PaymentType.TARJETA);
        BigDecimal totalTransferSales = saleRepository.sumTotalAmountByCashStartIdAndPaymentType(activeCashStart.getId(), PaymentType.TRANSFERENCIA);

        // 2. Calcular total de salidas de efectivo
        BigDecimal totalOutFlows = cashOutFlowRepository.findByCashStartId(activeCashStart.getId())
                .stream()
                .map(CashOutFlow::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. Calcular monto esperado de efectivo en la caja
        BigDecimal expectedCashAmount = activeCashStart.getStartAmount().add(totalCashSales).subtract(totalOutFlows);

        // 4. Crear y guardar el cash out
        BigDecimal cashDifference = cashCutRequestDTO.getFinalAmount().subtract(expectedCashAmount);
        CashCut newCashCut = new CashCut();
        newCashCut.setCashStart(activeCashStart);
        newCashCut.setStartAmount(activeCashStart.getStartAmount());
        newCashCut.setFinalAmount(cashCutRequestDTO.getFinalAmount());
        newCashCut.setDifference(cashDifference);

        cashCutRepository.save(newCashCut);

        // 5. Finalizar el cash start
        activeCashStart.setEndDateTime(LocalDateTime.now());
        activeCashStart.setFinalAmount(cashCutRequestDTO.getFinalAmount());
        activeCashStart.setDifference(cashDifference);

        cashStartRepository.save(activeCashStart);

        // 6. Construir y devolver el reporte
        CashCutReportDTO report = new CashCutReportDTO();
        report.setCashStartId(activeCashStart.getId());
        report.setStartAmount(activeCashStart.getStartAmount());
        report.setStartDateTime(activeCashStart.getCreatedDateTime());
        report.setFinalAmount(cashCutRequestDTO.getFinalAmount());
        report.setExpectedCashAmount(expectedCashAmount);
        report.setCashDifference(cashDifference);
        report.setCutDateTime(LocalDateTime.now());
        report.setTotalCashSales(totalCashSales);
        report.setTotalCardSales(totalCardSales);
        report.setTotalTransferSales(totalTransferSales);
        report.setTotalOutFlows(totalOutFlows);


        return report;
    }
}
