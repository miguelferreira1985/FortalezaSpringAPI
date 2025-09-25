package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.request.SaleRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.SaleResponseDTO;

public interface ISaleService {

    SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO);

}
