package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.practice.examease.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
