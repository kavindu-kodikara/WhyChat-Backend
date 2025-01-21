package com.example.why.service;

import com.example.why.entity.Chat;
import com.example.why.entity.ChatStaus;
import com.example.why.entity.User;
import com.example.why.entity.UserStatus;
import com.example.why.repo.ChatRepo;
import com.example.why.repo.ChatStatusRepo;
import com.example.why.repo.UserRepo;
import com.example.why.repo.UserStatusRepo;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class HomeService {

    @Autowired
    ChatRepo chatRepo;

    @Autowired
    ChatStatusRepo chatStatusRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserStatusRepo userStatusRepo;

    @Autowired
    ServletContext servletContext;

    public HashMap<String,Object> loadHomeData(String mobile){

        HashMap<String,Object> responseObject = new HashMap<>();
        responseObject.put("success", false);
        responseObject.put("message", "Unable to process your request");

         User user = userRepo.findByMobile(mobile);

        if (user != null){


            List<User> userList = userRepo.findAllByMobileNot(mobile);

            Pageable pageable = PageRequest.of(0,1);

            ArrayList<HashMap<String,Object>> chatArray = new ArrayList<>();

            for (User user1 : userList){

                List<Chat> chatList = chatRepo.findTopByUsers(user,user1,pageable);


                HashMap<String, Object> chatItem = new HashMap<>();

                chatItem.put("other_user_name", user1.getFname() + " " + user1.getLname());
                chatItem.put("other_user_id", user1.getId());
                chatItem.put("other_user_mobile", user1.getMobile());
                chatItem.put("other_user_status", user1.getUserStatus().getId());

                //check avarat
                String serverPath = servletContext.getRealPath("");
                String otherUserAvatarImagePath = serverPath+ File.separator+"AvatarImages"+File.separator+user1.getMobile()+".png";
                File otherUserAvatarImageFile = new File(otherUserAvatarImagePath);

                chatItem.put("avatar_image_found",otherUserAvatarImageFile.exists());

                if (chatList.isEmpty()) {
                    //no msg
                    chatItem.put("message", "Let's Start new conversation");
                    chatItem.put("dateTime",new SimpleDateFormat("hh:mm a").format(user.getRegisterdDateTime()));
                    chatItem.put("chat_status_id", 1); //1=seen, 2=unseen
                } else {
                    //found msg

                for (Chat latestChat : chatList){
                    System.out.println(latestChat.getMessage());
                    chatItem.put("message", latestChat.getMessage());
                    chatItem.put("dateTime", new SimpleDateFormat("hh:mm a").format(latestChat.getDateTime()));
                    chatItem.put("chat_status_id", latestChat.getChatStaus().getId());
                }



                }

                ChatStaus unseenChatStaus = chatStatusRepo.getReferenceById(2);

                int unseenChatCount = chatRepo.countChatsByFromUserAndToUserAndChatStaus(user1,user,unseenChatStaus);
                chatItem.put("unseen_chat_count",unseenChatCount);

                chatArray.add(chatItem);


            }

            responseObject.put("success", true);
            responseObject.put("message", "Success");
            responseObject.put("user", user);
            responseObject.put("jsonChatArray", chatArray);

        }

        return responseObject;

    }

    public void updateUserStatus(String mobile,int statusId){

        UserStatus userStatus = userStatusRepo.getReferenceById(statusId);

        User user = userRepo.findByMobile(mobile);
        user.setUserStatus(userStatus);

        userRepo.save(user);

    }

}
