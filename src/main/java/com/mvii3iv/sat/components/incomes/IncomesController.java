package com.mvii3iv.sat.components.incomes;

import com.mvii3iv.sat.components.UserData.UserData;
import com.mvii3iv.sat.components.UserData.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/incomes")
public class IncomesController {

    private IncomesService incomesService;
    private IncomesRepository incomesRepository;
    private UserDataService userDataService;

    @Autowired
    public IncomesController(IncomesService incomesService, IncomesRepository incomesRepository){
        this.incomesService = incomesService;
        this.userDataService = userDataService;
        this.incomesRepository = incomesRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Incomes> getIcomes(@RequestParam String userRFC, HttpServletRequest request, Authentication authentication){
        //UserData userData = (UserData)UserDataService.usersData.get(userRFC);
        if(authentication.getName().toLowerCase().equals("admin"))
            return incomesRepository.findByEmisorRFC(userRFC);
        else
            return incomesRepository.findByEmisorRFC(authentication.getName());
    }

}
