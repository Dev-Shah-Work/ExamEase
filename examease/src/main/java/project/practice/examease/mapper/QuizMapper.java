package project.practice.examease.mapper;

import org.springframework.stereotype.Service;
import project.practice.examease.dto.QuizDto;
import project.practice.examease.entity.Question;
import project.practice.examease.entity.Quiz;

import java.util.List;

@Service
public class QuizMapper {
    public QuizDto entityToDto(Quiz quiz){
        QuizDto quizDto=new QuizDto();
        quizDto.setDifficulty(quiz.getDifficulty());
        quizDto.setQuizName(quiz.getQuizName());
        quizDto.setDuration(quiz.getDuration());
        List<Question> questionList=quiz.getQuestions();
        quizDto.setTotalQuizQuestions(questionList.size());
        int totalScore=0;
        for(Question question:questionList){
            totalScore+=question.getPoint();
        }
        quizDto.setTotalQuizScore(totalScore);
        return quizDto;
    }
}
