package com.aaalace.hsecoinbase.service;

import com.aaalace.hsecoinbase.domain.model.category.Category;
import com.aaalace.hsecoinbase.repository.CategoryRepository;
import com.aaalace.hsecoinbase.util.MeasureExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @MeasureExecutionTime
    public Category getById(String id) {
        UUID categoryId = UUID.fromString(id);
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category with id " + id + " not found"));
    }

    @MeasureExecutionTime
    public Category register(Category category) {
        log.info("Registering new category with id: {}", category.getId());
        categoryRepository.save(category);
        return category;
    }

    @MeasureExecutionTime
    public Category edit(Category category) {
        log.info("Editing category with id: {}", category.getId());
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new RuntimeException("Category with id " + category.getId() + " not found"));

        existingCategory.setName(category.getName());

        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @MeasureExecutionTime
    public void delete(String id) {
        UUID categoryId = UUID.fromString(id);
        if (categoryRepository.findById(categoryId).isEmpty()) {
            log.error("Category with id {} not found for deletion", id);
            throw new RuntimeException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(categoryId);
        log.info("Category with id {} deleted", id);
    }
}