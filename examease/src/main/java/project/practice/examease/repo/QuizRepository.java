package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  project.practice.examease.entity.Quiz;
@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {
}