package finderapi.demo.controller;

import finderapi.demo.model.City;
import finderapi.demo.service.CityService;
import finderapi.demo.service.UtilityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CityController {

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

    @PostMapping("/cities")
    public City createCity(@RequestBody City cityObject){
        System.out.println("calling createCity ===>");
        return cityService.createCity(cityObject);
    }
}
