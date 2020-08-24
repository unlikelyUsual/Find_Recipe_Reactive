package com.example.recipe.service;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.domain.Recipe;
import com.example.recipe.dto.RecipeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService  {

    Flux<Recipe> getAllRecipes();

    Mono<Recipe> getRecipeById(String id);

    Mono<RecipeCommand> saveOrUpdateRecipe(RecipeCommand recipeCommand);

    Mono<RecipeCommand> getRecipeCommonObjectById(String id);

    Mono<Recipe> save(Recipe recipe);

    Flux<RecipeDTO> getRecipesByDescription(String description);

}
