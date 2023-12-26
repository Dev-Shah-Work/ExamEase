package project.practice.examease.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.practice.examease.entity.Quiz;
import project.practice.examease.entity.Test;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserDto {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNo;
    private String role;
    private List<Test> tests;
    private List<QuizDto> quizzes;

}
