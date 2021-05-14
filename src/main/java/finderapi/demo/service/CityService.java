package finderapi.demo.service;

import finderapi.demo.model.City;
import finderapi.demo.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CityService {
    
    private CityRepository cityRepository;
    
    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
    
    public List<City> getCities() {
        System.out.println("service calling getCities===>");
    }

    public City createCity(City cityObject) {
    }
}
