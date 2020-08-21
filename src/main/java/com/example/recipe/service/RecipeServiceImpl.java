package com.example.recipe.service;


import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.domain.Recipe;
import com.example.recipe.dto.RecipeDTO;
import com.example.recipe.exceptions.NotFoundException;
import com.example.recipe.mappers.RecipeMapper;
import com.example.recipe.repositories.RecipeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public Set<Recipe> getAllRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add );
        return recipeSet;
    }

    @Override
    public Recipe getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if(recipe == null){
            throw new NotFoundException("Recipe With ID " + id + " Not Found");
        }
        return recipe;
    }

    @Override
    @Transactional
    public RecipeCommand saveOrUpdateRecipe(RecipeCommand recipeCommand) {
        if(recipeCommand.getId() != null){
            Recipe dbRecipe = this.getRecipeById(recipeCommand.getId());
            recipeCommand.setImage(dbRecipe.getImage());
            recipeCommand.setImageString(dbRecipe.getImageString());
        }
        Recipe recipe = recipeMapper.commandToEntity(recipeCommand);
        recipeCommand.getIngredients().forEach(recipe::addIngredient);
        Recipe saveRecipe = recipeRepository.save(recipe);
        return recipeMapper.entityToCommand(saveRecipe);
    }

    @Override
    public RecipeCommand getRecipeCommonObjectById(Long id) {
        Recipe recipe = this.getRecipeById(id);
        if(recipe == null) throw new NotFoundException("Recipe Not found");
        return recipeMapper.entityToCommand(recipe);
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public List<RecipeDTO> getRecipesByDescription(String description) {
        List<RecipeDTO> dtoList = new ArrayList<>();
        recipeRepository.findByDescriptionContainingIgnoreCase(description)
                .forEach(recipe -> {
                    RecipeDTO dto = new RecipeDTO();
                    BeanUtils.copyProperties(recipe,dto);
                    dtoList.add(dto);
                });
        return dtoList;
    }

}
