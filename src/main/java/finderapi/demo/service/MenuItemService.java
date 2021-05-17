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
}
