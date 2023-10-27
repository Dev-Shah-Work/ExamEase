package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.practice.examease.entity.Subcategory;
@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory,Integer> {
}
