package com.example.recipe.service;

import com.example.recipe.domain.Category;
import reactor.core.publisher.Flux;

public interface CategoryService {

    Flux<Category> fetchAllCategory();

}
