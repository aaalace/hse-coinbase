package com.aaalace.hsecoinbase.domain.model.category;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Category {
    private UUID id;
    private String name;
    private CategoryType type;
}
