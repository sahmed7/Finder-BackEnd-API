package finderapi.demo.service;

import finderapi.demo.exception.InformationExistException;
import finderapi.demo.exception.InformationNotFoundException;
import finderapi.demo.model.City;
import finderapi.demo.model.Restaurant;
import finderapi.demo.model.User;
import finderapi.demo.repository.CityRepository;
import finderapi.demo.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    
    private CityRepository cityRepository;
    private RestaurantRepository restaurantRepository;

    public UtilityService utility = new UtilityService();
    
    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public void setRestaurantRepository(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    // Create a city
    public City createCity(City cityObject) {
        System.out.println("service calling createCategory ==>");
        // User user = utility.getAuthenticatedUser();
        // City city = cityRepository.findByUserIdAndName(user.getId(), cityObject.getName());
        City city = cityRepository.findByName(cityObject.getName());
        if(city!=null) {
            throw new InformationExistException("city with name " + cityObject.getName() + " already exists");
        } else {
            //cityObject.setUser(user);
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

    // Update a single city
    public City updateCity(Long cityId, City cityObject) {
        System.out.println("service calling updateCity ==>");
        //User user = utility.getAuthenticatedUser();
        Optional<City> city = cityRepository.findById(cityId);
        if (!city.isPresent()) {
            throw new InformationNotFoundException("city with id " + cityId + " not found");
        } else {
            city.get().setName(cityObject.getName());
            return cityRepository.save(city.get());
        }
    }

    // Delete a single city
    public String deleteCity(Long cityId) {
        System.out.println("service calling deleteCity ==>");
        User user = utility.getAuthenticatedUser();
        Optional<City> city = cityRepository.findById(cityId);
        List<Restaurant> restaurantList = city.get().getRestaurantList();
        if (city == null) {
            throw new InformationNotFoundException("City with id " + cityId + " not found");
        }
        if (restaurantList.isEmpty()) {
            cityRepository.deleteById(cityId);
            return "City with id " + cityId + " has been successfully deleted";
        } else {
            throw new InformationExistException("Sorry, cannot delete " + city.get().getName() + " city because it has restaurants in it");
        }
    }

}
