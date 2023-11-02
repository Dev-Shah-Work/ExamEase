package project.practice.examease.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;
import project.practice.examease.entity.AppUser;
import project.practice.examease.service.AppUserService;
import project.practice.examease.util.ResponseUtil;

import java.util.HashMap;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/examease/user")
public class UserController {

    @Autowired
    private final AppUserService appUserService;
    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody AppUser requestBody){
        try{
                return appUserService.register(requestBody);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Something went wrong in Controller Implementation", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody HashMap<String,String> requestBody){
        try{

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Something went wrong in Controller Implementation",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
