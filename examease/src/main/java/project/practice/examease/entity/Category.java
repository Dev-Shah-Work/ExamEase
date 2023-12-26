package project.practice.examease.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "category_text")
    private String categoryText;

//
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Subcategory> subcategories = new ArrayList<>();

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryText='" + categoryText + '\'' +
                '}';
    }
//
}

