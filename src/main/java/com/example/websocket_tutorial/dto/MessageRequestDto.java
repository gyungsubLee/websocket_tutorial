package com.example.websocket_tutorial.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageRequestDto {

    private final String content;

    @JsonCreator
    public MessageRequestDto(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
