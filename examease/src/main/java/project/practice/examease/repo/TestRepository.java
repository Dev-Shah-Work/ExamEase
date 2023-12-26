package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.practice.examease.entity.Test;

import java.util.HashMap;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test,Integer> {
    @Query(value = "SELECT SUM(q.point) AS max_score FROM test t JOIN question q ON t.quiz_id_fk=q.quiz_id_fk WHERE t.id=:id ", nativeQuery = true)
    Optional<Integer> fetchTestMaxScore(@Param("id") int id);
    @Query(value="SELECT COUNT(q) AS total_questions FROM test t JOIN question q ON t.quiz_id_fk=q.quiz_id_fk WHERE t.id=:id ",nativeQuery = true)
    Optional<Integer> fetchTestTotalQuestion(@Param("id") int id);
    @Query(value ="SELECT q.duration FROM quiz q WHERE q.id=(SELECT quiz_id_fk FROM test t WHERE t.id=:id)",nativeQuery = true)
    Optional<Integer> fetchTestMaxDuration(@Param("id")int id);
    @Query(value ="SELECT AVG(t.test_score) AS avg_score FROM test t WHERE t.quiz_id_fk=(SELECT quiz_id_fk FROM test t WHERE t.id=:id) GROUP BY t.quiz_id_fk",nativeQuery = true)
    Optional<Double> fetchTestAvgScore(@Param("id")int id);
    @Query(value="SELECT q.quiz_name FROM test t JOIN quiz q ON t.quiz_id_fk=q.id where t.id=:id",nativeQuery = true)
    Optional<String> fetchTestQuizName(@Param("id")int id);
    @Query(value = "SELECT COUNT(id) FROM test",nativeQuery = true)
    Optional<Integer> findTotalTests();

}
