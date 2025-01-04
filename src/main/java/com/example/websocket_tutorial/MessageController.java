package com.example.websocket_tutorial;

import com.example.websocket_tutorial.dto.MessageRequestDto;
import com.example.websocket_tutorial.dto.MessageResponseDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/receive/message")
    public MessageResponseDto mesaging(MessageRequestDto message) throws Exception {
        Thread.sleep(1000);
        return new MessageResponseDto(HtmlUtils.htmlEscape(message.getContent()));
    }
}
