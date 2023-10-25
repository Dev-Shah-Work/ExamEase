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
    private String question_text;
    private byte[] img;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id_fk")
    private List<Option> options;
    @OneToOne(mappedBy = "question",cascade = CascadeType.ALL)
    private Answer answer;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id_fk")
    private List<Response> responses;
}
