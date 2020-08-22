package com.example.recipe.repositories.reactiveRepostories;

import com.example.recipe.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe,String> {
    Mono<Recipe> findByDescription(String description);

    Flux<Recipe> findByDescriptionContainingIgnoreCase(String description);
}
