package com.mvii3iv.sat.components.index;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.URISyntaxException;

@Controller
public class IndexController {

    /**
     * @return views-profile view
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "views-profile";
    }


    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String data() {
        return "data";
    }


    @ResponseBody
    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public int downloadPDF() throws IOException, URISyntaxException {

        /*
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
        */
        return 0;
    }

}
