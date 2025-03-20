package com.aaalace.hsecoinbase.service;

import com.aaalace.hsecoinbase.repository.OperationRepository;
import org.springframework.stereotype.Service;

public class CountAnalyticsService implements AnalyticsService {

    private final OperationRepository operationRepository;

    public CountAnalyticsService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public void fetchData(String parameters) {
        long count = operationRepository.findAll().size();

        System.out.println("Total number of operations: " + count);
    }
}