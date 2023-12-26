package project.practice.examease.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.practice.examease.dto.PortalStatDto;
import project.practice.examease.dto.ResponseDto;
import project.practice.examease.dto.TestDto;
import project.practice.examease.entity.*;
import project.practice.examease.mapper.ResponseMapper;
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
    @Autowired
    private final ResponseMapper responseMapper;

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
                dummyTest.setQuiz(new Quiz(requestBody.getQuizId()));
                Test finalTest = testRepository.save(dummyTest);
                List<Response> testResponses = finalTest.getResponses();
                List<ResponseDto> testResponseDtos = requestBody.getResponses();
                for (int i = 0; i < testResponses.size(); i++) {
                    Response testResponse = testResponses.get(i);
                    ResponseDto testResponseDto = testResponseDtos.get(i);
                    testResponseDto.setId(testResponse.getId());
                }
                Quiz quiz = quizRepository.findById(requestBody.getQuizId()).get();
                List<Test> quizTests = quiz.getTests();
//
                quizTests.add(finalTest);
                quiz.setTests(quizTests);
//
                List<Question> quizQuestions = quiz.getQuestions();
                int i = 0;
                for (Question que : quizQuestions) {
                    for (ResponseDto resDto : testResponseDtos) {
                        if (resDto.getQuestionId() == que.getId()) {
                            List<Response> queResponses = que.getResponses();
                            queResponses.add(responseMapper.dtoToEntity(resDto));
                            que.setResponses(queResponses);
                        }

                    }
                    quizQuestions.set(i, que);
                    i++;

                }
                quiz.setQuestions(null);
                quiz.setQuestions(quizQuestions);
                quizRepository.save(quiz);
                AppUser appUser = appUserRepository.findById(requestBody.getQuizTakerId()).get();
                List<Test> userTests = appUser.getTests();
                userTests.add(finalTest);
                appUser.setTests(userTests);
                List<Response> userResponses = appUser.getResponses();
                for (Response re : finalTest.getResponses()) {
                    userResponses.add(re);
                }
                appUser.setResponses(userResponses);
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

    @Override
    public ResponseEntity<PortalStatDto> getPortalStats() {
        try {
            PortalStatDto portalStatDto = new PortalStatDto();
            portalStatDto.setTotalQuizes(quizRepository.findTotalQuizzes().get());
            portalStatDto.setTotalUsers(appUserRepository.findTotalUsers().get());
            portalStatDto.setTotalTests(testRepository.findTotalTests().get());
            return new ResponseEntity<>(portalStatDto, HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new PortalStatDto(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Quiz>> getQuizesByPagination(int pageSize, int pageNumber, Integer subcategoryId) {
        try {
            Pageable p = PageRequest.of(pageNumber, pageSize);
            Page<Quiz> quizPage;
            if (subcategoryId == null) {
                quizPage = quizRepository.findAll(p);
            } else {
                quizPage = quizRepository.findAll(p, subcategoryId).get();
            }
            List<Quiz> allQuiz = quizPage.getContent();
            return new ResponseEntity<>(allQuiz, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Integer> getQuizCountBySubcategoryId(Integer id) {
        try {
            List<Quiz> quizes = quizRepository.findBySubcategory_Id(id).get();

            int quizCount = quizes.size();
            return new ResponseEntity<>(quizCount, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(0, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
