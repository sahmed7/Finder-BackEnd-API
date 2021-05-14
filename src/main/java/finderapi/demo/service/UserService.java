package finderapi.demo.service;

import finderapi.demo.model.User;
import finderapi.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByEmailAddress(String emailAddress){
        return userRepository.findByEmailAddress(emailAddress);
    }
}
