package project.practice.examease.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.practice.examease.dto.TestDto;
import project.practice.examease.entity.Category;
import project.practice.examease.entity.Quiz;
import project.practice.examease.service.QuizService;
import project.practice.examease.util.ResponseUtil;

import java.util.ArrayList;
import java.util.List;

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
            System.out.println(requestBody);
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
    @GetMapping("/quizes/subcategory/{id}")
    public ResponseEntity<List<Quiz>> getQuizBySubcategoryId(@PathVariable("id")Integer id){
        try{
            return quizService.getQuizBySubcategoryId(id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/quizes")
    public ResponseEntity<List<Quiz>> getQuizes(){
        try{
            return quizService.getQuizes();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @PostMapping("/tests")
    public ResponseEntity<String> addTest(@RequestBody TestDto requestBody){
        try{
            return quizService.addTest(requestBody);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Error in Controller Portion", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
