package finderapi.demo.repository;

import finderapi.demo.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findById(Long cityId);

    City findByIdAndUserId(Long categoryId, Long userId);

    City findByName(String cityName);
    // find city by userId and by cityName
    City findByUserIdAndName(Long userId, String cityName);

    List<City> findByUserId(Long userId);

}
