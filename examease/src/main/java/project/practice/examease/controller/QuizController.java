package project.practice.examease.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.practice.examease.entity.Category;
import project.practice.examease.entity.Quiz;
import project.practice.examease.service.QuizService;
import project.practice.examease.util.ResponseUtil;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/examease")
public class QuizController {
    @Autowired
    private final QuizService quizService;

    @PostMapping("/quizes")
    public ResponseEntity<String> addQuiz(@RequestBody Quiz requestBody){
        try{
            return quizService.addQuiz(requestBody);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Error in Controller Portion", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/quizes/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable("id") Integer id){
        try{
            return quizService.getQuizById(id);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new Quiz(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
