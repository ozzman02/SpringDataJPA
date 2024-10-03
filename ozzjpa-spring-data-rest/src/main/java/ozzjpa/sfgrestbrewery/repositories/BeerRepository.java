package ozzjpa.sfgrestbrewery.repositories;


import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ozzjpa.sfgrestbrewery.domain.Beer;
import ozzjpa.sfgrestbrewery.domain.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@RepositoryRestResource(path = "beer", collectionResourceRel = "beer")
public interface BeerRepository extends JpaRepository<Beer, UUID> {

    Page<Beer> findAllByBeerName(String beerName, Pageable pageable);

    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyle, Pageable pageable);

    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageable);

    Beer findByUpc(String upc);

}
