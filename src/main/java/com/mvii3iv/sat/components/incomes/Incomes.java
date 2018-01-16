package com.mvii3iv.sat.components.incomes;

import com.mvii3iv.sat.components.UserData.UserData;
import com.mvii3iv.sat.components.UserData.UserDataService;
import com.mvii3iv.sat.components.bills.Bills;
import com.mvii3iv.sat.components.bills.BillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/incomes")
public class Incomes {

    private BillsService billsService;
    private UserDataService userDataService;

    @Autowired
    public Incomes(BillsService billsService){
        this.billsService = billsService;
        this.userDataService = userDataService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Bills> getIcomes(HttpServletRequest request){

        String sessionId = request.getSession().getId();
        UserData userData = (UserData)UserDataService.usersData.get(sessionId);

        return userData.getBills();
    }

}
