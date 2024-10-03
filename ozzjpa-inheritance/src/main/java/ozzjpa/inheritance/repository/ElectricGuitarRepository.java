package ozzjpa.inheritance.repository;

import ozzjpa.inheritance.domain.joinedtable.ElectricGuitar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricGuitarRepository extends JpaRepository<ElectricGuitar, Long> {
}
