package com.example.recipe.service;

import com.example.recipe.domain.Category;
import com.example.recipe.repositories.reactiveRepostories.CategoryReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryReactiveRepository categoryRepository;

    public CategoryServiceImpl(CategoryReactiveRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Flux<Category> fetchAllCategory() {
        return categoryRepository.findAll();
    }
}
