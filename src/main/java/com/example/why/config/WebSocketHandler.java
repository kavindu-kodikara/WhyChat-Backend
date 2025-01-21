package com.example.why.config;

import com.example.why.dto.ChatMessageDTO;
import com.example.why.dto.MessageType;
import com.example.why.service.ChatService;
import com.example.why.service.HomeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private HomeService homeService;

    @Autowired
    private ChatService chatService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ConcurrentMap<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, WebSocketSession> chatSessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session)  throws Exception{
        URI uri = session.getUri();

        if(uri != null){
            Map<String, String> params = Validat.parseQueryParams(uri);
            String type = params.get("type");
            String mobileNumber = params.get("mobileNumber");

            if ("register".equalsIgnoreCase(type)) {
                sessionMap.put(mobileNumber, session);
                System.out.println("Registered mobile number: " + mobileNumber);

                homeService.updateUserStatus(mobileNumber,1);

                for (WebSocketSession otherSession : sessionMap.values()){

                    String otherUserMobileNumber = sessionMap.entrySet()
                            .stream()
                            .filter(entry -> otherSession.equals(entry.getValue()))
                            .map(entry -> entry.getKey())
                            .findFirst()
                            .orElse(null);

                    sendHomeData(otherUserMobileNumber,homeService.loadHomeData(otherUserMobileNumber));

                }

            }


            super.afterConnectionEstablished(session);
        }


    }



    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        MessageType messageType = objectMapper.readValue(payload, MessageType.class);


        if ("chat".equalsIgnoreCase(messageType.getType())) {
            chatSessionMap.put(messageType.getMobileNumber(), session);
            System.out.println("Registered mobile number: " + messageType.getMobileNumber());

            HashMap<String,Object> list = new HashMap<>();

            chatService.updateChatStatus(messageType.getOther_user_id(),messageType.getLogged_user_id());

            homeService.updateUserStatus(messageType.getMobileNumber(),1);



            ArrayList<HashMap<String,Object>> chatList = chatService.loadChat(messageType.getLogged_user_id(),messageType.getOther_user_id());

            if (chatList != null){
                list.put("type","chatData");
                list.put("data",chatList);
                sendChatData(messageType.getMobileNumber(),list);
            }

            for (WebSocketSession otherSession : sessionMap.values()){

                String otherUserMobileNumber = sessionMap.entrySet()
                        .stream()
                        .filter(entry -> otherSession.equals(entry.getValue()))
                        .map(entry -> entry.getKey())
                        .findFirst()
                        .orElse(null);

                sendHomeData(otherUserMobileNumber,homeService.loadHomeData(otherUserMobileNumber));

            }

        }

        if ("sendChat".equalsIgnoreCase(messageType.getType())) {

            ChatMessageDTO chatMessageDTO = chatService.saveChat(messageType.getLogged_user_id(),messageType.getOther_user_id(),messageType.getMessage());

            sendChatMessage(chatMessageDTO);

            sendHomeData(chatMessageDTO.getOtherUserMobile(),homeService.loadHomeData(chatMessageDTO.getOtherUserMobile()));

        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {

        // Find the mobile number associated with this session
        String mobileNumber = sessionMap.entrySet()
                .stream()
                .filter(entry -> session.equals(entry.getValue()))
                .map(entry -> entry.getKey())
                .findFirst()
                .orElse(null);

        if (mobileNumber == null){
            mobileNumber = chatSessionMap.entrySet()
                    .stream()
                    .filter(entry -> session.equals(entry.getValue()))
                    .map(entry -> entry.getKey())
                    .findFirst()
                    .orElse(null);
        }

        if (mobileNumber != null){
            System.out.println("mobile : "+mobileNumber);
            homeService.updateUserStatus(mobileNumber,2);
        }

        sessionMap.values().remove(session);
        chatSessionMap.values().remove(session);

        System.out.println("WebSocket connection closed: " + session.getId());

        for (WebSocketSession otherSession : sessionMap.values()){

            String otherUserMobileNumber = sessionMap.entrySet()
                    .stream()
                    .filter(entry -> otherSession.equals(entry.getValue()))
                    .map(entry -> entry.getKey())
                    .findFirst()
                    .orElse(null);

            sendHomeData(otherUserMobileNumber,homeService.loadHomeData(otherUserMobileNumber));

        }

    }



    // Method to send a message to a specific mobile number
    public void sendHomeData(String mobileNumber, HashMap<String,Object> hashMap) {
        WebSocketSession session = sessionMap.get(mobileNumber);
        if (session != null && session.isOpen()) {
            try {
                String payload = objectMapper.writeValueAsString(hashMap);
                session.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // Method to send a message to a specific mobile number
    public void sendChatData(String mobileNumber, HashMap<String,Object> hashMap) {

        WebSocketSession session = chatSessionMap.get(mobileNumber);

        if (session != null && session.isOpen()) {

            System.out.println(mobileNumber + " : " + session.getId());
            System.out.println(hashMap.get("type"));
            System.out.println(hashMap.get("data"));

            try {
                String payload = objectMapper.writeValueAsString(hashMap);
                session.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendChatMessage(ChatMessageDTO chatMessageDTO){



        WebSocketSession loggedUserSesson = chatSessionMap.get(chatMessageDTO.getLoggedUserMobile());
        WebSocketSession otherUserSesson = chatSessionMap.get(chatMessageDTO.getOtherUserMobile());

        HashMap<String,Object> list = new HashMap<>();
        list.put("type","sendChat");

        if (loggedUserSesson != null && loggedUserSesson.isOpen()){

            chatMessageDTO.setSide("right");

            if (otherUserSesson != null && otherUserSesson.isOpen()){
                chatMessageDTO.setStatus("1");
                chatService.updateChatStatus(chatMessageDTO.getLogged_user_id(),chatMessageDTO.getOther_user_id());
            }

            list.put("data",chatMessageDTO);

            try {
                String payload = objectMapper.writeValueAsString(list);
                loggedUserSesson.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (otherUserSesson != null && otherUserSesson.isOpen()){

            chatMessageDTO.setSide("left");
            list.put("data",chatMessageDTO);

            try {
                String payload = objectMapper.writeValueAsString(list);
                otherUserSesson.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}
