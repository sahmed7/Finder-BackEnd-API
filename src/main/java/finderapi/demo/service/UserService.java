package finderapi.demo.service;

import finderapi.demo.exception.InformationExistException;
import finderapi.demo.exception.InformationNotFoundException;
import finderapi.demo.model.User;
import finderapi.demo.model.request.LoginRequest;
import finderapi.demo.model.response.LoginResponse;
import finderapi.demo.repository.UserRepository;
import finderapi.demo.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtils jwtUtils;

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

    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmailAddress());
            final String JWT = jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        } catch (NullPointerException e) {
            throw new InformationNotFoundException("User with that email address " + loginRequest.getEmailAddress()
                    + " not found!");
        }
    }
}
