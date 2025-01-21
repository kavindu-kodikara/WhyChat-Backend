package com.example.why.controller;

import com.example.why.dto.ProfileDTO;
import com.example.why.service.ProfileService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;

@RestController
@RequestMapping(value = "why/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @Autowired
    ServletContext servletContext;

    @PostMapping(value = "/update-user" ,consumes = "multipart/form-data")
    public HashMap<String, Object> updateUser(@RequestParam("fname") String fname,
                                              @RequestParam("lname") String lname,
                                              @RequestParam("password") String password,
                                              @RequestParam(value = "avatarImage", required = false) MultipartFile avatarImage,
                                              @RequestParam("id") String id){

        HashMap<String,Object> responseJson = new HashMap<>();
        responseJson.put("success", false);

        if (fname.isEmpty()) {
            responseJson.put("message", "Please enter your first name");
        } else if (lname.isEmpty()) {
            responseJson.put("message", "Please enter your last name");
        } else if (password.isEmpty()) {
            responseJson.put("message", "Please enter your password");
        } else if (avatarImage.isEmpty()) {
            responseJson.put("message", "Please select profile Image");
        } else {

            ProfileDTO profileDTO = new ProfileDTO(id,fname,lname,password,avatarImage);

            profileService.updateUser(profileDTO);

            responseJson.put("success", true);
            responseJson.put("message", "Profile update Completed");


        }

        return responseJson;

    }

    @GetMapping(value = "/check-profile-image")
    public HashMap<String, Object> checkProfileImage(@RequestParam("mobile") String mobile){

        HashMap<String,Object> responseObject = new HashMap<>();
        responseObject.put("foundImg", false);

        System.out.println("check-profile-image"+mobile);

        String serverPath = servletContext.getRealPath("");
        String avatarImagePath = serverPath + File.separator + "AvatarImages" + File.separator + mobile + ".png";
        File avatarImageFile = new File(avatarImagePath);

        if (avatarImageFile.exists()) {
            //avatar found
            responseObject.put("foundImg", true);
        } else {
            //avatar not found
            responseObject.put("foundImg", false);
        }

        return responseObject;

    }

}
