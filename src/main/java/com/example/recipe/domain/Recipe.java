package com.example.recipe.domain;

import com.example.recipe.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Integer prepTime;

    private Integer cookTime;

    private Integer serving;

    private String source;

    private String url;

    @Lob
    private String directions;

    @Lob
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
    private Set<Ingredient> ingredients = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",joinColumns = @JoinColumn(name = "recipe_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Lob
    private String imageString;

    public void setNotes(Notes notes) {
        notes.setRecipe(this);
        this.notes = notes;
    }

    public void addIngredient(Ingredient ingredients) {
        ingredients.setRecipe(this);
        this.ingredients.add(ingredients);
    }
}
