package finderapi.demo.repository;

import finderapi.demo.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

    Restaurant findByName(String restaurantName);

    Restaurant findByNameAndUserIdAndIdIsNot(String restaurantName,Long userId, Long recipeId);

    List<Restaurant> findByCityId(Long restaurantId);

    List<Restaurant> findByCityIdAndUserId(Long restaurantId, Long userId);

    Restaurant findByNameAndUserId(String restaurantName, Long userId);

    Restaurant findByIdAndUserId(Long restaurantId, Long userId);

    Restaurant findByAddressAndUserId(String restaurantAddress, Long userId);

    Restaurant findByAddressAndUserIdAndIdIsNot(String restaurantAddress,Long userId, Long restaurantId);

}
