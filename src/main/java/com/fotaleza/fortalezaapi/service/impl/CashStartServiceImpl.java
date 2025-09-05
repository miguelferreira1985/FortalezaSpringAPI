package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.CashStartRequestDTO;
import com.fotaleza.fortalezaapi.dto.CashStartResponseDTO;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.mapper.CashStartMapper;
import com.fotaleza.fortalezaapi.model.CashStart;
import com.fotaleza.fortalezaapi.repository.CashStartRepository;
import com.fotaleza.fortalezaapi.service.ICashStartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashStartServiceImpl implements ICashStartService {

    private CashStartRepository cashStartRepository;
    private CashStartMapper cashStartMapper;

    @Override
    @Transactional
    public CashStartResponseDTO createCashStart(CashStartRequestDTO cashStartRequestDTO) {
        if (cashStartRepository.findByEndDateTimeIsNull().isPresent()) {
            throw new ResourceAlreadyExistsException("Ya tiene una caja activa.");
        }

        CashStart newCashStart = cashStartMapper.toEntity(cashStartRequestDTO);
        CashStart savedCashStart = cashStartRepository.save(newCashStart);

        return cashStartMapper.toResponseDTO(savedCashStart);
    }

}
