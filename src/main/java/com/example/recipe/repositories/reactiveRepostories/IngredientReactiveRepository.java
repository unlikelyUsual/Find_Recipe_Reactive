package com.example.recipe.repositories.reactiveRepostories;

import com.example.recipe.domain.Ingredient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IngredientReactiveRepository extends ReactiveMongoRepository<Ingredient,String> {
}
