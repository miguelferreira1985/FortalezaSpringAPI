package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.SaleRequestDTO;
import com.fotaleza.fortalezaapi.dto.SaleResponseDTO;

public interface ISaleService {

    SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO);

}
