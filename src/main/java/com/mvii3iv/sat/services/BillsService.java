package com.mvii3iv.sat.services;


import com.mvii3iv.sat.models.Bills;
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
