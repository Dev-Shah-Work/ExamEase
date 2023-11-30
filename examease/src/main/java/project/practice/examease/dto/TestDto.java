package project.practice.examease.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.practice.examease.entity.Response;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {
    private int testScore;
    private Timestamp attemptTime;
    private Timestamp completionTime;
    private List<Response> responses;
    private int quizTakerId;
    private int quizId;
}
