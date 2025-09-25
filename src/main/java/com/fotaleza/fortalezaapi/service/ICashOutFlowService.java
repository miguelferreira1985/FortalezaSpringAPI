package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.request.CashOutFlowRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.CashOutFlowResponseDTO;

public interface ICashOutFlowService {

    CashOutFlowResponseDTO createCashOutFlow(CashOutFlowRequestDTO cashOutFlowRequestDTO);

}
