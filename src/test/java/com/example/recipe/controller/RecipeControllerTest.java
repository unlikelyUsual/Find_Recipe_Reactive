package com.example.recipe.controller;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.domain.Category;
import com.example.recipe.domain.Recipe;
import com.example.recipe.domain.UnitOfMeasure;
import com.example.recipe.dto.RecipeDTO;
import com.example.recipe.service.CategoryService;
import com.example.recipe.service.IngredientService;
import com.example.recipe.service.RecipeService;
import com.example.recipe.service.UnitOfMeasureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @InjectMocks
    RecipeController recipeController;

    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;
    @Mock
    CategoryService categoryService;

    MockMvc mockMvc;

    private final Long RECIPE_ID = 1L;

    private Recipe recipeTest;

    private List<Category> categoryList;

    private Set<UnitOfMeasure> unitOfMeasureList;

    @BeforeEach
    void setUp() {
        recipeTest = new Recipe();
        recipeTest.setId(RECIPE_ID);

        Category categoryOne = new Category(1L,"category 1");
        Category categoryTwo = new Category(2L,"category 2");

        categoryList = new ArrayList<>(2);
        categoryList.add(categoryOne);
        categoryList.add(categoryTwo);

        unitOfMeasureList = new HashSet<>();
        unitOfMeasureList.add(new UnitOfMeasure(1L,"tea spoon"));

        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).setControllerAdvice(new ControllerAdviceHandler()).build();
    }

    @SneakyThrows
    @Test
    void getRecipeInfoById() {

        when(recipeService.getRecipeById(anyLong())).thenReturn(recipeTest);

        mockMvc.perform(get("/recipe/"+RECIPE_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("recipe/viewRecipe"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void getRecipeAddPage() throws Exception {
        when(categoryService.fetchAllCategory()).thenReturn(categoryList);
        when(unitOfMeasureService.findAll()).thenReturn(unitOfMeasureList);

        mockMvc.perform(get("/recipe/create"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("recipe/recipeForm"))
                .andExpect(model().size(3));

    }

    @Test
    void saveOrUpdate() {
        try {
            RecipeCommand recipeCommand = new RecipeCommand();
            recipeCommand.setId(RECIPE_ID);
            when(recipeService.saveOrUpdateRecipe(any())).thenReturn(recipeCommand);
            mockMvc.perform(
                    post("/recipe/form").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("id", "")
                            .param("description", "some string")
                            .param("directions", "some directions")
            )
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/recipe/" + RECIPE_ID));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    void saveOrUpdateFailTest() {
        try {
            mockMvc.perform(
                    post("/recipe/form").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("id", "")
                            .param("description", "some string")
                            .param("directions", "some directions")
                            .param("difficulty","NOT_SO_HARD")
            )
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attributeExists("recipe"))
                    .andExpect(view().name("recipe/recipeForm"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void modifyRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        when(categoryService.fetchAllCategory()).thenReturn(categoryList);
        when(unitOfMeasureService.findAll()).thenReturn(unitOfMeasureList);
        when(recipeService.getRecipeCommonObjectById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/modify/" + RECIPE_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("recipe/recipeForm"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void uploadRecipeImage() {
    }

    @Test
    void getRecipeSearchPage() throws Exception {
        mockMvc.perform(get("/recipe/search"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("recipe/searchRecipe"));
    }

    @Test
    void searchRecipe() throws Exception {
       final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

        List<RecipeDTO> recipeDTOS = new ArrayList<>(1);
        recipeDTOS.add(new RecipeDTO());

        Recipe recipe = new Recipe();
        recipe.setDescription("");

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(recipe);
        String returnString = mapper.writeValueAsString(recipeDTOS);

        when(recipeService.getRecipesByDescription(anyString())).thenReturn(recipeDTOS);

        mockMvc.perform(
                post("/recipe/search")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(jsonString)
        )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(returnString));


    }
}