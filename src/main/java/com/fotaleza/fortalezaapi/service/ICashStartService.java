package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.CashStartRequestDTO;
import com.fotaleza.fortalezaapi.dto.CashStartResponseDTO;

public interface ICashStartService {

    CashStartResponseDTO createCashStart(CashStartRequestDTO cashStartRequestDTO);

}
