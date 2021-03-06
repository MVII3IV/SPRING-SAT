package com.mvii3iv.sat.security;

import com.mvii3iv.sat.components.UserData.UserData;
import com.mvii3iv.sat.components.UserData.UserDataService;
import com.mvii3iv.sat.components.bills.Bills;
import com.mvii3iv.sat.components.bills.BillsRepository;
import com.mvii3iv.sat.components.login.LoginController;
import com.mvii3iv.sat.components.user.UserRepository;
import com.mvii3iv.sat.components.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    private BillsRepository billsRepository;
    private UserRepository userRepository;

    @Autowired
    public CustomAuthenticationSuccessHandler(LoginController loginController, BillsRepository billsRepository, UserRepository userRepository){
        this.loginController = loginController;
        this.billsRepository = billsRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        boolean admin = false;
        String sessionId = request.getSession().getId();
        sessionId = authentication.getName();
        if ( !UserDataService.usersData.containsKey(sessionId) ) {
            UserData userData = new UserData(null, null, new ArrayList<Bills>(), new Users(), false);
            UserDataService.usersData.put(sessionId, userData);
        }

        Users user = userRepository.findById(authentication.getName());
        ((UserData)UserDataService.usersData.get(sessionId)).setUser(user);


        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println(">Users Granted");

        if (user.getRole().equals("ROLE_ADMIN"))
            admin = true;

        if(admin){
            response.sendRedirect("/admin/");
        }else{
            List<Bills> incomes = billsRepository.findByEmisorRFC(authentication.getName());

            //remove these two lines
            //loginController.extractData(sessionId, response);
            //response.sendRedirect("/");


            if(incomes.size() > 0)
                response.sendRedirect("/");
            else {
                loginController.extractData(sessionId, response);
                response.sendRedirect("/");
            }
        }
    }
}