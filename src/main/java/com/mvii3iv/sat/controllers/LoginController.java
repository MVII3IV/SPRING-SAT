package com.mvii3iv.sat.controllers;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.dv.util.Base64;
import org.omg.CORBA_2_3.portable.InputStream;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Controller
public class LoginController {

    private static WebClient webClient;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getLogin() {
        login();
        return "login";
    }

    private void satLogin() {
        HtmlPage page = null;
        try {
            page = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/");


            System.out.println(page.getTitleText());


            HtmlImage image = page.<HtmlImage>getFirstByXPath("//*[@id='IDPLogin']/div[3]/label/img");
            //File imageFile = new File("C:\\Users\\edomingu\\Desktop\\SATDemo\\src\\main\\resources\\static\\img\\captcha.jpg");
            File imageFile = new File("C:\\captcha.jpg");

            image.saveAs(imageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @ResponseBody
    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public void getLastReport(HttpServletResponse response)
            throws IOException {

        init();

        HtmlPage page = null;
        try {
            page = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/");


            System.out.println(page.getTitleText());


            HtmlImage image = page.<HtmlImage>getFirstByXPath("//*[@id='IDPLogin']/div[3]/label/img");
            File imageFile = new File("C:\\Users\\edomingu\\Desktop\\SATDemo\\src\\main\\resources\\static\\img\\captcha.jpg");



            image.saveAs(imageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }



        String captcha = "";
        byte[] bytes = null;
        String loginTemplate = "";

        try {
            loginTemplate = FileUtils.readFileToString(   new File(this.getClass().getResource("/templates/login.html").toURI()), "UTF-8");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }



        //try (java.io.InputStream templateStream = getClass().getResourceAsStream("/img/captcha.jpg")) {
        Path path = Paths.get("C:\\Users\\edomingu\\Desktop\\SATDemo\\src\\main\\resources\\static\\img\\captcha.jpg");
        bytes = Files.readAllBytes(path);



        String encodedImage = Base64.encode(bytes);
        loginTemplate = loginTemplate.replace("$captcha",encodedImage);


        response.getOutputStream().write(loginTemplate.getBytes());

        //response.getOutputStream().write("The report is not available".getBytes());

    }


    private void login() {
        init();
        satLogin();
    }

    private void init() {
        webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER, "proxy.autozone.com", 8080);

        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);

        DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
        credentialsProvider.addCredentials("edomingu", "mULLEN20855-");
    }

}
