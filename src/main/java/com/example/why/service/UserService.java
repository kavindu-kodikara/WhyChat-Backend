package com.example.why.service;

import com.example.why.dto.UserSignInDTO;
import com.example.why.entity.User;
import com.example.why.entity.UserStatus;
import com.example.why.repo.UserRepo;
import com.example.why.repo.UserStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserStatusRepo userStatusRepo;

    public User userSignIn(UserSignInDTO userSignInDTO){

        User user = userRepo.findByMobileAndPassword(userSignInDTO.getMobile(),userSignInDTO.getPassword());

        return user;

    }

    public boolean userSignUp(User user){

        boolean status = false;

        if (!userRepo.existsByMobile(user.getMobile())){

            user.setRegisterdDateTime(new Date());
            user.setLast_seen(new Date());

            Optional<UserStatus> optional = userStatusRepo.findById(2);

            user.setUserStatus(optional.get());

            userRepo.save(user);

            status = true;

        }

        return status;

    }


}
