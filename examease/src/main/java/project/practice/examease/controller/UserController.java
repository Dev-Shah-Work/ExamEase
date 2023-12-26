package project.practice.examease.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;
import project.practice.examease.dto.AppUserDto;
import project.practice.examease.dto.LeaderBoardDTO;
import project.practice.examease.dto.QuizAnalyticDto;
import project.practice.examease.dto.TestAnalyticDto;
import project.practice.examease.entity.AppUser;
import project.practice.examease.service.AppUserService;
import project.practice.examease.util.ResponseUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            return appUserService.login(requestBody);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Something went wrong in Controller Implementation",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<AppUserDto> findById(@PathVariable("id") Integer id){
        try{
            return appUserService.findById(id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new AppUserDto(),HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @PatchMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id")Integer id,@RequestBody HashMap<String,String> requestBody){
        try{
            return appUserService.updateUser(id,requestBody);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Something went wrong in Controller Implementation",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/users/test-analytics/{id}")
    public ResponseEntity<List<TestAnalyticDto>> getTestAnalytics(@PathVariable("id")int id){
        try{
            return appUserService.getTestAnalytics(id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return  new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/users/quiz-analytics/{id}")
    public ResponseEntity<List<QuizAnalyticDto>> getQuizAnalytics(@PathVariable("id")int id){
        try{
            return appUserService.getQuizAnalytics(id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/users/leaderboard/stats")
    public ResponseEntity<List<LeaderBoardDTO>> getLeaderBoardStats(){
        try{
            return appUserService.getLeaderBoardStats();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
