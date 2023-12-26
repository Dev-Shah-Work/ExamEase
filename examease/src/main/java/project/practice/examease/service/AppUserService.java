package project.practice.examease.service;


import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import project.practice.examease.dto.AppUserDto;
import project.practice.examease.dto.LeaderBoardDTO;
import project.practice.examease.dto.QuizAnalyticDto;
import project.practice.examease.dto.TestAnalyticDto;
import project.practice.examease.entity.AppUser;

import java.util.HashMap;
import java.util.List;


public interface AppUserService {
    ResponseEntity<String> register(AppUser requestMap);

    ResponseEntity<String> login(HashMap<String, String> requestBody);

    ResponseEntity<AppUserDto> findById(Integer id);

    ResponseEntity<String> updateUser(Integer id, HashMap<String, String> requestBody);

    ResponseEntity<List<TestAnalyticDto>> getTestAnalytics(int id);

    ResponseEntity<List<QuizAnalyticDto>> getQuizAnalytics(int id);

    ResponseEntity<List<LeaderBoardDTO>> getLeaderBoardStats();
}
