package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.practice.examease.entity.Test;
@Repository
public interface TestRepository extends JpaRepository<Test,Integer> {
}
