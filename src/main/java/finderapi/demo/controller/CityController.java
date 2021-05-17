package finderapi.demo.controller;

import finderapi.demo.model.City;
import finderapi.demo.model.Restaurant;
import finderapi.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class CityController {

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService)
    {
        this.cityService = cityService;
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World!";
    }

    //http://localhost:9092/api/cities
    @PostMapping("/cities")
    public City createCity(@RequestBody City cityObject){
        System.out.println("calling createCity ===>");
        return cityService.createCity(cityObject);
    }

    //http://localhost:9092/api/cities
    @GetMapping("/cities")
    public List<City> getCities() {
        System.out.println("calling getCities ===>");
        return cityService.getCities();
    }

    //http://localhost:9092/api/cities/1
    @GetMapping(path = "/cities/{cityId}")
    public Optional<City> getSingleCity(@PathVariable Long cityId) {
        System.out.println("calling getSingleCity ==> ");
        return cityService.getSingleCity(cityId);
    }

    //http://localhost:9092/api/cities/1
    @PutMapping(path = "/cities/{cityId}")
    public City updateCity(@PathVariable Long cityId, @RequestBody City cityObject){
        System.out.println("calling updateCity ==> ");
        return cityService.updateCity(cityId, cityObject);
    }

    //http://localhost:9092/api/cities/1
    @DeleteMapping(path = "/cities/{cityId}")  //user is entering/passing the id anyway, use it
    public ResponseEntity<HashMap> deleteCity(@PathVariable Long cityId) {
        System.out.println("calling deleteCity ==>");
        String status = cityService.deleteCity(cityId);
        HashMap message = new HashMap();
        message.put("status", status);
        return new ResponseEntity(message, HttpStatus.OK);
    }

}
