package vn.com.javaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.javaapi.entity.OrderDetails;

@Repository
public interface OrderDetailRepository  extends JpaRepository<OrderDetails, Long>, JpaSpecificationExecutor<OrderDetails> {
}
