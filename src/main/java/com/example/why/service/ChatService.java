package com.example.why.service;

import com.example.why.dto.ChatMessageDTO;
import com.example.why.entity.Chat;
import com.example.why.entity.ChatStaus;
import com.example.why.entity.User;
import com.example.why.entity.UserStatus;
import com.example.why.repo.ChatRepo;
import com.example.why.repo.ChatStatusRepo;
import com.example.why.repo.UserRepo;
import com.example.why.repo.UserStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ChatService {

    @Autowired
    ChatRepo chatRepo;

    @Autowired
    ChatStatusRepo chatStatusRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserStatusRepo userStatusRepo;

    public ArrayList<HashMap<String, Object>> loadChat(String loggedUserId, String otherUserId){

        int logged_user_id = Integer.parseInt(loggedUserId);
        int other_user_id = Integer.parseInt(otherUserId);

        User logged_user = userRepo.getReferenceById(logged_user_id);
        User other_user = userRepo.getReferenceById(other_user_id);

        List<Chat> chatList = chatRepo.findChatBetweenUsers(logged_user,other_user);

        ChatStaus chatStaus = chatStatusRepo.getReferenceById(2);

        ArrayList<HashMap<String,Object>> chatArray = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");

        for (Chat chat : chatList) {

            //create chat object
            HashMap<String,Object> chatObject = new HashMap<>();
            chatObject.put("message", chat.getMessage());
            chatObject.put("dateTime", dateFormat.format(chat.getDateTime()));

            if (chat.getFromUser().getId() == other_user.getId()) {

                //add side to chat object
                chatObject.put("side", "left");

                //get only unseen chats
                if (chat.getChatStaus().getId() == 2) {

                    //update chat status to seen
                    chat.setChatStaus(chatStaus);
                    chatRepo.save(chat);

                }

            } else {
                //get chat from logged user

                //add side to chat object
                chatObject.put("side", "right");
                chatObject.put("status", chat.getChatStaus().getId()); //1 = seen, 2 = unseen

            }
            //add chat object into chatArray
            chatArray.add(chatObject);

        }

        return chatArray;

    }

    public ChatMessageDTO saveChat(String logged_user_id, String other_user_id, String message){


        User logged_user = userRepo.getReferenceById(Integer.parseInt(logged_user_id));
        User other_user = userRepo.getReferenceById(Integer.parseInt(other_user_id));

        Chat chat = new Chat();

        ChatStaus chatstatus = chatStatusRepo.getReferenceById(2);
        chat.setChatStaus(chatstatus);

        chat.setDateTime(new Date());
        chat.setFromUser(logged_user);
        chat.setToUser(other_user);
        chat.setMessage(message);

        chatRepo.save(chat);

        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");

        ChatMessageDTO chatMessageDTO = new ChatMessageDTO(
                message,
                format.format(new Date()),
                String.valueOf(chatstatus.getId()),
                logged_user.getMobile(),
                other_user.getMobile(),
                logged_user_id,
                other_user_id
        );

        return chatMessageDTO;

    }

    public HashMap<String, Object> updateChatStatus(String fromUserId, String toUserId){
        System.out.println("updateChatStatus");
        HashMap<String,Object> map = new HashMap<>();

        User fromUser = userRepo.getReferenceById(Integer.parseInt(fromUserId)); //other user
        User toUser = userRepo.getReferenceById(Integer.parseInt(toUserId));

        ChatStaus unseenChatStaus = chatStatusRepo.getReferenceById(2);
        List<Chat> chatList = chatRepo.findByChatStausAndFromUserAndToUser(unseenChatStaus,fromUser,toUser);

        ChatStaus seenChatStaus = chatStatusRepo.getReferenceById(1);

        ArrayList<Chat> chatArrayList = new ArrayList<>();

        for (Chat chat : chatList){
            System.out.println(chat.getMessage());
            chat.setChatStaus(seenChatStaus);
            chatArrayList.add(chat);
        }

        chatRepo.saveAll(chatArrayList);

        boolean isTrue = !chatArrayList.isEmpty();

        map.put("isTrue",isTrue);
        map.put("otheruserMobile",fromUser.getMobile());

        return map;

    }

}
