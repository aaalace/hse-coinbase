package com.aaalace.hsecoinbase.domain.model.bankaccount;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class BankAccount {
    private UUID id;
    private String name;
    private BigDecimal balance;
}
