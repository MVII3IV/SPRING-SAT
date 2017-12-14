package com.mvii3iv.sat.components.login;


import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.mvii3iv.sat.components.bills.Bills;
import com.mvii3iv.sat.components.anticaptcha.AntiCaptchaService;
import com.mvii3iv.sat.components.bills.BillsService;
import com.mvii3iv.sat.components.captcha.CaptchaService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

@Controller
public class LoginController {

    /*
        RFC LULR860821MTA
        PAS goluna21

        RFC AACD9001011X8
        PAS Luxdom03
     */
    private static WebClient webClient;
    private boolean proxyEnabled = true;
    private HtmlPage browser = null;
    private String loginMessage = "";
    List bills = new ArrayList<Bills>();


    private static final String LOGIN_URL = "https://portalcfdi.facturaelectronica.sat.gob.mx/";
    private String HOST_NAME;
    private String HOST_PORT;
    private String HOST_SCHEME = "http://";


    /**
     * Services
     */
    private BillsService billsService;
    private CaptchaService captchaService;
    private Environment environment;

    @Autowired
    public LoginController(BillsService billsService, CaptchaService captchaService, Environment environment) {
        this.billsService = billsService;
        this.captchaService = captchaService;
        this.environment = environment;


        try {
            HOST_PORT = environment.getProperty("local.server.port");
            HOST_NAME = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }



    /**
     * This method is in change of load the the login
     * @param model
     * @param request
     *
     * @return login view
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request){
        try {
            init();
            browser = webClient.getPage(LOGIN_URL);

            //if the captcha couldn be saved then reload the site
            if(!saveCaptcha(request.getSession().getId()))
                return "redirect:" + HOST_SCHEME + HOST_NAME + ":" + HOST_PORT;

            model.addAttribute("message", loginMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "login";
    }




    /**
     * Just a simple call to the service in charge of save the captcha in to the service
     * @return true if the image has been saved otherwise returns false
     */
    private boolean saveCaptcha(String sessionId){

        if(sessionId == null || sessionId.length() == 0)
            return false;

        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\" + sessionId + ".jpg";
        HtmlImage image = browser.<HtmlImage>getFirstByXPath("//*[@id='IDPLogin']/div[3]/label/img");

        if(captchaService.saveCaptcha(image, path)){
            return true;
        }else{
            return false;
        }
    }




    /**
     *  Returns any template by its path ("/templates/login.html")
     * @param path
     * @return
     */
    private String getTemplate(String path){
        try {
            return FileUtils.readFileToString(new File(this.getClass().getResource(path).toURI()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }





    /**
     * in charge of login the application and pass to the next page section
     * @param request
     * @param satLogin
     * @return pages-profile view
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String enterLoginData(HttpServletRequest request, @ModelAttribute SatLogin satLogin) {

        try {

            if(!login(satLogin, request.getSession().getId())){
                String redirectUrl = request.getScheme() + "://localhost:8080";
                loginMessage = "Datos incorrectos intenta nuevamente";
                return "redirect:" + HOST_SCHEME + HOST_NAME + ":" + HOST_PORT;
            }

            browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/ConsultaEmisor.aspx");
            browser.getHtmlElementById("ctl00_MainContent_RdoFechas").click();
            ((HtmlInput) browser.getHtmlElementById("ctl00_MainContent_CldFechaInicial2_Calendario_text")).setValueAttribute("01/01/2017");
            ((HtmlInput) browser.getHtmlElementById("ctl00_MainContent_CldFechaFinal2_Calendario_text")).setValueAttribute("05/10/2017");
            browser = ((HtmlInput) browser.getHtmlElementById("ctl00_MainContent_BtnBusqueda")).click();
            webClient.waitForBackgroundJavaScript(10000);


            final HtmlTable table = browser.getHtmlElementById("ctl00_MainContent_tblResult");
            for (final HtmlTableRow row : table.getRows()) {

                bills.add(
                        new Bills(
                                row.getCells().get(1).asText(), //fiscalId
                                row.getCells().get(2).asText(), //emisorRFC
                                row.getCells().get(3).asText(), //emisorName
                                row.getCells().get(4).asText(), //receiverRFC
                                row.getCells().get(5).asText(), //receiverName
                                row.getCells().get(6).asText(), //emitedDate
                                row.getCells().get(7).asText(), //certificationDate
                                row.getCells().get(8).asText(), //certifiedPAC
                                row.getCells().get(9).asText(), //total
                                row.getCells().get(10).asText(),//voucherEffect
                                row.getCells().get(11).asText() //voucherStatus
                        )
                );

            }

            billsService.setBills(bills);
        } catch (IOException e) {
            e.printStackTrace();
        }

       return "pages-profile";
    }




    /**
     *
     * @param satLogin
     * @param sessionId
     * @return
     */
    private boolean login(SatLogin satLogin, String sessionId){

        try {
            HtmlForm loginForm = browser.getFormByName("IDPLogin");
            HtmlInput rfc = loginForm.getInputByName("Ecom_User_ID");
            HtmlPasswordInput pass = loginForm.getInputByName("Ecom_Password");
            HtmlInput captcha = loginForm.getInputByName("jcaptcha");
            HtmlInput sendButton = loginForm.getInputByName("submit");

            rfc.setValueAttribute(satLogin.getRfc());
            pass.setValueAttribute(satLogin.getPass());
            captcha.setValueAttribute(captchaService.decodeCaptcha(sessionId));

            browser = sendButton.click();

            webClient.waitForBackgroundJavaScript(5000);
            browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/");

            if(browser.getPage().getTitleText().toLowerCase().equals("sat autenticación"))
                return false;

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }




    /**
     * initiates HtmlUnit and some global variables
     */
    private void init() {

        if (proxyEnabled) {
            webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER, "proxy.autozone.com", 8080);
            DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
            credentialsProvider.addCredentials("edomingu", "A17934862-");
        } else {
            webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
        }


        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {

            @Override
            public void timeoutError(HtmlPage arg0, long arg1, long arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void scriptException(HtmlPage arg0, ScriptException arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void malformedScriptURL(HtmlPage arg0, String arg1, MalformedURLException arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void loadScriptError(HtmlPage arg0, URL arg1, Exception arg2) {
                // TODO Auto-generated method stub

            }
        });
        webClient.setHTMLParserListener(new HTMLParserListener() {

            @Override
            public void error(String message, URL url, String html, int line, int column, String key) {

            }

            @Override
            public void warning(String message, URL url, String html, int line, int column, String key) {

            }
        });


        //CookieManager cookieMan = new CookieManager();
        //cookieMan = browser.getCookieManager();
        //cookieMan.setCookiesEnabled(true);
    }

}
