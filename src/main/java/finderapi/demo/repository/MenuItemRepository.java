package finderapi.demo.repository;

import finderapi.demo.model.MenuItem;
import finderapi.demo.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    MenuItem findByIdAndUserId(Long menuItemId, Long userId);

}
