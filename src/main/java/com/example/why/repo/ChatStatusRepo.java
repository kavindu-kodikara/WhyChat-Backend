package com.example.why.repo;

import com.example.why.entity.ChatStaus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatStatusRepo extends JpaRepository<ChatStaus,Integer> {
}
