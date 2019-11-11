package com.ico.ltd.spring5recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exc) {
        log.error("Handling number format exception");
        log.error(exc.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exc", exc);
        modelAndView.setViewName("400error");

        return modelAndView;
    }
}
