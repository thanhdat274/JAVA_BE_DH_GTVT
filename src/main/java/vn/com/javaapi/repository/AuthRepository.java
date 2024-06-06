package vn.com.javaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.com.javaapi.entity.Users;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Users, Long>{

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Optional<Users> findByEmail(String email);

}
