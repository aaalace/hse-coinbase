package com.aaalace.hsecoinbase.service;

import com.aaalace.hsecoinbase.domain.model.bankaccount.BankAccount;
import com.aaalace.hsecoinbase.domain.model.operation.Operation;
import com.aaalace.hsecoinbase.domain.model.operation.OperationType;
import com.aaalace.hsecoinbase.repository.OperationRepository;
import com.aaalace.hsecoinbase.util.MeasureExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;
    private final BankAccountService bankAccountService;

    @MeasureExecutionTime
    public Operation getById(String id) {
        UUID operationId = UUID.fromString(id);
        return operationRepository.findById(operationId)
                .orElseThrow(() -> new RuntimeException("Operation with id " + id + " not found"));
    }

    @MeasureExecutionTime
    public Operation register(Operation operation) {
        log.info("Registering new operation with id: {}", operation.getId());

        BankAccount account = this.bankAccountService.getById(operation.getBankAccountId().toString());
        BigDecimal nb;
        if (operation.getType() == OperationType.Income) {
            nb = account.getBalance().add(operation.getAmount());
        } else {
            nb = account.getBalance().subtract(operation.getAmount());
        }
        account.setBalance(nb);
        bankAccountService.edit(account);

        operationRepository.save(operation);
        return operation;
    }

    @MeasureExecutionTime
    public Operation edit(Operation operation) {
        log.info("Editing operation with id: {}", operation.getId());
        Operation existingOperation = operationRepository.findById(operation.getId())
                .orElseThrow(() -> new RuntimeException("Operation with id " + operation.getId() + " not found"));

        existingOperation.setAmount(operation.getAmount());
        existingOperation.setDescription(operation.getDescription());

        operationRepository.save(existingOperation);
        return existingOperation;
    }

    @MeasureExecutionTime
    public void delete(String id) {
        UUID operationId = UUID.fromString(id);
        if (!operationRepository.findById(operationId).isPresent()) {
            log.error("Operation with id {} not found for deletion", id);
            throw new RuntimeException("Operation with id " + id + " not found");
        }
        operationRepository.deleteById(operationId);
        log.info("Operation with id {} deleted", id);
    }
}