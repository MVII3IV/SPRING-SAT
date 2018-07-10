package com.mvii3iv.sat.components.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String profile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(-1);
        return "admin";
    }

}
