package com.example.newssite.service;

import com.example.newssite.model.Category;
import com.example.newssite.model.News;
import com.example.newssite.repository.CategoryRepository;
import com.example.newssite.repository.NewsRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final NewsRepository newsRepository;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public CategoryService(CategoryRepository categoryRepository, NewsRepository newsRepository) {
        this.categoryRepository = categoryRepository;
        this.newsRepository = newsRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category) {
        category.setId(idGenerator.getAndIncrement());
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.deleteById(id);
        List<News> newsToDelete = newsRepository.findByCategory(category);
        for (News news : newsToDelete) {
            newsRepository.deleteById(news.getId());
        }
    }
}