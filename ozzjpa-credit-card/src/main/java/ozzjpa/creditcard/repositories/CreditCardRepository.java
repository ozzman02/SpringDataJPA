package ozzjpa.creditcard.repositories;

import ozzjpa.creditcard.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
