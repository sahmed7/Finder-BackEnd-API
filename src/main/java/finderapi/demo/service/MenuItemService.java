package finderapi.demo.service;

import finderapi.demo.exception.InformationExistException;
import finderapi.demo.exception.InformationNotFoundException;
import finderapi.demo.model.City;
import finderapi.demo.model.MenuItem;
import finderapi.demo.model.Restaurant;
import finderapi.demo.model.User;
import finderapi.demo.repository.CityRepository;
import finderapi.demo.repository.MenuItemRepository;
import finderapi.demo.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    private CityRepository cityRepository;
    private RestaurantRepository restaurantRepository;
    private MenuItemRepository menuItemRepository;

    public UtilityService utility = new UtilityService();

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public void setRestaurantRepository(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    @Autowired
    public void setMenuItemRepository(MenuItemRepository menuItemRepository){
        this.menuItemRepository = menuItemRepository;
    }

    // Create a menuItem for a single restaurant
    public MenuItem createMenuItem(Long cityId, Long restaurantId, MenuItem menuItemObject) {
        System.out.println("service calling createMenuItem ==>");
        User user = utility.getAuthenticatedUser();
        City city = cityRepository.findByIdAndUserId(cityId, user.getId());

        if (city == null) {
            throw new InformationNotFoundException(
                    "City with id " + cityId + " does not exist");
        }
        Restaurant restaurant = restaurantRepository.findByIdAndUserId(restaurantId, user.getId());
        if (restaurant == null) {
            throw new InformationExistException("Restaurant with id " + restaurant.getId() + " already exists");
        }
        MenuItem menuItem = menuItemRepository.findByIdAndUserId(menuItemObject.getId(), user.getId());
        if (menuItem != null){
            throw new InformationExistException("MenuItem with id " + menuItem.getId() + " already exists");
        }
        menuItemObject.setUser(user);
        menuItemObject.setRestaurant(restaurant);
        menuItemObject.setName(menuItemObject.getName());
        menuItemObject.setDescription(menuItemObject.getDescription());
        return menuItemRepository.save(menuItemObject);
    }

    // Get all menuItems of a single restaurant
    public List<MenuItem> getMenuItems(Long cityId, Long restaurantId) {
        System.out.println("service calling getMenuItems ==>");
        User user = utility.getAuthenticatedUser();
        City city = cityRepository.findByIdAndUserId(cityId, user.getId());
        if (city == null) {
            throw new InformationNotFoundException("City with id " + cityId + " " + " does not exist");
        }
        Restaurant restaurant = restaurantRepository.findByIdAndUserId(restaurantId, user.getId());
        if (restaurant == null) {
            throw new InformationExistException("Restaurant with id " + restaurant.getId() + " already exists");
        }
        return restaurant.getMenuItemList();
    }

    // Get single MenuItem of a single restaurant
    public MenuItem getSingleMenuItem(Long cityId, Long restaurantId, Long menuItemId) {
        System.out.println("service calling getSingleMenuItem ==>");
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
        Optional<MenuItem> menuItem = menuItemRepository.findByRestaurantId(
                restaurantId).stream().filter(p -> p.getId().equals(menuItemId)).findFirst();
        if (!menuItem.isPresent()) {
            throw new InformationNotFoundException("MenuItem with id " + menuItemId + " does not exist");
        }
        return menuItem.get();
    }

    // Update a single menuItem
    public MenuItem updateMenuItem(Long cityId, Long restaurantId, Long menuItemId, MenuItem menuItemObject) {
        System.out.println("service calling updateMenuItem ==>");
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
        Optional<MenuItem> menuItem = menuItemRepository.findByRestaurantId(
                restaurantId).stream().filter(p -> p.getId().equals(menuItemId)).findFirst();
        if (!menuItem.isPresent()) {
            throw new InformationNotFoundException("Menu Item with id " + menuItemId + " does not exist");
        }
        MenuItem oldMenuItem = menuItemRepository.findByNameAndUserIdAndIdIsNot(
                menuItemObject.getName(), user.getId(), menuItemId);
        if (oldMenuItem != null) {
            throw new InformationExistException("Menu Item with name " + oldMenuItem.getName() + " already exists");
        }
        menuItem.get().setName(menuItemObject.getName());
        menuItem.get().setDescription(menuItemObject.getDescription());
        return menuItemRepository.save(menuItem.get());
    }
}
