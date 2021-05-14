package finderapi.demo.controller;

import finderapi.demo.model.User;
import finderapi.demo.model.request.LoginRequest;
import finderapi.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/users/register")
    public User createUser(@RequestBody User userObject){return this.userService.createUser(userObject);}

    @PostMapping(path = "/users/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){return this.userService.loginUser(loginRequest);}


}
