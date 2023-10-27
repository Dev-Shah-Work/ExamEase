package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.practice.examease.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
}
