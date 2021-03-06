package com.agility.handlerexception.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class WebController {

    @RequestMapping(value = "/normalexcetion")
    public void throwException() {
        throw new RuntimeException();
    }

    @RequestMapping(value = "/customexception")
    public void throwCustomException() {
        throw new CustomException();
    }

    @ExceptionHandler(CustomException.class)
    public String runtimeException() {
        return "controlleradvice";
    }

    @RequestMapping(value = "/conflictexception")
    public void throwConflictException() {
        throw new CustomConflictException();
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Having conflict")
    @ExceptionHandler(CustomConflictException.class)
    public void conflict() {

    }

    @RequestMapping(value = "/customexceptionwithhttpstatuscode")
    public void throwCustomExceptionWithHttpStatusCode() {
        throw new CustomExceptionWithHttpStatusCode();
    }

    /**
     * Use ExceptionHandler defined with @ControllerAdvice
     */
    @RequestMapping(value = "/customgeneralexception")
    public void throwsCustomGeneralException() {
        throw new CustomGeneralException();
    }

}
