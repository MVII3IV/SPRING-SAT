package com.mvii3iv.sat.components.bills;


import com.mvii3iv.sat.components.UserData.UserDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillsService {


    public List getBills() {
        return bills;
    }

    public void setBills(List bills) {
        this.bills = bills;
    }

    private List bills = new ArrayList<Bills>();
}
