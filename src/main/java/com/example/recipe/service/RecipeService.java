package com.example.recipe.service;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.domain.Recipe;
import com.example.recipe.dto.RecipeDTO;

import java.util.List;
import java.util.Set;

public interface RecipeService  {

    Set<Recipe> getAllRecipes();

    Recipe getRecipeById(String id);

    RecipeCommand saveOrUpdateRecipe(RecipeCommand recipeCommand);

    RecipeCommand getRecipeCommonObjectById(String id);

    Recipe save(Recipe recipe);

    List<RecipeDTO> getRecipesByDescription(String description);

}
