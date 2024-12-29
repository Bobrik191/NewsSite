package com.example.newssite.controller;

import com.example.newssite.model.News;
import com.example.newssite.model.Category;
import com.example.newssite.service.NewsService;
import com.example.newssite.service.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {
    @Autowired //DI field
    private NewsService newsService;

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<News> allNews = newsService.getAllNews();
        model.addAttribute("news", allNews);
        List<Category> allCategories = categoryService.getAllCategories();
        model.addAttribute("categories", allCategories);
        return "admin";
    }

    @GetMapping("/admin/createForm")
    public String createForm(@RequestParam String formType, Model model) {
        List<Category> categories = categoryService.getAllCategories();
        switch (formType) {
            case "news":
                model.addAttribute("categories", categories);
                model.addAttribute("news", new News());
                break;
            case "category":
                model.addAttribute("category", new Category());
                break;
            default:
                break;
        }
        model.addAttribute("formType", formType);
        return "createForm";
    }

    @PostMapping("/admin/createNews")
    public String createNews(@ModelAttribute News news) {
        newsService.createNews(news);
        return "redirect:/";
    }

    @PostMapping("/admin/createCategory")
    public String createCategory(@ModelAttribute Category category) {
        categoryService.createCategory(category);
        return "redirect:/";
    }
    @PostMapping("/admin/deleteNews/{id}")
    public String deleteNews(@PathVariable Long id) {
        newsService.deleteNewsById(id);
        return "redirect:/";
    }

    @PostMapping("/admin/deleteCategory/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/";
    }
}