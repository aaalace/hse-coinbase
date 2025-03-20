package com.aaalace.hsecoinbase.repository;

import com.aaalace.hsecoinbase.domain.model.operation.Operation;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class OperationRepository extends BaseRepository<Operation> {

    @Override
    protected UUID getId(Operation entity) {
        return entity.getId();
    }
}
