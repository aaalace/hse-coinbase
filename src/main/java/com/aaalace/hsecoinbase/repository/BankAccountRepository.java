package com.aaalace.hsecoinbase.repository;

import com.aaalace.hsecoinbase.domain.model.bankaccount.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class BankAccountRepository extends BaseRepository<BankAccount> {

    @Override
    protected UUID getId(BankAccount entity) {
        return entity.getId();
    }
}
