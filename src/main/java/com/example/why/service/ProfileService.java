package com.example.why.service;

import com.example.why.dto.ProfileDTO;
import com.example.why.entity.User;
import com.example.why.repo.UserRepo;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
public class ProfileService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ServletContext servletContext;

    public void updateUser(ProfileDTO profileDTO){

        User user = userRepo.getReferenceById(Integer.parseInt(profileDTO.getId()));

        if(!user.getFname().equals(profileDTO.getFname()) ||
                        !user.getFname().equals(profileDTO.getLname()) ||
                            !user.getPassword().equals(profileDTO.getPassword())){

            user.setFname(profileDTO.getFname());
            user.setLname(profileDTO.getLname());
            user.setPassword(profileDTO.getPassword());

            userRepo.save(user);
            System.out.println("user updated");
        }

        if (profileDTO.getAvatarImage() != null){

            try {


                String serverPath = servletContext.getRealPath("/");
                String avatarImagePath = serverPath + File.separator + "AvatarImages" + File.separator + user.getMobile() + ".png";

                File file = new File(avatarImagePath);
                Files.createDirectories(file.getParentFile().toPath()); // Create directories if they do not exist
                Files.copy(profileDTO.getAvatarImage().getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Image saved at: " + avatarImagePath);
            } catch ( IOException e) {
                throw new RuntimeException("Failed to save image", e);
            }
        }



    }


}
