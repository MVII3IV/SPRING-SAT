package com.mvii3iv.sat.components.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public User getUser(Authentication authentication){
        return new User(authentication.getName(), "Fake Name", "");
    }

}
