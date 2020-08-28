package com.example.recipe.service;


import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.domain.Recipe;
import com.example.recipe.dto.RecipeDTO;
import com.example.recipe.mappers.RecipeMapper;
import com.example.recipe.repositories.reactiveRepostories.RecipeReactiveRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;

    private final RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public Flux<Recipe> getAllRecipes() {
       return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> getRecipeById(String id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> saveOrUpdateRecipe(RecipeCommand recipeCommand) {
        if(recipeCommand.getId() != null && !recipeCommand.getId().isEmpty()){
            Recipe dbRecipe = this.getRecipeById(recipeCommand.getId()).block();
            recipeCommand.setImage(dbRecipe.getImage());
            recipeCommand.setImageString(dbRecipe.getImageString());
        }
        Recipe recipe = recipeMapper.commandToEntity(recipeCommand);
        recipeCommand.getIngredients().forEach(recipe::addIngredient);
        Recipe saveRecipe = recipeRepository.save(recipe).block();
        return Mono.just(recipeMapper.entityToCommand(saveRecipe));
    }

    @Override
    public Mono<RecipeCommand> getRecipeCommonObjectById(String id) {
        return Mono.just(recipeMapper.entityMonoToCommand(this.getRecipeById(id)));
    }

    @Override
    public Mono<Recipe> save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Flux<RecipeDTO> getRecipesByDescription(String description) {
      return recipeRepository.findByDescriptionContainingIgnoreCase(description)
                .map(recipe -> {
                    RecipeDTO dto = new RecipeDTO();
                    BeanUtils.copyProperties(recipe,dto);
                    return dto;
                });
    }

}
