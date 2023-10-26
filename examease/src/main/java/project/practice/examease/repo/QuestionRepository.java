package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.practice.examease.entity.Question;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
}
