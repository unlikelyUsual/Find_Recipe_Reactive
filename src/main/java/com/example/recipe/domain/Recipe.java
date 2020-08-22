package com.example.recipe.domain;

import com.example.recipe.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;


@Document
@Getter
@Setter
public class Recipe {

    @Id
    private String id;

    private String description;

    private Integer prepTime;

    private Integer cookTime;

    private Integer serving;

    private String source;

    private String url;

    private String directions;

    private Byte[] image;

    private String imageString;

    private Difficulty difficulty;

    private Set<Ingredient> ingredients = new HashSet<>();

    private Notes notes;

    private Set<Category> categories = new HashSet<>();


    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public void addIngredient(Ingredient ingredients) {
        this.ingredients.add(ingredients);
    }
}
