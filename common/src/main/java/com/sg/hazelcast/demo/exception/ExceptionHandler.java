package com.sg.hazelcast.demo.exception;

import com.sg.hazelcast.demo.model.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody MessageDTO incorrectRequest(final RuntimeException e) {
        return new MessageDTO(e.getMessage());
    }
}
