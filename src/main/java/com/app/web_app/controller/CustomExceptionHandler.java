package com.app.web_app.controller;

import com.app.web_app.exceptions.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@ControllerAdvice/*(basePackages = {"com.app.demo.config.security"})*/ //TODO
@Controller
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(AppException.class)
    public void exceptionHandler(AppException e) {
        log.info("message: "  + e.getMessage());
        log.error("Stacktrace: " + Arrays.toString(e.getStackTrace()));
//        model.addAttribute("message", e.getMessage());
    }

    @GetMapping("/notFound")
    public String notFoundPage() {
        return "not_found";
    }

    @GetMapping ("/internalServerError")
    public String internalServerErrorPage(){
        return "internal_server_error";
    }

    @GetMapping("/tooManyRequests")
    public String conflict(){
        return "too_many_requests";
    }
}
