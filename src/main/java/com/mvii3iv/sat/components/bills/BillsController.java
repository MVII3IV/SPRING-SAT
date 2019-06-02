package com.mvii3iv.sat.components.bills;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvii3iv.sat.components.anticaptcha.models.AntiCaptchaCreatedTaskResponse;
import com.mvii3iv.sat.components.assets.HostValidator;
import com.mvii3iv.sat.components.user.UserRepository;
import com.mvii3iv.sat.components.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

@RestController
@RequestMapping(value = "/bills")
public class BillsController {

    private BillsService billsService;
    private BillsRepository billsRepository;
    private UserRepository userRepository;

    @Autowired
    private Environment env;

    @Autowired
    private HostValidator hostValidator;

    @Autowired
    public BillsController(BillsService billsService, BillsRepository billsRepository, UserRepository userRepository, HostValidator hostValidator){
        this.billsService = billsService;
        this.billsRepository = billsRepository;
        this.userRepository = userRepository;
        this.hostValidator = hostValidator;
    }

    @RequestMapping(value="/external", method = RequestMethod.GET)
    public List<Bills> getIcomesExternal(@RequestParam String rfc, Authentication authentication){
        Users user = userRepository.findById(authentication.getName());
        return getIcomes(user.getId(), user.getPass());
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Bills> getIcomes(@RequestParam String rfc, @RequestParam String pass){

        HttpURLConnection con = null;
        URL url = null;
        List<Bills> bills = null;
        Proxy proxy = null;

        try {
            String connectionString = env.getProperty("SERVER_URL") + "/bills?rfc=" + rfc + "&pass=" + pass;
            url = new URL(connectionString);

            if (hostValidator.isProxyRequired()) {

                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(env.getProperty("PROXY_SERVER"), Integer.parseInt(env.getProperty("PROXY_PORT")) ));

                Authenticator authenticator = new Authenticator() {

                    public PasswordAuthentication getPasswordAuthentication() {
                        return (new PasswordAuthentication(env.getProperty("PROXY_USER"),
                                env.getProperty("PROXY_PASS").toCharArray()));
                    }
                };
                Authenticator.setDefault(authenticator);
                con = (HttpURLConnection) url.openConnection(proxy);
            }

            if (!hostValidator.isProxyRequired())
            con = (HttpURLConnection) url.openConnection();

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


    /*
    @RequestMapping(method = RequestMethod.GET)
    public List<Bills> getIcomes(@RequestParam String userRFC, HttpServletRequest request, Authentication authentication){
        Users user = userRepository.findById(authentication.getName());
        if(user.getRole().equals("ROLE_ADMIN"))
            return billsRepository.findByEmisorRFC(userRFC);
        else
            return billsRepository.findByEmisorRFC(authentication.getName());
    }*/
