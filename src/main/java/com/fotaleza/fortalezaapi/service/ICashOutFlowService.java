package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.CashOutFlowRequestDTO;
import com.fotaleza.fortalezaapi.dto.CashOutFlowResponseDTO;

public interface ICashOutFlowService {

    CashOutFlowResponseDTO createCashOutFlow(CashOutFlowRequestDTO cashOutFlowRequestDTO);

}
