package ozzjpa.orderservice.repositories;

import ozzjpa.orderservice.domain.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
}
