package com.mvii3iv.sat.security;

import com.mvii3iv.sat.components.UserData.UserData;
import com.mvii3iv.sat.components.UserData.UserDataService;
import com.mvii3iv.sat.components.incomes.Incomes;
import com.mvii3iv.sat.components.incomes.IncomesRepository;
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
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {

    private LoginController loginController;
    private IncomesRepository incomesRepository;

    @Autowired
    public CustomAuthenticationSuccessHandler(LoginController loginController, IncomesRepository incomesRepository){
        this.loginController = loginController;
        this.incomesRepository = incomesRepository;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {



        boolean admin = false;
        String sessionId = request.getSession().getId();

        if ( !UserDataService.usersData.containsKey(sessionId) ) {
            UserData userData = new UserData(null, null, new ArrayList<Incomes>(), new User(), false);
            UserDataService.usersData.put(sessionId, userData);
        }

        ((UserData)UserDataService.usersData.get(sessionId)).setUser(new User(authentication.getName(), authentication.getCredentials().toString()));


        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);



        System.out.println(">User Granted");



        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(auth.getAuthority())){
                admin = true;
            }
        }

        if(admin){
            response.sendRedirect("/admin/");
        }else{
            List<Incomes> incomes = incomesRepository.findByEmisorRFC(authentication.getName());

            if(incomes.size() > 0)
                response.sendRedirect("/");
            else {
                loginController.extractData(request, response);
                response.sendRedirect("/");
            }
        }
    }
}