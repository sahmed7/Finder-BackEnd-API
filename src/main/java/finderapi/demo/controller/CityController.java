package finderapi.demo.controller;

import finderapi.demo.model.City;
import finderapi.demo.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class CityController {

    private CityService cityService;

    @GetMapping("/helloworld")
    public String helloWorld(){
        return "Hello World!";
    }

}
