package com.example.recipe.service;

import com.example.recipe.domain.UnitOfMeasure;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasure> findAll();
}
