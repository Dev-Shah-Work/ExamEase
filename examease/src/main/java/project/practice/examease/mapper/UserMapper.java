package project.practice.examease.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.practice.examease.dto.AppUserDto;
import project.practice.examease.dto.QuizDto;
import project.practice.examease.entity.AppUser;
import project.practice.examease.entity.Quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final QuizMapper quizMapper;
    public AppUserDto convertAppUserToUserDTO(AppUser appUser) {
        System.out.println(appUser.getQuizzes());
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setId(appUser.getId());
        appUserDto.setRole(appUser.getRole());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setFirstname(appUser.getFirstname());
        appUserDto.setLastname(appUser.getLastname());
        appUserDto.setPhoneNo(appUser.getPhoneNo());
        appUserDto.setTests(appUser.getTests());
        List<QuizDto> quizDtos=new ArrayList<>();
        for(Quiz quiz:appUser.getQuizzes()){
            QuizDto quizDto=quizMapper.entityToDto(quiz);
            quizDtos.add(quizDto);
        }
        appUserDto.setQuizzes(quizDtos);
        return appUserDto;
    }

    public Optional<AppUserDto> convertAppUserToUserDTO(Optional<AppUser> appUser) {
        return appUser.map(this::convertAppUserToUserDTO);
    }


}
