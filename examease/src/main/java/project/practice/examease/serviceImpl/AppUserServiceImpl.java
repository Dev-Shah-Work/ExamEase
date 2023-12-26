package project.practice.examease.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.practice.examease.dto.AppUserDto;
import project.practice.examease.dto.LeaderBoardDTO;
import project.practice.examease.dto.QuizAnalyticDto;
import project.practice.examease.dto.TestAnalyticDto;
import project.practice.examease.entity.AppUser;
import project.practice.examease.entity.Quiz;
import project.practice.examease.entity.Response;
import project.practice.examease.entity.Test;
import project.practice.examease.mapper.LeaderBoardMapper;
import project.practice.examease.mapper.UserMapper;
import project.practice.examease.repo.AppUserRepository;
import project.practice.examease.repo.QuizRepository;
import project.practice.examease.repo.TestRepository;
import project.practice.examease.security.CustomUserDetailService;
import project.practice.examease.security.JwtUtil;
import project.practice.examease.service.AppUserService;
import project.practice.examease.util.ResponseUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    CustomUserDetailService customUserDetailService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TestRepository testRepository;
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    LeaderBoardMapper leaderBoardMapper;

    @Override
    public ResponseEntity<String> register(AppUser requestMap) {
        try {
            AppUser appUser = appUserRepository.findByEmail(requestMap.getEmail());
            if (Objects.isNull(appUser)) {
                String password = passwordEncoder.encode(requestMap.getPassword());
                requestMap.setPassword(password);
                appUserRepository.save(requestMap);
                return ResponseUtil.getResponseEntity("Registration Successful", HttpStatus.OK);
            } else {
                return ResponseUtil.getResponseEntity("User already exist", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Something went wrong in service Implementation", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(HashMap<String, String> requestBody) {
        try {
            System.out.println(requestBody.toString());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestBody.get("email"), requestBody.get("password")));
            if (authentication.isAuthenticated()) {
                System.out.println(customUserDetailService.getUserDetail().getEmail());
                System.out.println(customUserDetailService.getUserDetail().getRole());
                System.out.println(jwtUtil.generateToken(customUserDetailService.getUserDetail().getEmail(),
                        customUserDetailService.getUserDetail().getRole()));
                return new ResponseEntity<String>("{\"token\":\"" +
                        jwtUtil.generateToken(customUserDetailService.getUserDetail().getEmail(),
                                customUserDetailService.getUserDetail().getRole()) + "\",\"role\":\"" + customUserDetailService.getUserDetail().getRole() + "\", \"id\" : \"" + customUserDetailService.getUserDetail().getId() + "\"}", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Something went wrong in service Implementation", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    @Transactional
    public ResponseEntity<AppUserDto> findById(Integer id) {
        try {
            Optional<AppUser> user = appUserRepository.findById(id);
//            System.out.println(user);

            if (user.isPresent()) {
                AppUser appUser = user.get();
//                System.out.println(appUser);
                return new ResponseEntity<>(userMapper.convertAppUserToUserDTO(appUser), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new AppUserDto(), HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new AppUserDto(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateUser(Integer id, HashMap<String, String> requestBody) {

        try {
            Optional<AppUser> user = appUserRepository.findById(id);

            if (user.isPresent()) {
                AppUser appUser = user.get();
                if (requestBody.containsKey("email")) {
                    AppUser dummyUser = appUserRepository.findByEmail(requestBody.get("email"));
                    if (Objects.isNull(dummyUser) || (dummyUser == appUser)) {
                        appUser.setEmail(requestBody.get("email"));

                    } else {
                        return ResponseUtil.getResponseEntity("User already exist", HttpStatus.BAD_REQUEST);
                    }
                }
                if (requestBody.containsKey("firstname")) {
                    appUser.setFirstname(requestBody.get("firstname"));
                }
                if (requestBody.containsKey("lastname")) {
                    appUser.setLastname(requestBody.get("lastname"));
                }

                if (requestBody.containsKey("phoneNo")) {
                    appUser.setPhoneNo(requestBody.get("phoneNo"));
                }
                appUserRepository.save(appUser);
                return ResponseUtil.getResponseEntity("Update Successful", HttpStatus.OK);
            } else {
                return ResponseUtil.getResponseEntity("Bad Request", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Something went wrong in service Implementation", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<TestAnalyticDto>> getTestAnalytics(int id) {
        try {
                AppUser user = appUserRepository.findById(id).get();
            List<TestAnalyticDto> testAnalyticDtoList = new ArrayList<>();
            List<Test> userTests=user.getTests();
            Comparator<Test> attemptTimeComparator = Comparator.comparing(Test::getAttemptTime);

            // Sort the tests list in descending order of attemptTime
            Collections.sort(userTests, attemptTimeComparator.reversed());
            System.out.println(userTests);

//            List<Test> sortedTests=Collections.sort(userTests, Comparator.comparing(Test::getAttemptTime).reversed());
            for (Test test : userTests) {
                TestAnalyticDto testAnalyticDto = new TestAnalyticDto();
                testAnalyticDto.setId(test.getId());
                Double avgScore = testRepository.fetchTestAvgScore(test.getId()).orElse(0.0);
                int maxDuration = testRepository.fetchTestMaxDuration(test.getId()).orElse(0);
                int maxScore = testRepository.fetchTestMaxScore(test.getId()).orElse(0);
                int totalQuestion = testRepository.fetchTestTotalQuestion(test.getId()).orElse(0);
                String testIdentifier=testRepository.fetchTestQuizName(test.getId()).orElse("");
                String attempTime=test.getAttemptTime().toString();
                testIdentifier=testIdentifier.concat(" ").concat(attempTime);
                Instant timeStamp1 = (test.getAttemptTime()).toInstant();
                Instant timeStamp2 = (test.getCompletionTime()).toInstant();
                Duration duration = Duration.between(timeStamp1, timeStamp2);
                testAnalyticDto.setDuration(Math.abs(duration.toSeconds()));
                testAnalyticDto.setMaxDuration(maxDuration * 60);
                testAnalyticDto.setAvgScore(avgScore);
                testAnalyticDto.setScore(test.getTestScore());
                testAnalyticDto.setTestIdentifier(testIdentifier);
                testAnalyticDto.setMaxScore(maxScore);
                int rightQuestions = 0;
                int wrongQuestions = 0;
                int unattemptedQuestions = 0;
                for (Response response : test.getResponses()) {
                    if (response.getScore() > 0) rightQuestions++;
                    else wrongQuestions++;
                }
                unattemptedQuestions = totalQuestion - (rightQuestions + wrongQuestions);
                testAnalyticDto.setRightQuestions(rightQuestions);
                testAnalyticDto.setWrongQuestions(wrongQuestions);
                testAnalyticDto.setUnattemptedQuestions(unattemptedQuestions);
                testAnalyticDtoList.add(testAnalyticDto);

            }
            return new ResponseEntity<>(testAnalyticDtoList, HttpStatus.OK);
//            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<QuizAnalyticDto>> getQuizAnalytics(int id) {
        try {
            List<Quiz> quizList = quizRepository.findQuizesByUser(id).get();
//            System.out.println(quizList);
            List<QuizAnalyticDto> quizAnalyticDtos = new ArrayList<>();
            for (Quiz quiz : quizList) {
                QuizAnalyticDto quizAnalyticDto = new QuizAnalyticDto();
                int count = 0;
                int maxScore = 0;
                int minScore = 0;
                int avgScore = 0;
                int totalScore = 0;
                int totalDuration = 0;
                int avgDuration = 0;
                for (Test test : quiz.getTests()) {
                    count++;
                    maxScore = Math.max(maxScore, test.getTestScore());
                    minScore = Math.min(minScore, test.getTestScore());
                    totalScore += test.getTestScore();
                    Instant timeStamp1 = (test.getAttemptTime()).toInstant();
                    Instant timeStamp2 = (test.getCompletionTime()).toInstant();
                    Duration duration = Duration.between(timeStamp1, timeStamp2);
                    totalDuration += Math.abs(duration.toSeconds());

                }
                if(count!=0){
                    avgScore = totalScore / count;
                    avgDuration = totalDuration / count;
                }
                quizAnalyticDto.setDuration(quiz.getDuration()*60);
                quizAnalyticDto.setAvgScore(avgScore);
                quizAnalyticDto.setMaxScore(maxScore);
                quizAnalyticDto.setMinScore(minScore);
                quizAnalyticDto.setAvgDuration(avgDuration);
                quizAnalyticDto.setId(quiz.getId());
                quizAnalyticDto.setAttemptCount(count);
                quizAnalyticDto.setQuizName(quiz.getQuizName());
                quizAnalyticDtos.add(quizAnalyticDto);
            }
            return new ResponseEntity<>(quizAnalyticDtos, HttpStatus.OK);


        }
//        try {
//            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.OK);
//        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<LeaderBoardDTO>> getLeaderBoardStats() {
         try{
             System.out.println(appUserRepository.getLeaderBoardStats().get());
             // Assuming LeaderBoardDTO is the correct type for your DTO
//             List<LeaderBoardDTO> leaderBoardDTOS = appUserRepository.getLeaderBoardStats()
//                     .map(list -> (List<LeaderBoardDTO>) list)
//                     .orElse(Collections.emptyList());
//             List<LeaderBoardDTO> leaderBoardDTOS=appUserRepository.getLeaderBoardStats().get();
             List<Map<String, Object>> queryResult = appUserRepository.getLeaderBoardStats().get();
             List<LeaderBoardDTO> leaderBoardDTOList = leaderBoardMapper.convertToDTO(queryResult);

             return new ResponseEntity<>(leaderBoardDTOList,HttpStatus.OK);
         }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
