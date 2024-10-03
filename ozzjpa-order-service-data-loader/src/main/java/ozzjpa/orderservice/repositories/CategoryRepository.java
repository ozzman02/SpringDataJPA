package ozzjpa.orderservice.repositories;

import ozzjpa.orderservice.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}