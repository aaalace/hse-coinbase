package com.aaalace.hsecoinbase.repository;

import com.aaalace.hsecoinbase.domain.model.category.Category;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CategoryRepository extends BaseRepository<Category> {

    @Override
    protected UUID getId(Category entity) {
        return entity.getId();
    }
}