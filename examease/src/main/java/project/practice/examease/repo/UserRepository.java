package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.practice.examease.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
}
