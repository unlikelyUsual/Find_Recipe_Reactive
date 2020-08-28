package com.example.recipe.mappers;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.domain.Recipe;
import org.mapstruct.Mapper;
import reactor.core.publisher.Mono;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    Recipe commandToEntity(RecipeCommand recipeCommand);

    RecipeCommand entityToCommand(Recipe recipe);

    RecipeCommand entityMonoToCommand(Mono<Recipe> recipe);

}
