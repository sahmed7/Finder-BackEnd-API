package finderapi.demo.controller;

import finderapi.demo.model.City;
import finderapi.demo.model.Restaurant;
import finderapi.demo.service.CityService;
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
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService)
    {
        this.restaurantService = restaurantService;
    }

    // http://localhost:9092/api/cities/1/restaurants
    @PostMapping(path = "/cities/{cityId}/restaurants")
    public Restaurant createRestaurant(@PathVariable Long cityId, @RequestBody Restaurant restaurantObject){
        System.out.println("calling createRestaurant ==> ");
        return restaurantService.createRestaurant(cityId, restaurantObject);
    }

    // http://localhost:9092/api/cities/1/restaurants
    @GetMapping(path = "/cities/{cityId}/restaurants")
    public List<Restaurant> getRestaurants(@PathVariable Long cityId) {
        System.out.println("calling getRestaurants ==> ");
        return restaurantService.getRestaurants(cityId);
    }

    // http://localhost:9092/api/cities/1/restaurants/1
    @GetMapping(path = "/cities/{cityId}/restaurants/{restaurantId}")
    public Restaurant getSingleRestaurant(@PathVariable Long cityId, @PathVariable Long restaurantId) {
        System.out.println("calling getSingleRestaurant ==>");
        return restaurantService.getSingleRestaurant(cityId, restaurantId);
    }

    // http://localhost:9092/api/cities/1/restaurants/1
    @PutMapping(path = "/cities/{cityId}/restaurants/{restaurantId}")
    public Restaurant updateRestaurant(@PathVariable Long cityId, @PathVariable Long restaurantId, @RequestBody Restaurant restaurantObject) {
        System.out.println("calling updateRestaurant ==> ");
        return restaurantService.updateRestaurant(cityId, restaurantId, restaurantObject);
    }

    // http://localhost:9092/api/cities/1/restaurants/1
    @DeleteMapping(path = "/cities/{cityId}/restaurants/{restaurantId}")
    public ResponseEntity<HashMap> deleteRestaurant(@PathVariable Long cityId, @PathVariable Long restaurantId) {
        System.out.println("calling deleteRestaurant ==> ");
        restaurantService.deleteRestaurant(cityId, restaurantId);
        HashMap responseMessage = new HashMap();
        responseMessage.put("Status", "Restaurant with id: "+ restaurantId + " was successfully deleted!");
        return new ResponseEntity<HashMap>(responseMessage, HttpStatus.OK);
    }

}
