package com.example.newssite.controller;

import com.example.newssite.model.News;
import com.example.newssite.model.Category;
import com.example.newssite.service.NewsService;
import com.example.newssite.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NewsController {
    private final NewsService newsService;
    private final CategoryService categoryService;
    private boolean isAdmin = false;

    @Autowired //DI constructor
    public NewsController(NewsService newsService, CategoryService categoryService) {
        this.newsService = newsService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<News> allNews = newsService.getAllNews();
        model.addAttribute("news", allNews);
        List<Category> allCategories = categoryService.getAllCategories();
        model.addAttribute("categories", allCategories);
        model.addAttribute("isAdmin", isAdmin);
        return "home";
    }

    @GetMapping("/news/{id}")
    public String viewNews(@PathVariable Long id, Model model) {
        News news = newsService.getNewsById(id);
        model.addAttribute("news", news);
        return "news";
    }

    @GetMapping("/news/category/{categoryId}")
    public String viewNewsByCategory(@PathVariable Long categoryId, Model model) {
        List<News> news = newsService.getNewsByCategoryId(categoryId);
        model.addAttribute("news", news);
        return "category";
    }

    @GetMapping("/search")
    public String searchNews(@RequestParam String keyword, Model model) {
        List<News> news = newsService.searchNews(keyword);
        model.addAttribute("news", news);
        return "home";
    }

    @GetMapping("/changeStatus")
    public String changeUserStatus(Model model) {
        isAdmin = !isAdmin;
        model.addAttribute("isAdmin", isAdmin);
        return "redirect:/";
    }
}
