package com.example.recipe.repositories;

import com.example.recipe.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CategoryRepositoryIT {

    // Integration Testing Example

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DirtiesContext // Loan Context Again After this Test
    void findByDescription() {
        Optional<Category> category = categoryRepository.findByDescription("American");
        assertEquals("American",category.get().getDescription());
    }


    @Test
    void findMexicanDescription() {
        Optional<Category> category = categoryRepository.findByDescription("Mexican");
        assertEquals("Mexican",category.get().getDescription());
    }

}