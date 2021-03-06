package finderapi.demo.service;

import finderapi.demo.exception.InformationForbiddenException;
import finderapi.demo.model.User;
import finderapi.demo.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class UtilityService {
    // Returns AUTHENTICATED through JWT Token User
    public User getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
            throw new InformationForbiddenException("Forbidden");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
}
