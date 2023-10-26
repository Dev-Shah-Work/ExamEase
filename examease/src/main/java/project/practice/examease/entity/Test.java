package project.practice.examease.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "test_score")
    private int testScore;
    @Column(name = "attempt_time")
    private Timestamp attemptTime;
    @Column(name = "completion_time")
    private Timestamp completionTime;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id_fk")
    private List<Response> responses;

}
