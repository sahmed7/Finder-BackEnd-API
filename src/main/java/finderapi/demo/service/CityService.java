package finderapi.demo.service;

import finderapi.demo.exception.InformationExistException;
import finderapi.demo.exception.InformationNotFoundException;
import finderapi.demo.model.City;
import finderapi.demo.repository.CityRepository;
import finderapi.demo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class CityService {
    
    private CityRepository cityRepository;
    
    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
    
    public List<City> getCities() {
        System.out.println("service calling getCities ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        System.out.println(userDetails.getUser().getId());
        List<City> city = cityRepository.findByUserId(userDetails.getUser().getId());
        if (city.isEmpty()) {
            throw new InformationNotFoundException("no cities found for user id " + userDetails.getUser().getId());
        } else {
            return city;
        }
    }

    public City createCity(City cityObject) {
        System.out.println("service calling createCategory ==>");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        City city = cityRepository.findByUserIdAndName(userDetails.getUser().getId(), cityObject.getName());

        System.out.println("The city object is: ==============> " + city);
        if(city != null) {
            throw new InformationExistException("city with name " + cityObject.getName() + " already exists");
        } else {
            System.out.println("The city object is: ==============> " + cityObject);
            cityObject.setUser(userDetails.getUser());
            return cityRepository.save(cityObject);
        }
    }
}
