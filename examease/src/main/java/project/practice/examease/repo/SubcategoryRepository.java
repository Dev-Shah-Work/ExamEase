package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.practice.examease.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory,Integer> {
}
