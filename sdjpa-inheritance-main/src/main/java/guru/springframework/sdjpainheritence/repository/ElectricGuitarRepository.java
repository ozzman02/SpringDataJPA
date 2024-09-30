package guru.springframework.sdjpainheritence.repository;

import guru.springframework.sdjpainheritence.domain.joinedtable.ElectricGuitar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricGuitarRepository extends JpaRepository<ElectricGuitar, Long> {
}
