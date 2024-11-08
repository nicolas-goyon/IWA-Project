package com.projet_iwa.ms_location.service;

import com.projet_iwa.ms_location.model.Category;
import com.projet_iwa.ms_location.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }


}

