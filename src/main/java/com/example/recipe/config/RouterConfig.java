package com.example.recipe.config;

import com.example.recipe.domain.Recipe;
import com.example.recipe.service.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouterConfig {

    @Bean
    RouterFunction<?> routerFunction(RecipeService recipeService) {
        return RouterFunctions
                .route(
                        GET("api/recipes")
                        ,serverRequest -> ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(recipeService.getAllRecipes(), Recipe.class)
                );
    }

}
