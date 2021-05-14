package finderapi.demo.service;

import finderapi.demo.exception.InformationExistException;
import finderapi.demo.exception.InformationNotFoundException;
import finderapi.demo.model.City;
import finderapi.demo.model.Restaurant;
import finderapi.demo.model.User;
import finderapi.demo.repository.CityRepository;
import finderapi.demo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public UtilityService utility = new UtilityService();

    public List<City> getCities() {
        User user = utility.getAuthenticatedUser();
        List<City> cities = cityRepository.findAll();
        if (cities.isEmpty()) {
            throw new InformationNotFoundException("No cities found!");
        } else {
            return cities;
        }
    }

    public City createCity(City cityObject) {

        User user = utility.getAuthenticatedUser();

        if (cityRepository.findByUserIdAndName(user.getId(), cityObject.getName()) != null){
            throw new InformationExistException("city exists!");
        } else {
            cityObject.setUser(user);
            return cityRepository.save(cityObject);
        }
    }
}
