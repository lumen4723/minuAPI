package com.lumen.minuAPI.Index;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class NotfoundException {
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFoundError(NoHandlerFoundException ex) {
        return new ModelAndView("redirect:/?error=404");
    }
}
