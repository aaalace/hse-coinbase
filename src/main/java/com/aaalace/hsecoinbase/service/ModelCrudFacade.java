package com.aaalace.hsecoinbase.service;

import com.aaalace.hsecoinbase.domain.enums.CrudModelType;
import com.aaalace.hsecoinbase.domain.enums.CrudOperation;
import com.aaalace.hsecoinbase.domain.model.bankaccount.BankAccount;
import com.aaalace.hsecoinbase.domain.model.category.Category;
import com.aaalace.hsecoinbase.domain.model.operation.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelCrudFacade {

    private final BankAccountService bankAccountService;
    private final CategoryService categoryService;
    private final OperationService operationService;

    @Autowired
    public ModelCrudFacade(
            BankAccountService bankAccountService,
            CategoryService categoryService,
            OperationService operationService
    ) {
        this.bankAccountService = bankAccountService;
        this.categoryService = categoryService;
        this.operationService = operationService;
    }

    public void performCrudOperation(CrudModelType model, CrudOperation op, Object entity) {
        switch (model) {
            case CrudModelType.BANK_ACCOUNT -> performBankAccountCrud(op, (BankAccount) entity);
            case CrudModelType.CATEGORY -> performCategoryCrud(op, (Category) entity);
            case CrudModelType.OPERATION -> performOperationCrud(op, (Operation) entity);
            default -> throw new IllegalArgumentException("Unsupported model: " + model);
        }
    }

    private void performBankAccountCrud(CrudOperation op, BankAccount bankAccount) {
        switch (op) {
            case GET -> bankAccountService.getById(bankAccount.getId().toString());
            case CREATE -> bankAccountService.register(bankAccount);
            case UPDATE -> bankAccountService.edit(bankAccount);
            case DELETE -> bankAccountService.delete(bankAccount.getId().toString());
        }
    }

    private void performCategoryCrud(CrudOperation op, Category category) {
        switch (op) {
            case GET -> categoryService.getById(category.getId().toString());
            case CREATE -> categoryService.register(category);
            case UPDATE -> categoryService.edit(category);
            case DELETE -> categoryService.delete(category.getId().toString());
        }
    }

    private void performOperationCrud(CrudOperation op, Operation operation) {
        switch (op) {
            case GET -> operationService.getById(operation.getId().toString());
            case CREATE -> operationService.register(operation);
            case UPDATE -> operationService.edit(operation);
            case DELETE -> operationService.delete(operation.getId().toString());
        }
    }
}