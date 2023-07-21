package com.codecademy.goldmedal.controller;

import com.codecademy.goldmedal.model.*;
import org.apache.commons.text.WordUtils;
import org.springframework.web.bind.annotation.*;

import com.codecademy.goldmedal.repositories.CountryRepository;
import com.codecademy.goldmedal.repositories.GoldMedalRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/countries")
public class GoldMedalController {
    // TODO-DONE: declare references to your repositories
    private final CountryRepository countryRepository;
    private final GoldMedalRepository goldMedalRepository;

    // TODO-DONE: update your constructor to include your repositories
    public GoldMedalController(final CountryRepository countryRepository, final GoldMedalRepository goldMedalRepository) {
        this.countryRepository = countryRepository;
        this.goldMedalRepository = goldMedalRepository;
    }

    @GetMapping
    public CountriesResponse getCountries(@RequestParam String sort_by, @RequestParam String ascending) {
        var ascendingOrder = ascending.toLowerCase().equals("y");
        return new CountriesResponse(getCountrySummaries(sort_by.toLowerCase(), ascendingOrder));
    }

    @GetMapping("/{country}")
    public CountryDetailsResponse getCountryDetails(@PathVariable String country) {
        String countryName = WordUtils.capitalizeFully(country);
        return getCountryDetailsResponse(countryName);
    }

    @GetMapping("/{country}/medals")
    public CountryMedalsListResponse getCountryMedalsList(@PathVariable String country, @RequestParam String sort_by, @RequestParam String ascending) {
        String countryName = WordUtils.capitalizeFully(country);
        var ascendingOrder = ascending.toLowerCase().equals("y");
        return getCountryMedalsListResponse(countryName, sort_by.toLowerCase(), ascendingOrder);
    }

    private CountryMedalsListResponse getCountryMedalsListResponse(String countryName, String sortBy, boolean ascendingOrder) {
        List<GoldMedal> medalsList;
        switch (sortBy) {
            case "year":
//                medalsList = // TODO-DONE: list of medals sorted by year in the given order
                if (ascendingOrder) {
                    medalsList = this.goldMedalRepository.findByCountryOrderByYearAsc(countryName);
                    break;
                }
                medalsList = this.goldMedalRepository.findByCountryOrderByYearDesc(countryName);
                break;
            case "season":
//                medalsList = // TODO-DONE: list of medals sorted by season in the given order
                if (ascendingOrder) {
                    medalsList = this.goldMedalRepository.findByCountryOrderBySeasonAsc(countryName);
                    break;
                }
                medalsList = this.goldMedalRepository.findByCountryOrderBySeasonDesc(countryName);
                break;
            case "city":
//                medalsList = // TODO-DONE: list of medals sorted by city in the given order
                if (ascendingOrder) {
                    medalsList = this.goldMedalRepository.findByCountryOrderByCityAsc(countryName);
                    break;
                }
                medalsList = this.goldMedalRepository.findByCountryOrderByCityDesc(countryName);
                break;
            case "name":
//                medalsList = // TODO-DONE: list of medals sorted by athlete's name in the given order
                if (ascendingOrder) {
                    medalsList = this.goldMedalRepository.findByCountryOrderByNameAsc(countryName);
                    break;
                }
                medalsList = this.goldMedalRepository.findByCountryOrderByNameDesc(countryName);
                break;
            case "event":
//                medalsList = // TODO-DONE: list of medals sorted by event in the given order
                if (ascendingOrder) {
                    medalsList = this.goldMedalRepository.findByCountryOrderByEventAsc(countryName);
                    break;
                }
                medalsList = this.goldMedalRepository.findByCountryOrderByEventDesc(countryName);
                break;
            default:
                medalsList = new ArrayList<>();
                break;
        }

        return new CountryMedalsListResponse(medalsList);
    }

    private CountryDetailsResponse getCountryDetailsResponse(String countryName) {
        var countryOptional = this.countryRepository.findByName(countryName); // TODO-DONE: get the country; this repository method should return a java.util.Optional
        if (countryOptional.isEmpty()) {
            return new CountryDetailsResponse(countryName);
        }

        Country country = countryOptional.get();
        var goldMedalCount = this.goldMedalRepository.countByCountry(countryName); // TODO-DONE: get the medal count

        // Easy to read
        String eventContainingText = "Olympic";
        String season = "Summer";

        var summerWins = this.goldMedalRepository.findByCountryAndSeasonAndEventContainingOrderByYearAsc(countryName, season, eventContainingText); // TODO: get the collection of wins at the Summer Olympics, sorted by year in ascending order
        var numberSummerWins = summerWins.size() > 0 ? summerWins.size() : null;
        var totalSummerEvents = this.goldMedalRepository.countByCountryAndSeasonAndEventContaining(countryName, season, eventContainingText); // TODO: get the total number of events at the Summer Olympics
        var percentageTotalSummerWins = totalSummerEvents != 0 && numberSummerWins != null ? (float) summerWins.size() / totalSummerEvents : null;
        var yearFirstSummerWin = summerWins.size() > 0 ? summerWins.get(0).getYear() : null;

        // Re-assign easy to read
        season = "Winter";

        var winterWins = this.goldMedalRepository.findAllByCountryAndSeasonAndEventContaining(countryName, season, eventContainingText); // TODO: get the collection of wins at the Winter Olympics
        var numberWinterWins = winterWins.size() > 0 ? winterWins.size() : null;
        var totalWinterEvents = this.goldMedalRepository.countByCountryAndSeasonAndEventContainingOrderByYearAsc(countryName, season, eventContainingText); // TODO: get the total number of events at the Winter Olympics, sorted by year in ascending order
        var percentageTotalWinterWins = totalWinterEvents != 0 && numberWinterWins != null ? (float) winterWins.size() / totalWinterEvents : null;
        var yearFirstWinterWin = winterWins.size() > 0 ? winterWins.get(0).getYear() : null;

        // Easy to read
        String sport = "Athletics";
        String gender = "Female";

        var numberEventsWonByFemaleAthletes = this.goldMedalRepository.countByCountryAndSportAndGender(countryName, sport, gender); // TODO: get the number of wins by female athletes

        // Re-assign easy to read
        gender = "Male";

        var numberEventsWonByMaleAthletes = this.goldMedalRepository.countByCountryAndSportAndGender(countryName, sport, gender); // TODO: get the number of wins by male athletes

        return new CountryDetailsResponse(
                countryName,
                country.getGdp(),
                country.getPopulation(),
                goldMedalCount,
                numberSummerWins,
                percentageTotalSummerWins,
                yearFirstSummerWin,
                numberWinterWins,
                percentageTotalWinterWins,
                yearFirstWinterWin,
                numberEventsWonByFemaleAthletes,
                numberEventsWonByMaleAthletes);
    }

    private List<CountrySummary> getCountrySummaries(String sortBy, boolean ascendingOrder) {
        List<Country> countries;
        switch (sortBy) {
            case "name":
//                countries = // TODO: list of countries sorted by name in the given order
                if (ascendingOrder) {
                    countries = this.countryRepository.findAllByOrderByNameAsc();
                    break;
                }
                countries = this.countryRepository.findAllByOrderByNameDesc();
                break;
            case "gdp":
//                countries = // TODO: list of countries sorted by gdp in the given order
                if (ascendingOrder) {
                    countries = this.countryRepository.findAllByOrderByGdpAsc();
                    break;
                }
                countries = this.countryRepository.findAllByOrderByGdpDesc();
                break;
            case "population":
//                countries = // TODO: list of countries sorted by population in the given order
                if (ascendingOrder) {
                    countries = this.countryRepository.findAllByOrderByPopulationAsc();
                    break;
                }
                countries = this.countryRepository.findAllByOrderByPopulationDesc();
                break;
            case "medals":
            default:
//                countries = // TODO: list of countries in any order you choose; for sorting by medal count, additional logic below will handle that
                if (ascendingOrder) {
                    countries = this.countryRepository.findAllByOrderByNameAsc();
                    break;
                }
                countries = this.countryRepository.findAllByOrderByNameDesc();
                break;
        }

        var countrySummaries = getCountrySummariesWithMedalCount(countries);

        if (sortBy.equalsIgnoreCase("medals")) {
            countrySummaries = sortByMedalCount(countrySummaries, ascendingOrder);
        }

        return countrySummaries;
    }

    private List<CountrySummary> sortByMedalCount(List<CountrySummary> countrySummaries, boolean ascendingOrder) {
        return countrySummaries.stream()
                .sorted((t1, t2) -> ascendingOrder ?
                        t1.getMedals() - t2.getMedals() :
                        t2.getMedals() - t1.getMedals())
                .collect(Collectors.toList());
    }

    private List<CountrySummary> getCountrySummariesWithMedalCount(List<Country> countries) {
        List<CountrySummary> countrySummaries = new ArrayList<>();
        for (var country : countries) {
            var goldMedalCount = this.goldMedalRepository.countByCountry(country.getName()); // TODO-DONE: get count of medals for the given country
            countrySummaries.add(new CountrySummary(country, goldMedalCount));
        }
        return countrySummaries;
    }
}
