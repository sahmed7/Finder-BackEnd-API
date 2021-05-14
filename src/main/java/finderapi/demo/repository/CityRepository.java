package finderapi.demo.repository;

import finderapi.demo.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findByIdAndUserId(Long cityId, Long userId);

    // find city by userId and by cityName
    City findByUserIdAndName(Long userId, String cityName);

    List<City> findByUserId(Long userId);

}
