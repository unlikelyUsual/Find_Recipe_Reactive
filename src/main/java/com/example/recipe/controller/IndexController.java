package com.example.recipe.controller;

import com.example.recipe.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"","/","index","index.html"})
    String getIndex(Model model){
        model.addAttribute("recipes",recipeService.getAllRecipes());
        return "index";
    }
}
