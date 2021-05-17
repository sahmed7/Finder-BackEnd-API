package finderapi.demo.service;

import finderapi.demo.exception.InformationExistException;
import finderapi.demo.exception.InformationNotFoundException;
import finderapi.demo.model.City;
import finderapi.demo.model.User;
import finderapi.demo.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    
    private CityRepository cityRepository;

    public UtilityService utility = new UtilityService();
    
    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    // Create a city
    public City createCity(City cityObject) {
        System.out.println("service calling createCategory ==>");
        User user = utility.getAuthenticatedUser();
        City city = cityRepository.findByUserIdAndName(user.getId(), cityObject.getName());

        if(city != null) {
            throw new InformationExistException("city with name " + cityObject.getName() + " already exists");
        } else {
            cityObject.setUser(user);
            return cityRepository.save(cityObject);
        }
    }

    // Get all cities
    public List<City> getCities() {
        List<City> cities = cityRepository.findAll();
        if (cities.isEmpty()) {
            throw new InformationNotFoundException("No cities found!");
        } else {
            return cities;
        }
    }

    // Get a single city
    public Optional<City> getSingleCity(Long cityId) {
        System.out.println("service calling getCity ==>");

        Optional<City> city = cityRepository.findById(cityId);
        if (city.isPresent()) {
            return city;
            } else {
            throw new InformationNotFoundException("city with id " + cityId + " not found");
        }
    }
}
