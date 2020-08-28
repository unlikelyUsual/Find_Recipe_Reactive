package com.example.recipe.controller;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.dto.RecipeDTO;
import com.example.recipe.service.CategoryService;
import com.example.recipe.service.IngredientService;
import com.example.recipe.service.RecipeService;
import com.example.recipe.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

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
    String getRecipeInfoById(@PathVariable(name = "id") String id, Model model) {
         if(id == null){
             return  "index";
         }
         model.addAttribute("recipe",recipeService.getRecipeById(id));
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
        RecipeCommand saveRecipe = recipeService.saveOrUpdateRecipe(recipeCommand).block();
        return  "redirect:/recipe/"+ saveRecipe.getId();
    }

    @GetMapping("/recipe/modify/{id}")
    String modifyRecipe(@PathVariable(name = "id") String id , Model model) {
        model.addAttribute("categories",categoryService.fetchAllCategory());
        model.addAttribute("uomMap",unitOfMeasureService.findAll());
        model.addAttribute("recipe",recipeService.getRecipeCommonObjectById(id));
        return "recipe/recipeForm";
    }

 /*   @PostMapping("/recipe/uploadImage")
    @ResponseBody
    String uploadRecipeImage(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
         String id = multipartHttpServletRequest.getParameter("recipe") != null ? multipartHttpServletRequest.getParameter("recipe")  : null;
         Recipe recipe = recipeService.getRecipeById(id).block();
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
    }*/

    @GetMapping("/recipe/search")
    String getRecipeSearchPage(){
        return "recipe/searchRecipe";
    }

    @PostMapping("/recipe/search")
    @ResponseBody
    Flux<RecipeDTO> searchRecipe(@RequestBody RecipeCommand recipeCommand)  throws Exception{
        Flux<RecipeDTO> recipes;
        if(recipeCommand.getDescription() == null) recipeCommand.setDescription("");
        recipes = recipeService.getRecipesByDescription(recipeCommand.getDescription());
        return recipes;
    }

/*    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    ModelAndView handleNotFoundException(Exception exception){
        ModelAndView modelAndView  = new ModelAndView();
        modelAndView.setViewName("/errors/404");
        modelAndView.addObject("message",exception.getMessage());
        return modelAndView;
    }*/


}
