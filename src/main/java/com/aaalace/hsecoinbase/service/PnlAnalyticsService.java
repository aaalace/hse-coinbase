package com.aaalace.hsecoinbase.service;

import com.aaalace.hsecoinbase.domain.model.operation.OperationType;
import com.aaalace.hsecoinbase.repository.OperationRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PnlAnalyticsService implements AnalyticsService {

    private final OperationRepository operationRepository;

    public PnlAnalyticsService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public void fetchData(String parameters) {
        BigDecimal totalAmount = operationRepository.findAll().stream()
                .map(operation -> operation.getType() == OperationType.Income
                        ? operation.getAmount()
                        : operation.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total PnL: " + totalAmount);
    }
}