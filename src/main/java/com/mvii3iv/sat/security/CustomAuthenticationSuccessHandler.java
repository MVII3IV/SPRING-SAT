package com.mvii3iv.sat.security;

import com.mvii3iv.sat.components.login.LoginController;
import com.mvii3iv.sat.components.login.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {

    private LoginController loginController;

    @Autowired
    public CustomAuthenticationSuccessHandler(LoginController loginController){
        this.loginController = loginController;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);

        boolean admin = false;

        System.out.println("AT onAuthenticationSuccess(...) function!");

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(auth.getAuthority())){
                admin = true;
            }

        }

        if(admin){
            response.sendRedirect("/admin");
        }else{
            loginController.enterLoginData(request, new User(authentication.getName() ,authentication.getCredentials().toString()), response);
            //response.sendRedirect("/access");
        }
    }
}