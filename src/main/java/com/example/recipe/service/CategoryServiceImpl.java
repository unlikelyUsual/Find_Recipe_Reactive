package com.example.recipe.service;

import com.example.recipe.domain.Category;
import com.example.recipe.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> fetchAllCategory() {
        List<Category> categories  = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }
}
