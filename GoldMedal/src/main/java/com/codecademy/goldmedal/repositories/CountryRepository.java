package com.codecademy.goldmedal.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codecademy.goldmedal.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, Integer> {
    // Name
    List<Country> findAllByOrderByNameAsc();
    List<Country> findAllByOrderByNameDesc();

    // Gdp
    List<Country> findAllByOrderByGdpAsc();
    List<Country> findAllByOrderByGdpDesc();

    // Population
    List<Country> findAllByOrderByPopulationAsc();
    List<Country> findAllByOrderByPopulationDesc();

    Optional<Country> findByName(String name);
}
