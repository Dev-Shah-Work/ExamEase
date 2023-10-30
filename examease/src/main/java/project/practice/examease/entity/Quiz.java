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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int duration;
    private String difficulty;
    @ManyToOne
    @JoinColumn(name="subcategory_id_fk")
    private Subcategory subcategory;
    @ManyToOne
    @JoinColumn(name="quiz_maker_id")
    private AppUser user;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id_fk")
    private List<Test> tests;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id_fk")
    private List<Question> questions;

}
