package com.example.recipe.service;

import com.example.recipe.domain.Ingredient;

import java.util.Set;

public interface IngredientService {
    Set<Ingredient> findAll();
}
