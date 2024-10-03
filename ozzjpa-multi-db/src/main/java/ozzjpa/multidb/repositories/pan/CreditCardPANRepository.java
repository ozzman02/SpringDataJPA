package ozzjpa.multidb.repositories.pan;

import ozzjpa.multidb.domain.pan.CreditCardPAN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardPANRepository extends JpaRepository<CreditCardPAN, Long> {

    Optional<CreditCardPAN> findByCreditCardId(Long id);

}
