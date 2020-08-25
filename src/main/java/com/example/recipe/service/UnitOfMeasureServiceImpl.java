package com.example.recipe.service;

import com.example.recipe.domain.UnitOfMeasure;
import com.example.recipe.repositories.reactiveRepostories.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Flux<UnitOfMeasure> findAll() {
        return unitOfMeasureRepository.findAll();
    }
}
