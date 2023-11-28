package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  project.practice.examease.entity.Quiz;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {
    Optional<List<Quiz>> findBySubcategory_Id(int subcategoryId);
}
