package com.example.recipe.commands;

import com.example.recipe.domain.Category;
import com.example.recipe.domain.Ingredient;
import com.example.recipe.domain.Notes;
import com.example.recipe.enums.Difficulty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RecipeCommand {

    private Long id;

    @NotBlank
    private String description;

    @Min(2)
    @Max(999)
    private Integer prepTime;

    @Min(5)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer serving;

    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;

    private Byte[] image;

    private Difficulty difficulty;

    private List<Ingredient> ingredients = new ArrayList<>();

    private Notes notes;

    private List<Category> categories = new ArrayList<>();

    private String imageString;
}
