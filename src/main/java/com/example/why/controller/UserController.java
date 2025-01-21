package com.example.why.controller;

import com.example.why.dto.UserSignInDTO;
import com.example.why.entity.User;
import com.example.why.config.Validat;
import com.example.why.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "why/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/sign_in")
    public HashMap<String, Object> signIn(@RequestBody UserSignInDTO userSignInDTO){

        HashMap<String, Object> responseJson = new HashMap<>();
        responseJson.put("success", false);

        if (userSignInDTO.getMobile().isEmpty()) {
            //mobile num is blank
            responseJson.put("message", "please fill your mobile number");

        }else if (!Validat.isMobileValid(userSignInDTO.getMobile())) {
            responseJson.put("message", "please fill a valid mobile");

        } else if (userSignInDTO.getPassword().isEmpty()) {
            responseJson.put("message", "please fill your password");

        } else {

            User user = userService.userSignIn(userSignInDTO);

            if(user != null){
                responseJson.put("message", "Sign In Completed");
                responseJson.put("success", true);
                responseJson.put("user", user);
            }else {
                responseJson.put("message", "invalid details please try again");
            }

        }


        return responseJson;

    }

    @PostMapping(value = "sign_up")
    public Map<String,Object> signUp(@RequestBody User requestUser){

        HashMap<String,Object> responseJson = new HashMap<>();

        responseJson.put("success", false);

        String mobile = requestUser.getMobile();
        String fname = requestUser.getFname();
        String lname = requestUser.getLname();
        String password = requestUser.getPassword();


        if (mobile.isEmpty()) {
            //mobile num is blank
            responseJson.put("message", "please fill mobile number");

        }else if (!Validat.isMobileValid(mobile)) {
            responseJson.put("message", "please fill a valid mobile");

        } else if (fname.isEmpty()) {
            responseJson.put("message", "please fill first Name");

        } else if (lname.isEmpty()) {
            responseJson.put("message", "please fill last Name");

        } else if (password.isEmpty()) {
            responseJson.put("message", "please fill your password");

        } else {

            if (userService.userSignUp(requestUser)){

                responseJson.put("message", "account created");
                responseJson.put("success", true);

            }

        }

        return responseJson;
    }


}
