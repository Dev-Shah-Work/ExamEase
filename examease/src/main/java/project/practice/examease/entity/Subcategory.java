package project.practice.examease.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "subcategory_text")
    private String subcategoryText;

    @ManyToOne
    @JoinColumn(name = "category_id_fk")
    @JsonIgnore
    private Category category;
    @OneToMany(mappedBy = "subcategory",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Quiz> quizzes;

//    @Override
//    public String toString() {
//        return "Subcategory{" +
//                "id=" + id +
//                ", subcategoryText='" + subcategoryText + '\'' +
//                ", quizzes=" + quizzes +
//                '}';
//    }
}
