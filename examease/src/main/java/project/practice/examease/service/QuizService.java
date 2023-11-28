package project.practice.examease.service;

import org.springframework.http.ResponseEntity;
import project.practice.examease.entity.Quiz;

import java.util.List;

public interface QuizService {
    ResponseEntity<String> addQuiz(Quiz requestBody);

    ResponseEntity<Quiz> getQuizById(Integer id);

    ResponseEntity<List<Quiz>> getQuizBySubcategoryId(Integer id);

    ResponseEntity<List<Quiz>> getQuizes();
}
