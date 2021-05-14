package finderapi.demo.service;

import finderapi.demo.exception.InformationExistException;
import finderapi.demo.model.User;
import finderapi.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserByEmailAddress(String emailAddress){
        return userRepository.findByEmailAddress(emailAddress);
    }

    public User createUser(User userObject){
        if(!userRepository.existsByEmailAddress(userObject.getEmailAddress())){
            if ((userRepository.existsByUserName(userObject.getUserName())))
                throw new InformationExistException("User with Username '" + userObject.getUserName() +
                        "' already exists");
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("User with email address: '" + userObject.getEmailAddress() +
                    "' already exists");
        }
    }


}
