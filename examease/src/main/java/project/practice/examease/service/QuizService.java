package project.practice.examease.service;

import org.springframework.http.ResponseEntity;
import project.practice.examease.dto.PortalStatDto;
import project.practice.examease.dto.TestDto;
import project.practice.examease.entity.Quiz;

import java.util.List;

public interface QuizService {
    ResponseEntity<String> addQuiz(Quiz requestBody);

    ResponseEntity<Quiz> getQuizById(Integer id);

    ResponseEntity<List<Quiz>> getQuizBySubcategoryId(Integer id);

    ResponseEntity<List<Quiz>> getQuizes();

    ResponseEntity<String> addTest(TestDto requestBody);

    ResponseEntity<PortalStatDto> getPortalStats();


    ResponseEntity<List<Quiz>> getQuizesByPagination(int pageSize, int pageNumber,Integer subcategoryId);

    ResponseEntity<Integer> getQuizCountBySubcategoryId(Integer id);
}
