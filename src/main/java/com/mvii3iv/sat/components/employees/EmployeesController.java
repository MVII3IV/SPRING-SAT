package com.mvii3iv.sat.components.employees;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvii3iv.sat.components.assets.HostValidator;
import com.mvii3iv.sat.components.bills.Bills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

@RestController
@RequestMapping(value="/employees")
public class EmployeesController {

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    private Environment env;

    @Autowired
    private HostValidator hostValidator;

    @Autowired
    public EmployeesController(EmployeesRepository employeesRepository, HostValidator hostValidator) {
        this.employeesRepository = employeesRepository;
        this.hostValidator = hostValidator;
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Employees> getEmployees(){

        String HOST_NAME = env.getProperty("SERVER_URL");

        HttpURLConnection con = null;
        URL url = null;
        List<Employees> employees = null;
        Proxy proxy = null;

        try {
            url = new URL(HOST_NAME  + "/employees");

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

            employees = new ObjectMapper().readValue(content.toString(), new ObjectMapper().getTypeFactory().constructCollectionType(List.class, Employees.class));

            in.close();
            con.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return employees;
    }



    @RequestMapping(method = RequestMethod.POST)
    public Employees saveEmployee(@RequestBody Employees employees){
        employeesRepository.save(employees);
        return employees;
    }



    @RequestMapping(method = RequestMethod.PUT)
    public Employees updateEmployee(@RequestBody Employees employees){
        employeesRepository.save(employees);
        return employees;
    }

}
