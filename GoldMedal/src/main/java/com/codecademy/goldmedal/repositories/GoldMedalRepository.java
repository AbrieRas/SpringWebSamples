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

    // get the collection of wins at the Summer Olympics, sorted by year in ascending order
    List<GoldMedal> findByCountryAndSeasonAndEventContainingOrderByYearAsc(String country, String season, String eventContainingText);
    // get the collection of wins at the Winter Olympics
    List<GoldMedal> findAllByCountryAndSeasonAndEventContaining(String country, String season, String eventContainingText);

    // get the total number of events at the Summer Olympics
    int countByCountryAndSeasonAndEventContaining(String country, String season, String eventContainingText);
    // get the total number of events at the Winter Olympics, sorted by year in ascending order
    int countByCountryAndSeasonAndEventContainingOrderByYearAsc(String country, String season, String eventContaining);
    // get the number of wins by gender athletes
    int countByCountryAndSportAndGender(String country, String sport, String gender);
    int countByCountry(String country);
}
