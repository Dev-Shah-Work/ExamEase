package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.practice.examease.entity.Response;

public interface ResponseRepository extends JpaRepository<Response,Integer> {
}
