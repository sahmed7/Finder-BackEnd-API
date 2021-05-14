package finderapi.demo.service;

import finderapi.demo.exception.InformationExistException;
import finderapi.demo.exception.InformationNotFoundException;
import finderapi.demo.model.City;
import finderapi.demo.model.Restaurant;
import finderapi.demo.model.User;
import finderapi.demo.repository.CityRepository;
import finderapi.demo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class CityService {
    
    private CityRepository cityRepository;

    public UtilityService utility = new UtilityService();
    
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
        User user = utility.getAuthenticatedUser();
        //City city = cityRepository.findByUserIdAndName(user.getId(), cityObject.getName());
        if (cityRepository.findByUserIdAndName(user.getId(), cityObject.getName())!= null){
            throw new InformationExistException("city exists!");
        } else {
            cityObject.setUser(user);
            return cityRepository.save(cityObject);
        }
//        System.out.println("The city is: ==============> " + city);
//        if(city != null) {
//            throw new InformationExistException("city with name " + cityObject.getName() + " already exists");
//        } else {
//            System.out.println("The city object is: ==============> " + cityObject);
//        }
    }
}
