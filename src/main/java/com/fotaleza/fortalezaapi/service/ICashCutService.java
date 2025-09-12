package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.CashCutReportDTO;
import com.fotaleza.fortalezaapi.dto.request.CashCutRequestDTO;

public interface ICashCutService {

    CashCutReportDTO performCashCut(CashCutRequestDTO cashCutRequestDTO);

}
