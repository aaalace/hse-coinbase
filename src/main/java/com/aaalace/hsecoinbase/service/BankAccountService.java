package com.aaalace.hsecoinbase.service;

import com.aaalace.hsecoinbase.domain.model.bankaccount.BankAccount;
import com.aaalace.hsecoinbase.repository.BankAccountRepository;
import com.aaalace.hsecoinbase.util.MeasureExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @MeasureExecutionTime
    public BankAccount getById(String id) {
        UUID accountId = UUID.fromString(id);
        return this.bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Bank account with id " + id + " not found"));
    }

    @MeasureExecutionTime
    public BankAccount register(BankAccount bankAccount) {
        log.info("Registering new bank account with id: {}", bankAccount.getId());
        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }

    @MeasureExecutionTime
    public BankAccount edit(BankAccount bankAccount) {
        log.info("Editing bank account with id: {}", bankAccount.getId());
        BankAccount existingAccount = bankAccountRepository.findById(bankAccount.getId())
                .orElseThrow(() -> new RuntimeException("Bank account with id " + bankAccount.getId() + " not found"));

        existingAccount.setName(bankAccount.getName());
        existingAccount.setBalance(bankAccount.getBalance());

        bankAccountRepository.save(existingAccount);
        return existingAccount;
    }

    @MeasureExecutionTime
    public void delete(String id) {
        UUID accountId = UUID.fromString(id);
        if (bankAccountRepository.findById(accountId).isEmpty()) {
            log.error("Bank account with id {} not found for deletion", id);
            throw new RuntimeException("Bank account with id " + id + " not found");
        }
        bankAccountRepository.deleteById(accountId);
        log.info("Bank account with id {} deleted", id);
    }
}