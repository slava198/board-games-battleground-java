package by.vyun.service;


import by.vyun.model.BoardGame;
import by.vyun.model.City;
import by.vyun.repo.CityRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CityService {
    CityRepo cityRepo;

    public City add(City city) {
        return cityRepo.save(city);
    }

    public List<City> getAllCities() {
        return cityRepo.findAll();
    }

    public List<String> getAllCityNames() {
        List<String> cityNames = new ArrayList<>();
        for (City city : getAllCities()) {
            cityNames.add(city.getName());
        }
        return cityNames;
    }

    public City getCityByName(String cityName) {
        return cityRepo.getFirstByName(cityName);
    }
}
