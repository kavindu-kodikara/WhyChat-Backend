package com.example.why.repo;

import com.example.why.entity.Chat;
import com.example.why.entity.ChatStaus;
import com.example.why.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepo extends JpaRepository<Chat,Integer> {

    @Query("SELECT c FROM Chat c WHERE (c.fromUser = :user AND c.toUser = :user1) OR (c.fromUser = :user1 AND c.toUser = :user) ORDER BY c.dateTime DESC")
    public List<Chat> findTopByUsers(@Param("user") User user, @Param("user1") User user1, Pageable pageable);

    @Query("SELECT c FROM Chat c WHERE (c.fromUser = :logged_user AND c.toUser = :other_user) OR (c.fromUser = :other_user AND c.toUser = :logged_user) ORDER BY c.dateTime ASC")
    public List<Chat> findChatBetweenUsers(@Param("logged_user") User logged_user, @Param("other_user") User other_user);

    @Query("SELECT COUNT(c) FROM Chat c WHERE c.fromUser = :fromUser AND c.toUser = :toUser AND c.chatStaus = :chatStaus")
    int countChatsByFromUserAndToUserAndChatStaus(
            @Param("fromUser") User fromUser,
            @Param("toUser") User toUser,
            @Param("chatStaus") ChatStaus chatStaus
    );

    public List<Chat> findByChatStausAndFromUserAndToUser(ChatStaus chatStaus,User fromUser,User toUser);

}
