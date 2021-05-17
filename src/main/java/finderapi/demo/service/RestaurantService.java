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
public class RestaurantService {

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


    // Create a restaurant for single city
    public Restaurant createRestaurant(Long cityId, Restaurant restaurantObject) {
        System.out.println("service calling createRestaurant ==>");
        User user = utility.getAuthenticatedUser();
        City city = cityRepository.findByIdAndUserId(cityId, user.getId());
        if (city == null) {
            throw new InformationNotFoundException(
                    "City with id " + cityId + " does not exist");
        }
        Restaurant restaurant = restaurantRepository.findByAddressAndUserId(restaurantObject.getAddress(), user.getId());
        if (restaurant != null) {
            throw new InformationExistException("Restaurant with address " + restaurant.getAddress() + " already exists");
        }
        restaurantObject.setUser(user);
        restaurantObject.setCity(city);
        restaurantObject.setName(restaurantObject.getName());
        restaurantObject.setAddress(restaurantObject.getAddress());
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
            throw new InformationNotFoundException("Restaurant with id " + restaurantId + " does not exist");
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
        Restaurant oldRestaurant = restaurantRepository.findByAddressAndUserIdAndIdIsNot(
                restaurantObject.getAddress(), user.getId(), restaurantId);
        if (oldRestaurant != null) {
            throw new InformationExistException("Restaurant with address " + oldRestaurant.getAddress() + " already exists");
        }
        restaurant.get().setName(restaurantObject.getName());
        restaurant.get().setAddress(restaurantObject.getAddress());
        restaurant.get().setCategory(restaurantObject.getCategory());
        return restaurantRepository.save(restaurant.get());
    }

    // Delete a single Restaurant
    public void deleteRestaurant(Long cityId, Long restaurantId) {
        System.out.println("service calling deleteCategoryRecipe ==>");
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
        restaurantRepository.deleteById(restaurant.get().getId());
    }

}
