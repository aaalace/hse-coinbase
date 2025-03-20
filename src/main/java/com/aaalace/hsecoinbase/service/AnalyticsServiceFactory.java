package com.aaalace.hsecoinbase.service;

import com.aaalace.hsecoinbase.domain.enums.AnalyticsType;
import com.aaalace.hsecoinbase.repository.OperationRepository;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsServiceFactory {

    private final OperationRepository operationRepository;

    public AnalyticsServiceFactory(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public AnalyticsService getService(AnalyticsType type) {
        return switch (type) {
            case COUNT -> new CountAnalyticsService(operationRepository);
            case PNL -> new PnlAnalyticsService(operationRepository);
        };
    }
}