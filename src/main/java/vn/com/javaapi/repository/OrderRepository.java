package vn.com.javaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.javaapi.entity.Orders;
import vn.com.javaapi.entity.Products;

@Repository
public interface OrderRepository  extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {
}
