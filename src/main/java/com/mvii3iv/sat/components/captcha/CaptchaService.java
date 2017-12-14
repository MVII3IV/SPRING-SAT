package com.mvii3iv.sat.components.captcha;

import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.mvii3iv.sat.components.anticaptcha.AntiCaptchaService;
import com.mvii3iv.sat.components.bills.BillsService;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CaptchaService {

    private AntiCaptchaService antiCaptchaService;

    @Autowired
    public CaptchaService(BillsService billsService, AntiCaptchaService antiCaptchaService) {
        this.antiCaptchaService = antiCaptchaService;
    }



    /**
     * Saves the captcha from the SAT page in to the local server
     * @param image
     * @param path
     *
     * @return true is the captcha has been saved
     */
    public boolean saveCaptcha(HtmlImage image, String path){

        File imagePath = new File(path);

        try {

            if(image == null || path.length() == 0){
                return false;
            }

            image.saveAs(imagePath);

            if(!imagePath.exists())
                throw new IOException();

            System.out.println("Captcha saved: " + imagePath.getName());
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Captcha couldn't be saved: " + imagePath.getName());
        return false;
    }



    /**
     * Inserts the downloaded captcha in to the login template (not used anymore, leave the code as future reference)
     * @deprecated
     * @param template
     * @return the same template but with the captcha inserted
     */
    public String insertCaptcha(String template){
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
    public String decodeCaptcha(String sessionId){
        try {
            System.out.println("Decoding captcha id: " + sessionId);

            Path path = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img\\" + sessionId + ".jpg");
            byte[] bytes = new byte[0];
            bytes = Files.readAllBytes(path);
            String decodedCaptcha = antiCaptchaService.decode(bytes);
            System.out.println("Captcha decoded: " + decodedCaptcha);
            deleteCaptchaFromServer(path.toAbsolutePath().toString());
            return decodedCaptcha;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * delete a captcha after has been decoded due it is not usable anymore
     * @param path
     * @return true if the operation was successfully
     */
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

}
