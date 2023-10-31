package project.practice.examease.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Column(name = "phone_no")
    private String phoneNo;
    private String role;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_taker_id_fk")
    private List<Response> responses;
    @OneToMany(mappedBy = "user")
    private List<Quiz> quizzes;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_taker_id_fk")
    private List<Test> tests;

}
