package com.example.recipe.controller;

import com.example.recipe.domain.Recipe;
import com.example.recipe.service.RecipeService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    private IndexController indexController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @SneakyThrows
    @Test
    void mockMVCTest(){
        MockMvc mockMvc =  MockMvcBuilders.standaloneSetup(indexController).setControllerAdvice(new ControllerAdviceHandler()).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getIndex() {

        String index = "index";
        assertEquals(index,indexController.getIndex(model));

        Recipe recipe = new Recipe();
        Recipe recipe1 = new Recipe();
        recipe1.setId(33L);
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe);
        recipeSet.add(recipe1);
        when(recipeService.getAllRecipes()).thenReturn(recipeSet);

        ArgumentCaptor<Set<Recipe>> recipeParameter = ArgumentCaptor.forClass(Set.class);

        verify(recipeService,times(1)).getAllRecipes();
        verify(model,times(1)).addAttribute(eq("recipes"),recipeParameter.capture());
        //assertEquals(2,recipeParameter.getValue().size());


    }
}