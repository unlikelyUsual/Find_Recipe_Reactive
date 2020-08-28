package com.example.recipe.service;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.domain.Recipe;
import com.example.recipe.dto.RecipeDTO;
import com.example.recipe.mappers.RecipeMapper;
import com.example.recipe.repositories.reactiveRepostories.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeReactiveRepository recipeRepository;

    @Mock
    RecipeMapper recipeMapper;

    private final String RECIPE_ID = "1";

    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
       testRecipe = new Recipe();
       testRecipe.setId(RECIPE_ID);
       testRecipe.setDescription("abc");
    }

    @Test
    void getAllRecipes() {
        Recipe recipe = new Recipe();

        when(recipeRepository.findAll()).thenReturn(Flux.just(recipe));

        Flux<Recipe> set = recipeService.getAllRecipes();

        assertEquals(1,set.collectList().block().size());

        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    void getRecipeById() {
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(testRecipe));
        Mono<Recipe> recipe = recipeService.getRecipeById(RECIPE_ID);
        assertEquals(RECIPE_ID,recipe.block().getId());
        verify(recipeRepository,times(1)).findById(anyString());
    }

    @Test
    void saveOrUpdateRecipe() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);

        when(recipeMapper.commandToEntity(any(RecipeCommand.class))).thenReturn(testRecipe);
        when(recipeMapper.entityToCommand(any(Recipe.class))).thenReturn(recipeCommand);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(Mono.just(testRecipe));
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(testRecipe));

        RecipeCommand saveCommand = recipeService.saveOrUpdateRecipe(recipeCommand).block();

        assertEquals(RECIPE_ID,saveCommand.getId());
        verify(recipeMapper,times(1)).entityToCommand(any());
        verify(recipeMapper,times(1)).commandToEntity(any());
        verify(recipeRepository,times(1)).save(any());

    }

    @Test
    void getRecipeCommonObjectById() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(testRecipe));
        when(recipeMapper.entityMonoToCommand(any())).thenReturn(recipeCommand);
        RecipeCommand command = recipeService.getRecipeCommonObjectById(anyString()).block();
        assertEquals(RECIPE_ID,command.getId());
    }

    @Test
    void save() {
        when(recipeRepository.save(any())).thenReturn(Mono.just(testRecipe));
        Recipe recipe = recipeService.save(any()).block();
        assertEquals(RECIPE_ID,recipe.getId());
        verify(recipeRepository,times(1)).save(any());
    }

    @Test
    void getRecipesByDescription() {
        when(recipeRepository.findByDescriptionContainingIgnoreCase(anyString())).thenReturn(Flux.just(testRecipe));
        List<RecipeDTO> recipeDTOList = recipeService.getRecipesByDescription(anyString()).collectList().block();
        assertEquals(1,recipeDTOList.size());
        assertEquals(RECIPE_ID,recipeDTOList.get(0).getId());
        verify(recipeRepository,times(1)).findByDescriptionContainingIgnoreCase(anyString());
    }
}