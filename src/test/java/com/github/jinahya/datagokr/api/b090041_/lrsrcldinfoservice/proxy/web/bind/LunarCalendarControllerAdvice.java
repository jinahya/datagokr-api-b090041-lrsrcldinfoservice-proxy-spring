package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.web.bind;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class LunarCalendarControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String exception(final Exception exception) {//}, ServletRequest request, HttpServletResponse response) {
        log.error("exception", exception);
        return exception.getMessage();
    }
}
