package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.practice.examease.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
