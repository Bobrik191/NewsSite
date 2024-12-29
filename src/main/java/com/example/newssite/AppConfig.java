package com.example.newssite;

import com.example.newssite.repository.CategoryRepository;
import com.example.newssite.repository.NewsRepository;
import com.example.newssite.service.CategoryService;
import com.example.newssite.service.NewsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
@Configuration
public class AppConfig {
    @Bean
    @Scope("prototype")
    public NewsService newsService(NewsRepository newsRepository, CategoryService categoryService) {
        return new NewsService(newsRepository, categoryService);
    }
    @Bean
    @Scope("prototype")
    public CategoryService categoryService(CategoryRepository categoryRepository, NewsRepository newsRepository) {
        return new CategoryService(categoryRepository, newsRepository);
    }
}
