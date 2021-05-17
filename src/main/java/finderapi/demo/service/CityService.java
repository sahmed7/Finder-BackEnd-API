package finderapi.demo.service;

import finderapi.demo.exception.InformationExistException;
import finderapi.demo.exception.InformationNotFoundException;
import finderapi.demo.model.City;
import finderapi.demo.model.Restaurant;
import finderapi.demo.model.User;
import finderapi.demo.repository.CityRepository;
import finderapi.demo.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // Update a single city
    public City updateCity(Long cityId, City cityObject) {
        System.out.println("service calling updateCity ==>");
        User user = utility.getAuthenticatedUser();
        City city = cityRepository.findByIdAndUserId(cityId, user.getId());
        if (city == null) {
            throw new InformationNotFoundException("city with id " + cityId + " not found");
        } else {
            city.setName(cityObject.getName());
            return cityRepository.save(city);
        }
    }

    // Delete a single city
    public String deleteCity(Long cityId) {
        System.out.println("service calling deleteCity ==>");
        User user = utility.getAuthenticatedUser();
        City city = cityRepository.findByIdAndUserId(cityId, user.getId());
        if (city == null) {
            throw new InformationNotFoundException("City with id " + cityId + " not found");
        } else {
            cityRepository.deleteById(cityId);
            return "City with id " + cityId + " has been successfully deleted";
        }
    }

    // Create a restaurant for single city
    public Restaurant createRestaurant(Long cityId, Restaurant restaurantObject) {
        System.out.println("service calling createRestaurant ==>");
        User user = utility.getAuthenticatedUser();
        City city = cityRepository.findByIdAndUserId(cityId, user.getId());
        if (city == null) {
            throw new InformationNotFoundException(
                    "City with id " + cityId + " does not exist");
        }
        Restaurant restaurant = restaurantRepository.findByNameAndUserId(restaurantObject.getName(), user.getId());
        if (restaurant != null) {
            throw new InformationExistException("Restaurant with name " + restaurant.getName() + " already exists");
        }
        restaurantObject.setUser(user);
        restaurantObject.setCity(city);
        restaurantObject.setName(restaurantObject.getName());
        restaurantObject.setCategory(restaurantObject.getCategory());
        return restaurantRepository.save(restaurantObject);
    }

    // Get all restaurants of a single city
    public List<Restaurant> getRestaurants(Long cityId) {
        System.out.println("service calling getRestaurants ==>");
        User user = utility.getAuthenticatedUser();
        City city = cityRepository.findByIdAndUserId(cityId, user.getId());
        if (city == null) {
            throw new InformationNotFoundException("City with id " + cityId + " " + " does not exist");
        }
        return city.getRestaurantList();
    }

    // Get single restaurant of a single city
    public Restaurant getSingleRestaurant(Long cityId, Long restaurantId) {
        System.out.println("service calling getSingleRestaurant ==>");
        User user = utility.getAuthenticatedUser();
        City city = cityRepository.findByIdAndUserId(cityId, user.getId());
        if (city == null) {
            throw new InformationNotFoundException("City with id " + cityId + " does not exist");
        }
        Optional<Restaurant> restaurant = restaurantRepository.findByCityId(
                cityId).stream().filter(p -> p.getId().equals(restaurantId)).findFirst();
        if (!restaurant.isPresent()) {
            throw new InformationNotFoundException("Restaurant with id " + restaurantId + "does not exist");
        }
        return restaurant.get();
    }

    // Update single restaurant
    public Restaurant updateRestaurant(Long cityId, Long restaurantId, Restaurant restaurantObject) {
        System.out.println("service calling updateRestaurant ==>");
        User user = utility.getAuthenticatedUser();
        City city = cityRepository.findByIdAndUserId(cityId, user.getId());
        if (city == null) {
            throw new InformationNotFoundException("City with id " + cityId + " does not exist");
        }
        Optional<Restaurant> restaurant = restaurantRepository.findByCityId(
                cityId).stream().filter(p -> p.getId().equals(restaurantId)).findFirst();
        if (!restaurant.isPresent()) {
            throw new InformationNotFoundException("Restaurant with id " + restaurantId + " does not exist");
        }
        Restaurant oldRestaurant = restaurantRepository.findByNameAndUserIdAndIdIsNot(
                restaurantObject.getName(), user.getId(), restaurantId);
        if (oldRestaurant != null) {
            throw new InformationExistException("Restaurant with name " + oldRestaurant.getName() + " already exists");
        }
        restaurant.get().setName(restaurantObject.getName());
        restaurant.get().setCategory(restaurantObject.getCategory());
        return restaurantRepository.save(restaurant.get());
    }
}
