package project.practice.examease.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.practice.examease.entity.Option;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private int id;
    private int score;
    private String responseText;
    private Option responseId;
    private int questionId;
}
