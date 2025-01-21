package com.example.why.repo;

import com.example.why.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {
    public User findByMobileAndPassword(String mobile,String password);
    public boolean existsByMobile(String mobile);
    public User findByMobile(String mobile);
    public List<User> findAllByMobileNot(String mobile);
}
