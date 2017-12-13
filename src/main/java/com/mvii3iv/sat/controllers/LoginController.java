package com.mvii3iv.sat.controllers;


import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.mvii3iv.sat.models.Bills;
import com.mvii3iv.sat.models.SatLogin;
import com.mvii3iv.sat.services.AntiCaptchaService;
import com.mvii3iv.sat.services.BillsService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.css.sac.ErrorHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private boolean proxyEnabled = false;
    private HtmlPage browser = null;
    private String decodedCaptcha;
    private String loginMessage = "";

    BillsService billsService;
    AntiCaptchaService antiCaptchaService;

    List bills = new ArrayList<Bills>();

    @Autowired
    public LoginController(BillsService billsService, AntiCaptchaService antiCaptchaService) {
        this.billsService = billsService;
        this.antiCaptchaService = antiCaptchaService;
    }


    /**
     * This method is in change od load the the login
     * @param
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Model model){

        try {
            init();
            //browser = null;
            browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/");
            saveCaptcha();
            model.addAttribute("message", loginMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "login";
    }






    /**
     * Saves the captcha from the SAT page in to the local server
     * @return true is the captcha has been saved
     */
    private boolean saveCaptcha(){
        try {
            HtmlImage image = browser.<HtmlImage>getFirstByXPath("//*[@id='IDPLogin']/div[3]/label/img");
            File imageFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\captcha.jpg");
            image.saveAs(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * Inserts the downloaded captcha in to the login template
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

    private String decodeCaptcha(){
        try {
            Path path = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\captcha.jpg");
            byte[] bytes = new byte[0];
            bytes = Files.readAllBytes(path);


            decodedCaptcha = antiCaptchaService.decode(bytes);
            return decodedCaptcha;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Returns any template by its path ("/templates/login.html")
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






    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "pages-profile";
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String data() {
        return "data";
    }

    @ResponseBody
    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public int downloadPDF() throws IOException, URISyntaxException {


        String javascript = ((DomAttr)browser.getByXPath("//*[@id=\"ctl00_MainContent_tblResult\"]/tbody/tr[17]/td[1]/div/img/@onclick").get(1)).getValue();

        ScriptResult result = browser.executeJavaScript(javascript);
        webClient.waitForBackgroundJavaScript(10000);

        InputStream input = result.getNewPage().getWebResponse().getContentAsStream();

        OutputStream output = new FileOutputStream(
                new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\pdf\\test.pdf"));

        int c = 0;
        byte[] buf = new byte[8192];
        while ((c = input.read(buf, 0, buf.length)) > 0) {
            output.write(buf, 0, c);
            output.flush();
        }

        output.close();
        input.close();

        return 200;
    }

    private boolean login(SatLogin satLogin){

        try {
            HtmlForm loginForm = browser.getFormByName("IDPLogin");
            HtmlInput rfc = loginForm.getInputByName("Ecom_User_ID");
            HtmlPasswordInput pass = loginForm.getInputByName("Ecom_Password");
            HtmlInput captcha = loginForm.getInputByName("jcaptcha");
            HtmlInput sendButton = loginForm.getInputByName("submit");

            rfc.setValueAttribute(satLogin.getRfc());
            pass.setValueAttribute(satLogin.getPass());
            captcha.setValueAttribute(decodeCaptcha()/*satLogin.getCaptcha()*/);

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



    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String enterLoginData(HttpServletRequest request, @ModelAttribute SatLogin satLogin) {

        try {

            if(!login(satLogin)){
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
