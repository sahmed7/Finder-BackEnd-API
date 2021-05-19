package finderapi.demo.repository;

import finderapi.demo.model.MenuItem;
import finderapi.demo.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    MenuItem findByIdAndUserId(Long menuItemId, Long userId);

    List<MenuItem> findByRestaurantId(Long menuItemId);

    List<MenuItem> findByRestaurantIdAndUserId(Long menuItemId, Long userId);

    MenuItem findByNameAndUserIdAndIdIsNot(String menuItemName, Long userId, Long menuItemId);

    MenuItem findByNameAndUserId(String menuItemName, Long userId);

}
