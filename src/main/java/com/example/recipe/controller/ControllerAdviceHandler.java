package com.example.recipe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;


@Slf4j
@ControllerAdvice
public class ControllerAdviceHandler {
/*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    ModelAndView handleNumberFormatException(Exception exception){
        ModelAndView modelAndView  = new ModelAndView();
        modelAndView.setViewName("/errors/400");
        modelAndView.addObject("message",exception.getMessage());
        return modelAndView;
    }*/

}
