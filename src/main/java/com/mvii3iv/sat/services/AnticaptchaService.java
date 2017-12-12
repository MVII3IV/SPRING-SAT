package com.mvii3iv.sat.services;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

import com.fasterxml.jackson.databind.deser.Deserializers;
import org.apache.xerces.impl.dv.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AnticaptchaService {

    private boolean USING_PROXY = true;
    private final String CLIENT_kEY = "d2e3cdd426d740b83ee2bd655067412e";
    private final String TASK_RESULT_URL = "https://api.anti-captcha.com/getTaskResult";
    private final String CREATE_TASK_URL = "https://api.anti-captcha.com/createTask";




    public static void main(String[] args) throws ParseException {

        AnticaptchaService anticaptchaService = new AnticaptchaService();


        //String taskId = "30268294";
        JSONParser parser = new JSONParser();

        String createTaskResult = anticaptchaService.createTask();

        Object JSON = parser.parse(createTaskResult);

        JSONObject jsonTaskResult = (JSONObject) JSON;

        String taskId = jsonTaskResult.get("taskId").toString();

        String result = anticaptchaService.getTaskResult(taskId);

    }



    public String createTask(){


        JSONObject jsonData = new JSONObject();
        jsonData.put("clientKey", CLIENT_kEY);

        JSONObject task = new JSONObject();
            task.put("type", "ImageToTextTask");
            task.put("body", "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fR9MhsDcmeOFQiMyBSAPXFc74k8CS+JL6O5udSA8tSqIIcAD86VWE3RUYrXT8CsLXorGyq1Ze6+bv1uU38D2vicnVJ4/7OeYbkWDr9W965vxJoPiDwnp00keo/a7GYCN3dfnUduT0/CvSbBdZ020jtZoYb0RqFWZJPLYj3Ug/wA6g1bRbzxJClrqBS2sNwaSKJyzyY5wWwMD6VE6EZR91Wl/XyNqGYVKdVKpJOmum+nl1/rUw/hKYj4YnCkGX7Qxf1AIGP5Guv1rUYtK0i6vJWx5cZKjuzY4A9yaxIfBEGm373eiX9xp3mffiTDxt+BrRk8Ow3dvKl/d3F3JIpTzHIXYD12qBgfXr71rSjOFPktqjlxVShWxLrc3ut3tbX07ficr8KIGksNT1STl7u4xn6ZP/s1eh1jaH4Y03w8hWwE6g8kNOxUn125x+lbNXQg4U1FmGPrxr4iVSGzCiiitTkCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA//Z");
            task.put("phrase", false);
            task.put("case", true);
            task.put("numeric", false);
            task.put("math", 0);
            task.put("minLength", 0);
            task.put("maxLength", 0);

        jsonData.put("task", task);

        return postData(CREATE_TASK_URL, jsonData.toJSONString());
    }


    public String getTaskResult(String taskId){

        JSONObject jsonData = new JSONObject();
            jsonData.put("clientKey", CLIENT_kEY);
            jsonData.put("taskId", taskId);

        return postData(TASK_RESULT_URL, jsonData.toJSONString());
    }


    private String postData(String u, String jsonData){
        try {
            HttpURLConnection conn = null;
            URL url = new URL(u);

            if(USING_PROXY) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP,
                        new InetSocketAddress("proxy.autozone.com", 8080));
                conn = (HttpURLConnection) url.openConnection(proxy);
            }else{
                conn = (HttpURLConnection) url.openConnection();
            }

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");


            OutputStream os = conn.getOutputStream();
            os.write(jsonData.getBytes());
            os.flush();

            /*
            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }*/

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = "";
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                return output;
            }
            return null;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}