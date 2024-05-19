package vn.com.javaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.com.javaapi.bean.AuthResponse;
import vn.com.javaapi.entity.Users;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Users, Long>{

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Optional<Users> findByEmail(String email);

    //AuthResponse signUp(Users user);

    //Users logIn(Users user);
    //
    //Users forgotPassword(String email);
    //
    //AuthResponse resetPassword(String email, Users users);
}
