package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import project.practice.examease.dto.AppUserDto;
import project.practice.examease.dto.LeaderBoardDTO;
import project.practice.examease.entity.AppUser;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Integer> {
    AppUser findByEmail(@Param("email") String email);
    @Query(value = "SELECT COUNT(id) FROM _user",nativeQuery = true)
    Optional<Integer> findTotalUsers();
    @Query(value = "SELECT u.id,CONCAT(u.firstname,' ',u.lastname) AS fullname,SUM(t.test_score) AS totalscore,RANK() OVER(ORDER BY SUM(t.test_score)desc) AS rank FROM test t LEFT JOIN _user u ON t.quiz_taker_id_fk=u.id GROUP BY u.id;",nativeQuery = true)
    Optional<List<Map<String,Object>>> getLeaderBoardStats();

}
