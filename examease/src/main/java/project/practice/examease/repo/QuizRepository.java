package project.practice.examease.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import  project.practice.examease.entity.Quiz;

import java.util.List;
import java.util.Optional;

@Repository
@CrossOrigin("*")
public interface QuizRepository extends JpaRepository<Quiz,Integer> {
    Optional<List<Quiz>> findBySubcategory_Id(int subcategoryId);
    @Query(value = "SELECT COUNT(id) FROM quiz",nativeQuery = true)
    Optional<Integer> findTotalQuizzes();
//    @Query(value="SELECT * FROM quiz",nativeQuery = true)
//    List<Quiz> findQuizesByUser();

    @Query(value="SELECT q FROM Quiz q WHERE q.user.id = :id",nativeQuery = false)
    Optional<List<Quiz>> findQuizesByUser(@Param("id") int id);
    @Query(value = "SELECT q FROM Quiz q WHERE q.subcategory.id=:subcategoryId",nativeQuery = false)
    Optional<Page<Quiz>> findAll(Pageable p, @Param("subcategoryId")int subcategoryId);
}
