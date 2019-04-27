package com.mvii3iv.sat.components.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Users> getUsers(Authentication authentication){
        return userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Users getUser(@RequestParam String id, HttpServletRequest request, Authentication authentication){

        if(authentication.getName().toLowerCase().equals("admin")){
            if(id.equals("mock"))
                return new Users("mock-user","mock","mock","ROLE_CUSTOMER");
            else
                return userRepository.findById(id);
        }
        else
            return userRepository.findById(authentication.getName());
    }

}
