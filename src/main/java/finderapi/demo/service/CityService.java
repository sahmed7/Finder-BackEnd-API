package finderapi.demo.service;

import finderapi.demo.exception.InformationExistException;
import finderapi.demo.exception.InformationNotFoundException;
import finderapi.demo.model.City;
import finderapi.demo.model.Restaurant;
import finderapi.demo.model.User;
import finderapi.demo.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    
    private CityRepository cityRepository;

    public UtilityService utility = new UtilityService();
    
    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getCities() {
        List<City> cities = cityRepository.findAll();
        if (cities.isEmpty()) {
            throw new InformationNotFoundException("No cities found!");
        } else {
            return cities;
        }
    }

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
}
