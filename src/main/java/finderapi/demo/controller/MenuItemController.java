package finderapi.demo.controller;

import finderapi.demo.model.City;
import finderapi.demo.model.MenuItem;
import finderapi.demo.model.Restaurant;
import finderapi.demo.service.CityService;
import finderapi.demo.service.MenuItemService;
import finderapi.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class MenuItemController {

    private MenuItemService menuItemService;

    @Autowired
    public void setMenuItemService(MenuItemService menuItemService)
    {
        this.menuItemService = menuItemService;
    }

    // http://localhost:9092/api/cities/1/restaurants/1/menu
    @PostMapping(path = "/cities/{cityId}/restaurants/{restaurantId}/menu")
    public MenuItem createMenuItem(@PathVariable Long cityId, @PathVariable Long restaurantId, @RequestBody MenuItem menuItemObject){
        System.out.println("calling createMenuItem ==> ");
        return menuItemService.createMenuItem(cityId, restaurantId, menuItemObject);
    }
}
