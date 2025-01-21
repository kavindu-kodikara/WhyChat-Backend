package com.example.why.repo;

import com.example.why.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepo extends JpaRepository<UserStatus,Integer> {
}
