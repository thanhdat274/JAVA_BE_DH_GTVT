package vn.com.javaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.com.javaapi.entity.Carts;

public interface CartRepository extends JpaRepository<Carts, Long>, JpaSpecificationExecutor<Carts> {
}
