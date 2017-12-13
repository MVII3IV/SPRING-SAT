package com.mvii3iv.sat.components.login;


import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.mvii3iv.sat.components.bills.Bills;
import com.mvii3iv.sat.components.anticaptcha.AntiCaptchaService;
import com.mvii3iv.sat.components.bills.BillsService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
    private String decodedCaptcha;
    private String loginMessage = "";
    List bills = new ArrayList<Bills>();

    /**
     * Services
     */
    private BillsService billsService;
    private AntiCaptchaService antiCaptchaService;


    @Autowired
    public LoginController(BillsService billsService, AntiCaptchaService antiCaptchaService) {
        this.billsService = billsService;
        this.antiCaptchaService = antiCaptchaService;
    }


    /**
     * This method is in change of load the the login
     * @param
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request){

        try {
            init();
            browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/");
            saveCaptcha(request.getSession().getId());
            model.addAttribute("message", loginMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "login";
    }



    /**
     * Saves the captcha from the SAT page in to the local server
     * @param sessionId
     * @return true is the captcha has been saved
     */
    private boolean saveCaptcha(String sessionId){
        try {
            HtmlImage image = browser.<HtmlImage>getFirstByXPath("//*[@id='IDPLogin']/div[3]/label/img");
            File imageFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\" + sessionId + ".jpg");
            image.saveAs(imageFile);
            System.out.println("Captcha id: " + sessionId);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }



    /**
     * Inserts the downloaded captcha in to the login template (not used anymore, leave the code as future reference)
     * @deprecated
     * @param template
     * @return the same template but with the captcha inserted
     */
    private String insertCaptcha(String template){
        try {
            Path path = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\captcha.jpg");
            byte[] bytes = new byte[0];
            bytes = Files.readAllBytes(path);
            String encodedImage = Base64.encode(bytes);
            return template.replace("$captcha", encodedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * decodes the captcha delegating data to the AntiCaptchaService.decode
     * @param sessionId
     * @return an string with the captcha decoded
     */
    private String decodeCaptcha(String sessionId){
        try {
            System.out.println("Decoding captcha id: " + sessionId);
            Path path = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\" + sessionId + ".jpg");
            byte[] bytes = new byte[0];
            bytes = Files.readAllBytes(path);
            decodedCaptcha = antiCaptchaService.decode(bytes);
            System.out.println("Captcha decoded: " + decodedCaptcha);
            deleteCaptchaFromServer(path.toAbsolutePath().toString());
            return decodedCaptcha;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private boolean deleteCaptchaFromServer(String path){
        try{

            File file = new File(path);

            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation is failed.");
                return false;
            }

            return true;

        }catch(Exception e){

            e.printStackTrace();
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
                return "redirect:" + redirectUrl;
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
            captcha.setValueAttribute(decodeCaptcha(sessionId));

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
