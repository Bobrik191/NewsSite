package com.example.newssite.repository;

import com.example.newssite.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CategoryRepository {
    private final ConcurrentHashMap<Long, Category> categories = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Category save(Category category) {
        if (category.getId() == null) {
            category.setId(idGenerator.getAndIncrement());
        }
        categories.put(category.getId(), category);
        return category;
    }

    public Category findById(Long id) {
        return categories.get(id);
    }

    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    public void deleteById(Long id) {
        categories.remove(id);
    }
}
