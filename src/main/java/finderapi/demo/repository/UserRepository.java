package finderapi.demo.repository;

import finderapi.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAddress(String emailAddress);
    boolean existsByEmailAddress(String emailAddress);
    boolean existsByUserName(String userName);
}
