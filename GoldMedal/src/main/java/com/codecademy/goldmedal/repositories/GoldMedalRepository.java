package com.codecademy.goldmedal.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codecademy.goldmedal.model.GoldMedal;

import java.util.List;

public interface GoldMedalRepository extends CrudRepository<GoldMedal, Integer> {
    // year
    List<GoldMedal> findByCountryOrderByYearAsc(String country);
    List<GoldMedal> findByCountryOrderByYearDesc(String country);

    // season
    List<GoldMedal> findByCountryOrderBySeasonAsc(String country);
    List<GoldMedal> findByCountryOrderBySeasonDesc(String country);

    // city
    List<GoldMedal> findByCountryOrderByCityAsc(String country);
    List<GoldMedal> findByCountryOrderByCityDesc(String country);

    // name
    List<GoldMedal> findByCountryOrderByNameAsc(String country);
    List<GoldMedal> findByCountryOrderByNameDesc(String country);

    // event
    List<GoldMedal> findByCountryOrderByEventAsc(String country);
    List<GoldMedal> findByCountryOrderByEventDesc(String country);

    List<GoldMedal> findByCountryAndSeasonOrderByYearAsc(String country, String season);
    List<GoldMedal> findAllByCountryAndSeason(String country, String season);

    int countBySeason(String season);
    int countByCountryAndGender(String country, String gender);
    int countByCountry(String country);
}
