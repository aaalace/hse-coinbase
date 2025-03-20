package com.aaalace.hsecoinbase.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseRepository<T> {

    protected final List<T> storage = new ArrayList<>();

    public void save(T entity) {
        storage.add(entity);

        // demo ---
        System.out.println("Entity: " + entity.toString());
        // demo ---
    }

    public Optional<T> findById(UUID id) {
        Optional<T> ent = storage.stream()
                .filter(entity -> getId(entity).equals(id))
                .findFirst();

        // demo ---
        ent.ifPresent(entity -> System.out.println("Entity: " + entity));
        // demo ---

        return ent;
    }

    public List<T> findAll() {
        return new ArrayList<>(storage);
    }

    public void deleteById(UUID id) {
        storage.removeIf(entity -> getId(entity).equals(id));
    }

    protected abstract UUID getId(T entity);
}
