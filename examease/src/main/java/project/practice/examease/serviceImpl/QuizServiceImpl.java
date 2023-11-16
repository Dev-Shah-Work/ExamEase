package project.practice.examease.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.practice.examease.entity.Quiz;
import project.practice.examease.repo.QuizRepository;
import project.practice.examease.service.QuizService;
import project.practice.examease.util.ResponseUtil;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    @Autowired
    private final QuizRepository quizRepository;

    @Override
    public ResponseEntity<String> addQuiz(Quiz requestBody) {
        try{
            return ResponseUtil.getResponseEntity("Working properly",HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Error in service portion", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Quiz> getQuizById(Integer id) {
        try{
            if(id!=null && quizRepository.existsById(id)){
                Quiz quiz=quizRepository.findById(id).get();
                return new ResponseEntity<>(quiz,HttpStatus.OK);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new Quiz(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
