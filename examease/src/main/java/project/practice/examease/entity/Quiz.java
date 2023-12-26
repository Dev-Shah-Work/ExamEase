package project.practice.examease.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public Quiz(int id){
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

    private int duration;
    private String difficulty;
    @Column(name = "quiz_name")
    private String quizName;
    @ManyToOne
    @JoinColumn(name="subcategory_id_fk")
    private Subcategory subcategory;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "quiz")
    private List<Test> tests;

    @ManyToOne
    @JoinColumn(name="quiz_maker_id")

    private AppUser user;



    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id_fk")
    private List<Question> questions;

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", duration=" + duration +
                ", difficulty='" + difficulty + '\'' +
                ", quizName='" + quizName + '\'' +
                ", subcategory=" + subcategory +
                ", user=" + user +
                ", tests=" + tests +
                ", questions=" + questions +
                '}';
    }
}
