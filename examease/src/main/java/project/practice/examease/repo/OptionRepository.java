package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.practice.examease.entity.Option;
@Repository
public interface OptionRepository extends JpaRepository<Option,Integer> {
}
