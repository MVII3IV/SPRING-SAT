package com.mvii3iv.sat.components.bills;

import com.mvii3iv.sat.components.UserData.UserData;
import com.mvii3iv.sat.components.UserData.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class BillsController {

    private BillsService billsService;
    private UserDataService userDataService;

    @Autowired
    public BillsController(BillsService billsService){
        this.billsService = billsService;
        this.userDataService = userDataService;
    }

    @RequestMapping(value = "/bills", method = RequestMethod.GET)
    public List<Bills> getBills(HttpServletRequest request){

        String sessionId = request.getSession().getId();
        UserData userData = (UserData)UserDataService.usersData.get(sessionId);

        return userData.getBills();
    }

}
