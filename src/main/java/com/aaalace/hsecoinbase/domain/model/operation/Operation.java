package com.aaalace.hsecoinbase.domain.model.operation;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class Operation {
    private UUID id;
    private OperationType type;
    private BigDecimal amount;
    private String description;

    @Builder.Default
    private Date date = new Date();

    private UUID bankAccountId;
    private UUID categoryId;
}
