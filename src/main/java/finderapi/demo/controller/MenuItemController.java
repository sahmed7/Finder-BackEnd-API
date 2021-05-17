package finderapi.demo.controller;

import finderapi.demo.model.MenuItem;
import finderapi.demo.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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

    // http://localhost:9092/api/cities/1/restaurants/1/menu
    @GetMapping(path = "/cities/{cityId}/restaurants/{restaurantId}/menu")
    public List<MenuItem> getMenuItems(@PathVariable Long cityId, @PathVariable Long restaurantId) {
        System.out.println("calling getMenuItems ==> ");
        return menuItemService.getMenuItems(cityId, restaurantId);
    }

    // http://localhost:9092/api/cities/1/restaurants/1/menu/1
    @GetMapping(path = "/cities/{cityId}/restaurants/{restaurantId}/menu/{menuItemId}")
    public MenuItem getSingleMenuItem(@PathVariable Long cityId, @PathVariable Long restaurantId, @PathVariable Long menuItemId) {
        System.out.println("calling getSingleMenuItem ==>");
        return menuItemService.getSingleMenuItem(cityId, restaurantId, menuItemId);
    }

    // http://localhost:9092/api/cities/1/restaurants/1/menu/1
    @PutMapping(path = "/cities/{cityId}/restaurants/{restaurantId}/menu/{menuItemId}")
    public MenuItem updateMenuItem(@PathVariable Long cityId, @PathVariable Long restaurantId, @PathVariable Long menuItemId, @RequestBody MenuItem menuItemObject) {
        System.out.println("calling updateMenuItem ==> ");
        return menuItemService.updateMenuItem(cityId, restaurantId, menuItemId, menuItemObject);
    }

    // http://localhost:9092/api/cities/1/restaurants/1/menu/1
    @DeleteMapping(path = "/cities/{cityId}/restaurants/{restaurantId}/menu/{menuItemId}")
    public ResponseEntity<HashMap> deleteMenuItem(@PathVariable Long cityId, @PathVariable Long restaurantId, @PathVariable Long menuItemId) {
        System.out.println("calling deleteMenuItem ==> ");
        menuItemService.deleteMenuItem(cityId, restaurantId, menuItemId);
        HashMap responseMessage = new HashMap();
        responseMessage.put("Status", "Menu Item with id: "+ menuItemId + " was successfully deleted!");
        return new ResponseEntity<HashMap>(responseMessage, HttpStatus.OK);
    }
}
