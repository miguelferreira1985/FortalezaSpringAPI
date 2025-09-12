package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.request.CashOutFlowRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.CashOutFlowResponseDTO;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.CashOutFlowMapper;
import com.fotaleza.fortalezaapi.model.CashOutFlow;
import com.fotaleza.fortalezaapi.model.CashStart;
import com.fotaleza.fortalezaapi.repository.CashOutFlowRepository;
import com.fotaleza.fortalezaapi.repository.CashStartRepository;
import com.fotaleza.fortalezaapi.service.ICashOutFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashOutFlowServiceImpl implements ICashOutFlowService {

    private final CashOutFlowRepository cashOutFlowRepository;
    private final CashStartRepository cashStartRepository;
    private final CashOutFlowMapper cashOutFlowMapper;

    @Override
    public CashOutFlowResponseDTO createCashOutFlow(CashOutFlowRequestDTO cashOutFlowRequestDTO) {

        CashStart activeCashStart = cashStartRepository.findByEndDateTimeIsNull()
                .orElseThrow(() -> new ResourceNotFoundException("No hay una caja activa."));

        CashOutFlow newCashOutFlow = cashOutFlowMapper.toEntity(cashOutFlowRequestDTO);
        newCashOutFlow.setCashStart(activeCashStart);

        CashOutFlow savedCashOutFlow = cashOutFlowRepository.save(newCashOutFlow);

        return cashOutFlowMapper.toResponseDTO(savedCashOutFlow);
    }
}
