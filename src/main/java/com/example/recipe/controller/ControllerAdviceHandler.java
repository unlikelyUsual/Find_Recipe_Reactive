package com.example.recipe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thymeleaf.exceptions.TemplateInputException;


@Slf4j
@ControllerAdvice
public class ControllerAdviceHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NumberFormatException.class, TemplateInputException.class})
    String handleNumberFormatException(Exception exception, Model model){
        model.addAttribute("message",exception.getMessage());
        return "/errors/400";
    }

}
