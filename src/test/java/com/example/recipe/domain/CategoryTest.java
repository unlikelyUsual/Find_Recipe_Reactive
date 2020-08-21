package com.example.recipe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    // UNIT Test with JUNIT 5

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long idValue = 4L;
        category.setId(idValue);
        assertEquals(idValue,category.getId());
    }

    @Test
    void getDescription() {
        String description = "Indian Food";
        category.setDescription(description);
        assertEquals(description,category.getDescription());
    }

    @Test
    void getRecipes() {
    }
}