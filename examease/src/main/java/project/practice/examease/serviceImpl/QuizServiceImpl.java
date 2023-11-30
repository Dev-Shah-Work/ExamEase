package project.practice.examease.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.practice.examease.dto.TestDto;
import project.practice.examease.entity.*;
import project.practice.examease.mapper.TestMapper;
import project.practice.examease.repo.AppUserRepository;
import project.practice.examease.repo.QuizRepository;
import project.practice.examease.repo.TestRepository;
import project.practice.examease.service.QuizService;
import project.practice.examease.util.ResponseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    @Autowired
    private final QuizRepository quizRepository;
    @Autowired
    private final TestMapper testMapper;
    @Autowired
    private final TestRepository testRepository;
    @Autowired
    private final AppUserRepository appUserRepository;

    @Override
    @Transactional
    public ResponseEntity<String> addQuiz(Quiz requestBody) {
        try {
            ArrayList<String> answers = new ArrayList<>();

            for (Question question : requestBody.getQuestions()) {
                if (question.getAnswer() != null) {
                    answers.add(question.getAnswer().getOptionText());
                    question.setAnswer(null);
                } else {
                    answers.add(null);
                }
            }
            System.out.println(answers);
            Quiz dummyQuiz = quizRepository.save(requestBody);
            System.out.println(dummyQuiz);
            if (dummyQuiz.getQuestions().size() == answers.size()) {
                int index = 0;
                for (Question question : dummyQuiz.getQuestions()) {

                    String currentAnswer = answers.get(index);
                    if (question.getOptions() != null) {
                        Optional<Option> result = question.getOptions().stream()
                                .filter(option -> (option.getOptionText() != null) && currentAnswer.equals(option.getOptionText()))
                                .findFirst();
                        if (result.isPresent()) {
                            Option answerOption = result.get();
                            question.setAnswer(answerOption);
                        } else {
                            question.setAnswer(null);
                        }
                    } else {
                        question.setAnswer(null);
                    }
                    index++;

                }

            }
            System.out.println(dummyQuiz);
            Quiz finalQuiz = quizRepository.save(dummyQuiz);
            System.out.println(finalQuiz);

            return ResponseUtil.getResponseEntity("Working properly", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Error in service portion", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Quiz> getQuizById(Integer id) {
        try {
            if (id != null && quizRepository.existsById(id)) {
                Quiz quiz = quizRepository.findById(id).get();
                return new ResponseEntity<>(quiz, HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new Quiz(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Quiz>> getQuizBySubcategoryId(Integer id) {
        try {
            if (id != null && quizRepository.findBySubcategory_Id(id).isPresent()) {
                List<Quiz> quizes = quizRepository.findBySubcategory_Id(id).get();
//                System.out.println(quizes);
                return new ResponseEntity<>(quizes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Quiz>> getQuizes() {
        try {
            List<Quiz> quizzes = quizRepository.findAll();
            if (quizzes.isEmpty()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(quizzes, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @Transactional
    public ResponseEntity<String> addTest(TestDto requestBody) {
        try {
            if (requestBody != null) {
                Test dummyTest = testMapper.dtoToEntity(requestBody);
                Test finalTest = testRepository.save(dummyTest);
                Quiz quiz = quizRepository.findById(requestBody.getQuizId()).get();
                List<Test> quizTests = quiz.getTests();
                quizTests.add(finalTest);
                quiz.setTests(quizTests);
                quizRepository.save(quiz);
                AppUser appUser = appUserRepository.findById(requestBody.getQuizTakerId()).get();
                List<Test> userTests = appUser.getTests();
                userTests.add(finalTest);
                appUser.setTests(userTests);
                appUserRepository.save(appUser);
                return ResponseUtil.getResponseEntity("Adding Test Successful", HttpStatus.OK);

            } else {
                return ResponseUtil.getResponseEntity("Pass a non null request body", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Error in service portion", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
