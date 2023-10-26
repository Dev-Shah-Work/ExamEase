package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import  project.practice.examease.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz,Integer> {
}
