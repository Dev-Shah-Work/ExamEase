package project.practice.examease.service;

import org.springframework.http.ResponseEntity;
import project.practice.examease.entity.Quiz;

public interface QuizService {
    ResponseEntity<String> addQuiz(Quiz requestBody);

    ResponseEntity<Quiz> getQuizById(Integer id);
}
