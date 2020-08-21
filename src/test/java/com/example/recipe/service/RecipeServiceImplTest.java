package com.example.recipe.service;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.domain.Recipe;
import com.example.recipe.dto.RecipeDTO;
import com.example.recipe.mappers.RecipeMapper;
import com.example.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeMapper recipeMapper;

    private final Long RECIPE_ID = 1L;

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
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeSet);

        Set<Recipe> set = recipeService.getAllRecipes();

        assertEquals(set.size(),1);

        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    void getRecipeById() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(testRecipe));
        Recipe recipe = recipeService.getRecipeById(RECIPE_ID);
        assertEquals(RECIPE_ID,recipe.getId());
        verify(recipeRepository,times(1)).findById(anyLong());
    }

    @Test
    void saveOrUpdateRecipe() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);

        when(recipeMapper.commandToEntity(any(RecipeCommand.class))).thenReturn(testRecipe);
        when(recipeMapper.entityToCommand(any(Recipe.class))).thenReturn(recipeCommand);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(testRecipe);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(testRecipe));

        RecipeCommand savedRecipe = recipeService.saveOrUpdateRecipe(recipeCommand);

        assertEquals(RECIPE_ID,recipeCommand.getId());
        verify(recipeMapper,times(1)).entityToCommand(any());
        verify(recipeMapper,times(1)).commandToEntity(any());
        verify(recipeRepository,times(1)).save(any());

    }

    @Test
    void getRecipeCommonObjectById() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(testRecipe));
        when(recipeMapper.entityToCommand(any(Recipe.class))).thenReturn(recipeCommand);
        RecipeCommand command = recipeService.getRecipeCommonObjectById(anyLong());
        assertEquals(RECIPE_ID,command.getId());
    }

    @Test
    void save() {
        when(recipeRepository.save(any())).thenReturn(testRecipe);
        Recipe recipe = recipeService.save(any());
        assertEquals(RECIPE_ID,recipe.getId());
        verify(recipeRepository,times(1)).save(any());
    }

    @Test
    void getRecipesByDescription() {
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(testRecipe);
        when(recipeRepository.findByDescriptionContainingIgnoreCase(anyString())).thenReturn(recipeList);
        List<RecipeDTO> recipeDTOList = recipeService.getRecipesByDescription(anyString());
        assertEquals(1,recipeDTOList.size());
        assertEquals(RECIPE_ID,recipeDTOList.iterator().next().getId());
        verify(recipeRepository,times(1)).findByDescriptionContainingIgnoreCase(anyString());
    }
}