package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import project.practice.examease.entity.Option;

public interface OptionRepository extends JpaRepository<Option,Integer> {
}
