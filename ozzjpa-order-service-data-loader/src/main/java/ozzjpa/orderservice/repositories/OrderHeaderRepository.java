package ozzjpa.orderservice.repositories;

import ozzjpa.orderservice.domain.Customer;
import ozzjpa.orderservice.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {

    List<OrderHeader> findAllByCustomer(Customer customer);

}
