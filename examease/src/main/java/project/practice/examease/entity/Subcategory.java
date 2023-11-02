package project.practice.examease.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subcategory_text;
    @ManyToOne
    @JoinColumn(name = "category_id_fk")
    private Category category;
    @OneToMany(mappedBy = "subcategory")
    private List<Quiz> quizzes;
}
