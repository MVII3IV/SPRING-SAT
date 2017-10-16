package com.mvii3iv.sat.controllers;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.mvii3iv.sat.models.SatLogin;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

@Controller
public class LoginController {

    /*
        RFC LULR860821MTA
        PAS goluna21
     */
    private static WebClient webClient;
    private boolean proxyEnabled;
    HtmlPage browser;


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public void login(HttpServletResponse response)
            throws IOException {

        init();

        browser = null;

        try {
            browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/");

            System.out.println(browser.getTitleText());

            HtmlImage image = browser.<HtmlImage>getFirstByXPath("//*[@id='IDPLogin']/div[3]/label/img");
            File imageFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\captcha.jpg");

            image.saveAs(imageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }


        String captcha = "";
        byte[] bytes = null;
        String loginTemplate = "";

        try {
            loginTemplate = FileUtils.readFileToString(new File(this.getClass().getResource("/templates/login.html").toURI()), "UTF-8");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        Path path = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\captcha.jpg");
        bytes = Files.readAllBytes(path);


        String encodedImage = Base64.encode(bytes);
        loginTemplate = loginTemplate.replace("$captcha", encodedImage);


        response.getOutputStream().write(loginTemplate.getBytes());

    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String enterLoginData(@ModelAttribute SatLogin satLogin) {

        try {

        HtmlForm loginForm = browser.getFormByName("IDPLogin");
        HtmlInput rfc = loginForm.getInputByName("Ecom_User_ID");
        HtmlPasswordInput pass = loginForm.getInputByName("Ecom_Password");
        HtmlInput captcha = loginForm.getInputByName("jcaptcha");
        HtmlSubmitInput sendButton = loginForm.getInputByName("submit");

        rfc.setValueAttribute(satLogin.getRfc());
        pass.setValueAttribute(satLogin.getPass());
        captcha.setValueAttribute(satLogin.getCaptcha());


        browser = sendButton.click();
            webClient.waitForBackgroundJavaScript(5000);
        browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/");
        browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/ConsultaEmisor.aspx");


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "result";
    }


    private void init() {


        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);



        if (proxyEnabled) {
            webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER, "proxy.autozone.com", 8080);
            DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
            credentialsProvider.addCredentials("edomingu", "mULLEN20855-");
        } else {
            webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
        }

        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());



        //CookieManager cookieMan = new CookieManager();
        //cookieMan = browser.getCookieManager();
        //cookieMan.setCookiesEnabled(true);

    }

}
