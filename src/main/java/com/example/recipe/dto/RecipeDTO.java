package com.example.recipe.dto;

import com.example.recipe.domain.Notes;
import com.example.recipe.enums.Difficulty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeDTO {

    private Long id;

    private String description;

    private Integer prepTime;

    private Integer cookTime;

    private Integer serving;

    private String source;

    private String url;

    private String directions;

    private Byte[] image;

    private Difficulty difficulty;

    private String imageString;
}
