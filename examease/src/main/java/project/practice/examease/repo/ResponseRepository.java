package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.practice.examease.entity.Response;
@Repository
public interface ResponseRepository extends JpaRepository<Response,Integer> {
}
