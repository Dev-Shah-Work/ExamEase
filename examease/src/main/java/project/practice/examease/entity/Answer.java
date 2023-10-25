package project.practice.examease.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String answer_text;
    @OneToOne
    @JoinColumn(name = "question_id_fk")
    private Question question;
    @OneToOne
    @JoinColumn(name = "option_id_fk")
    private Option option;
}
