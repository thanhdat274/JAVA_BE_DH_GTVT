package vn.com.javaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.javaapi.entity.Cart;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;


public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    List<Cart> findByUserId(@Param("userId") Long userId);
}
