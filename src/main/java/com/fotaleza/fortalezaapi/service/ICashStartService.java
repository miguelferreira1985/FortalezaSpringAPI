package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.request.CashStartRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.CashStartResponseDTO;

public interface ICashStartService {

    CashStartResponseDTO createCashStart(CashStartRequestDTO cashStartRequestDTO);

}
