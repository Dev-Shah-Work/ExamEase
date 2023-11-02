package project.practice.examease.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int point;
    @Column(name = "is_mcq")
    private boolean isMcq;
    @Column(name = "question_text")
    private String questionText;
    private byte[] img;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private List<Option> options;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id_fk")
    private List<Response> responses;
    @OneToOne
    @JoinColumn(name = "answer_id")
    private Option answer;
    @JoinColumn(name = "answer_text")
    private String answerText;
}
