package com.example.newssite.repository;

import com.example.newssite.model.News;
import com.example.newssite.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class NewsRepository {
    private final ConcurrentHashMap<Long, News> news = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public News save(News newsItem) {
        if (newsItem.getId() == null) {
            newsItem.setId(idGenerator.getAndIncrement());
        }
        news.put(newsItem.getId(), newsItem);
        return newsItem;
    }

    public News findById(Long id) {
        return news.get(id);
    }

    public List<News> findAll() {
        return new ArrayList<>(news.values());
    }

    public List<News> findByCategory(Category category) {
        return news.values().stream()
                .filter(newsItem -> newsItem.getCategory().getId().equals(category.getId()))
                .collect(Collectors.toList());
    }

    public List<News> findByTitleContaining(String keyword) {
        return news.values().stream()
                .filter(newsItem -> newsItem.getTitle().contains(keyword))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        news.remove(id);
    }
}
