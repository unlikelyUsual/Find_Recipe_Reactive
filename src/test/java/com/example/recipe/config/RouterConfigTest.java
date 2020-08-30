package com.example.recipe.config;

import com.example.recipe.domain.Recipe;
import com.example.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RouterConfigTest {

    private WebTestClient webTestClient;

    @Mock
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        RouterConfig routerConfig = new RouterConfig();
        webTestClient = WebTestClient.bindToRouterFunction(routerConfig.routerFunction(recipeService)).build();
    }

    @Test
    void routerFunction() {
        List<Recipe> recipeList = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipeList.add(recipe);
        Flux<Recipe> recipeFlux = Flux.fromIterable(recipeList);
        given(recipeService.getAllRecipes()).willReturn(recipeFlux);
        webTestClient.get()
                .uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Recipe.class);

    }
}