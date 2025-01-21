package com.example.why.controller;

import com.example.why.dto.TestDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    @MessageMapping("request-home-chats")
    @SendTo("/topic/load-home-chats")
    public TestDTO loadHomeChats(TestDTO testDTO){

        System.out.println("Received message: " + testDTO.getContent());

        TestDTO response = new TestDTO();
        response.setFrom("Server");
        response.setContent("Hello, " + testDTO.getFrom() + "! Message received: " + testDTO.getContent());

        return response;

    }

}
