package com.mvii3iv.sat.controllers;

import com.mvii3iv.sat.models.Bills;
import com.mvii3iv.sat.services.BillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BillsController {

    private BillsService billsService;

    @Autowired
    public BillsController(BillsService billsService){
        this.billsService = billsService;
    }

    @RequestMapping(value = "/bills", method = RequestMethod.GET)
    public List<Bills> getBills(){
        return billsService.getBills();
    }

}
