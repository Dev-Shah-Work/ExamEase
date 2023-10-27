package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.practice.examease.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
