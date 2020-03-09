package org.example.core.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.MessageFormat;
import java.util.Arrays;

@ControllerAdvice
@Controller
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(AppException.class)
    public void exceptionHandler(AppException e) {
        log.info(MessageFormat.format("message: {0}", e.getMessage()));
        log.error(MessageFormat.format("Stacktrace: {0}", Arrays.toString(e.getStackTrace())));
    }

    @GetMapping("/notFound")
    public String notFoundPage() {
        return "not_found";
    }

    @GetMapping("/internalServerError")
    public String internalServerErrorPage() {
        return "internal_server_error";
    }

    @GetMapping("/tooManyRequests")
    public String conflict() {
        return "too_many_requests";
    }
}
