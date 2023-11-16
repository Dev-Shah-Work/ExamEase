package project.practice.examease.service;


import org.springframework.http.ResponseEntity;
import project.practice.examease.dto.AppUserDto;
import project.practice.examease.entity.AppUser;

import java.util.HashMap;


public interface AppUserService {
    ResponseEntity<String> register(AppUser requestMap);

    ResponseEntity<String> login(HashMap<String, String> requestBody);

    ResponseEntity<AppUserDto> findById(Integer id);

    ResponseEntity<String> updateUser(Integer id, HashMap<String, String> requestBody);
}
