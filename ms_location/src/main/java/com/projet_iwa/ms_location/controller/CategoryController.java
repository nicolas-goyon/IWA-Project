package com.projet_iwa.ms_location.controller;



import com.projet_iwa.ms_location.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.projet_iwa.ms_location.model.Category;
import com.projet_iwa.ms_location.model.Location;

import com.projet_iwa.ms_location.service.LocationService;

import java.util.List;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/get_all")
    public List<Category> getAllCategory() {
        return categoryService.getAllCategories();
    }
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
    @PostMapping("/create")
    public void createCategory(@RequestBody Category category) {
        categoryService.saveCategory(category);
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }
}