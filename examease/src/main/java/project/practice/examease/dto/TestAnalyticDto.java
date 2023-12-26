package project.practice.examease.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestAnalyticDto {
    private long duration;
    private int maxDuration;
    private int rightQuestions;
    private int wrongQuestions;
    private int unattemptedQuestions;
    private int maxScore;
    private int score;
    private Double avgScore;
    private int id;
    private String testIdentifier;
}
