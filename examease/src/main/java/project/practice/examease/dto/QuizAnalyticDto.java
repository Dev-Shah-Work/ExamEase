package project.practice.examease.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnalyticDto {
    private double avgDuration;
    private int duration;
    private int maxScore;
    private int minScore;
    private double avgScore;
    private int id;
    private int attemptCount;
    private String quizName;
}
