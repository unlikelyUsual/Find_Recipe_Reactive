package com.example.recipe.controller;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.domain.Recipe;
import com.example.recipe.dto.RecipeDTO;
import com.example.recipe.exceptions.NotFoundException;
import com.example.recipe.service.CategoryService;
import com.example.recipe.service.IngredientService;
import com.example.recipe.service.RecipeService;
import com.example.recipe.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
        this.categoryService = categoryService;
    }

    @GetMapping("/recipe/{id}")
    String getRecipeInfoById(@PathVariable(name = "id") Long id, Model model) {
         if(id == null){
             return  "index";
         }
        Recipe recipe = recipeService.getRecipeById(id);
         model.addAttribute("recipe",recipe);
        return "recipe/viewRecipe";
    }

    @GetMapping("/recipe/create")
    String getRecipeAddPage(Model model) {
        model.addAttribute("categories",categoryService.fetchAllCategory());
        model.addAttribute("uomMap",unitOfMeasureService.findAll());
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeForm";
    }

    @PostMapping("/recipe/form")
    String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand , BindingResult result ) {
        if(result.hasErrors()){
            result.getAllErrors().forEach(objectError -> log.error(objectError.toString()));
            return "recipe/recipeForm";
        }
        RecipeCommand saveRecipe = recipeService.saveOrUpdateRecipe(recipeCommand);
        return  "redirect:/recipe/"+ saveRecipe.getId();
    }

    @GetMapping("/recipe/modify/{id}")
    String modifyRecipe(@PathVariable(name = "id") Long id , Model model) {
        model.addAttribute("categories",categoryService.fetchAllCategory());
        model.addAttribute("uomMap",unitOfMeasureService.findAll());
        model.addAttribute("recipe",recipeService.getRecipeCommonObjectById(id));
        return "recipe/recipeForm";
    }

    @PostMapping("/recipe/uploadImage")
    @ResponseBody
    String uploadRecipeImage(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
         Long id = multipartHttpServletRequest.getParameter("recipe") != null ? Long.valueOf(multipartHttpServletRequest.getParameter("recipe") ) : null;
         Recipe recipe = recipeService.getRecipeById(id);
         MultipartFile file = multipartHttpServletRequest.getFile("file");
         Byte[] bytes = new Byte[file.getBytes().length];
         int i = 0;
         for (byte b : file.getBytes()){
             bytes[i++] = b;
         }
         recipe.setImage(bytes);
         recipe.setImageString(Base64.getEncoder().encodeToString(file.getBytes()));
         recipeService.save(recipe);
        return "";
    }

    @GetMapping("/recipe/search")
    String getRecipeSearchPage(){
        return "recipe/searchRecipe";
    }

    @PostMapping("/recipe/search")
    @ResponseBody
    List<RecipeDTO> searchRecipe(@RequestBody RecipeCommand recipeCommand)  throws Exception{
        List<RecipeDTO> recipes;
        if(recipeCommand.getDescription() == null) recipeCommand.setDescription("");
        recipes = recipeService.getRecipesByDescription(recipeCommand.getDescription());
        return recipes;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    ModelAndView handleNotFoundException(Exception exception){
        ModelAndView modelAndView  = new ModelAndView();
        modelAndView.setViewName("/errors/404");
        modelAndView.addObject("message",exception.getMessage());
        return modelAndView;
    }


}
