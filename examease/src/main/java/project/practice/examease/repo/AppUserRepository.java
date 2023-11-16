package project.practice.examease.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.practice.examease.dto.AppUserDto;
import project.practice.examease.entity.AppUser;
@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Integer> {
    AppUser findByEmail(@Param("email") String email);

}
