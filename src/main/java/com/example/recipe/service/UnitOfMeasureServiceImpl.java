package com.example.recipe.service;

import com.example.recipe.domain.UnitOfMeasure;
import com.example.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Set<UnitOfMeasure> findAll() {
        Set<UnitOfMeasure> set  = new HashSet<>();
        unitOfMeasureRepository.findAll().forEach(set::add);
        return set;
    }
}
