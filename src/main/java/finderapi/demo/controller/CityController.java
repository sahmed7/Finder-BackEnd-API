package finderapi.demo.controller;

import finderapi.demo.model.City;
import finderapi.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World!";
    }

    @GetMapping("/cities")
    public List<City> getCities() {
        System.out.println("calling getCities ===>");
        return cityService.getCities();
    }

    @GetMapping("/cities/{cityId}")
    public City getCity(@PathVariable Long cityId){
        return cityService.getCity(cityId);
    }

    @PostMapping("/cities")
    public City createCity(@RequestBody City cityObject){
        return cityService.createCity(cityObject);
    }
}
