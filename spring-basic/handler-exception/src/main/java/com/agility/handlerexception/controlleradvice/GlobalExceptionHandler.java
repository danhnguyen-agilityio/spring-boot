package com.agility.handlerexception.controlleradvice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomGeneralException.class)
    public ModelAndView handleCustomGeneralException() {
        ModelAndView model = new ModelAndView();
        model.setViewName("generalexception");
        return model;
    }
}
