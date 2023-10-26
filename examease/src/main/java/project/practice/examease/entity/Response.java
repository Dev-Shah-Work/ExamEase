package project.practice.examease.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int score;
    @Column(name = "response_text")
    private String responseText;
    @OneToOne
    @JoinColumn(name = "response_id")
    private Option responseId;
}
