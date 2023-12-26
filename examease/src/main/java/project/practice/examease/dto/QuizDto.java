package project.practice.examease.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.practice.examease.entity.AppUser;
import project.practice.examease.entity.Question;
import project.practice.examease.entity.Subcategory;
import project.practice.examease.entity.Test;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDto {
    private int duration;
    private String difficulty;
    private int totalQuizScore;
    private int totalQuizQuestions;
    private String quizName;
}
