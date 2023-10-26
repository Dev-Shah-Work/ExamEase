package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.practice.examease.entity.Test;

public interface TestRepository extends JpaRepository<Test,Integer> {
}
