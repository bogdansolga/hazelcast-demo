package com.sg.hazelcast.demo.model;

import java.io.Serializable;

public class MessageDTO implements Serializable {

    private final String message;

    public MessageDTO(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
