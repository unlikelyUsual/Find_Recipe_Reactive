package com.example.recipe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerAdviceHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    ModelAndView handleNumberFormatException(Exception exception){
        ModelAndView modelAndView  = new ModelAndView();
        modelAndView.setViewName("/errors/400");
        modelAndView.addObject("message",exception.getMessage());
        return modelAndView;
    }

}
