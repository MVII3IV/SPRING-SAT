package com.mvii3iv.sat.components.bills;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvii3iv.sat.components.anticaptcha.models.AntiCaptchaCreatedTaskResponse;
import com.mvii3iv.sat.components.user.UserRepository;
import com.mvii3iv.sat.components.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping(value = "/bills")
public class BillsController {

    private BillsService billsService;
    private BillsRepository billsRepository;
    private UserRepository userRepository;

    @Autowired
    public BillsController(BillsService billsService, BillsRepository billsRepository, UserRepository userRepository){
        this.billsService = billsService;
        this.billsRepository = billsRepository;
        this.userRepository = userRepository;
    }

    /*
    @RequestMapping(method = RequestMethod.GET)
    public List<Bills> getIcomes(@RequestParam String userRFC, HttpServletRequest request, Authentication authentication){
        Users user = userRepository.findById(authentication.getName());
        if(user.getRole().equals("ROLE_ADMIN"))
            return billsRepository.findByEmisorRFC(userRFC);
        else
            return billsRepository.findByEmisorRFC(authentication.getName());
    }*/

    @RequestMapping(method = RequestMethod.GET)
    public List<Bills> getIcomes(@RequestParam String rfc, @RequestParam String pass){

        URL url = null;
        List<Bills> bills = null;
        try {

            url = new URL("http://104.248.23.45:8080/crawler/extract/data?rfc=" + rfc + "&pass=" + pass);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(90000);
            con.setReadTimeout(90000);

            BufferedReader in = new BufferedReader(      new InputStreamReader(con.getInputStream())     );
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            bills = new ObjectMapper().readValue(content.toString(), new ObjectMapper().getTypeFactory().constructCollectionType(List.class, Bills.class));

            in.close();
            con.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return bills;
    }

}
