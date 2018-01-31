package com.mvii3iv.sat.components.incomes;

import com.mvii3iv.sat.components.UserData.UserData;
import com.mvii3iv.sat.components.UserData.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/incomes")
public class IncomesController {

    private IncomesService incomesService;
    private UserDataService userDataService;

    @Autowired
    public IncomesController(IncomesService incomesService){
        this.incomesService = incomesService;
        this.userDataService = userDataService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Incomes> getIcomes(HttpServletRequest request){

        String sessionId = request.getSession().getId();
        UserData userData = (UserData)UserDataService.usersData.get(sessionId);

        return userData.getBills();
    }

}
