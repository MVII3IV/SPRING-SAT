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
import org.springframework.stereotype.Service;

@Service
public class AntiCaptchaService {

    private boolean USING_PROXY = false;
    private final String CLIENT_kEY = "d2e3cdd426d740b83ee2bd655067412e";
    private final String TASK_RESULT_URL = "https://api.anti-captcha.com/getTaskResult";
    private final String CREATE_TASK_URL = "https://api.anti-captcha.com/createTask";
    private JSONParser parser = new JSONParser();


    public String decode(byte[] captcha) {
        try {

            String createTaskResult = createTask(captcha);
            JSONObject jsonTaskResult = null;
            jsonTaskResult = (JSONObject) parser.parse(createTaskResult);
            String taskId = jsonTaskResult.get("taskId").toString();
            String result = getTaskResult(taskId);
            String resolvedCaptcha = ((JSONObject) ((JSONObject) parser.parse(result)).get("solution")).get("text").toString();

            return resolvedCaptcha;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String createTask(byte[] captcha) {


        JSONObject jsonData = new JSONObject();
        jsonData.put("clientKey", CLIENT_kEY);
        String captchaEncoded = Base64.encode(captcha);

        JSONObject task = new JSONObject();
        task.put("type", "ImageToTextTask");
        task.put("body", captchaEncoded);
        task.put("phrase", false);
        task.put("case", true);
        task.put("numeric", false);
        task.put("math", 0);
        task.put("minLength", 0);
        task.put("maxLength", 0);

        jsonData.put("task", task);


        return postDataToCreateTask(CREATE_TASK_URL, jsonData.toJSONString());
    }


    public String getTaskResult(String taskId) {

        JSONObject jsonData = new JSONObject();
        jsonData.put("clientKey", CLIENT_kEY);
        jsonData.put("taskId", taskId);

        return postDataToGetTaskResult(TASK_RESULT_URL, jsonData.toJSONString());
    }


    private String postDataToCreateTask(String u, String jsonData) {
        try {
            HttpURLConnection conn = null;
            URL url = new URL(u);

            if (USING_PROXY) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP,
                        new InetSocketAddress("proxy.autozone.com", 8080));
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
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
                System.out.println(output);
                try {

                    int errorId = Integer.parseInt(((JSONObject) parser.parse(output)).get("errorId").toString());
                    if (errorId == 0)
                        return output;
                    else
                        System.out.println("anti-captcha returned error > 0");

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(output);

            }
            return null;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String postDataToGetTaskResult(String u, String jsonData) {
        boolean isResolved = false;
        try {
            do {
            HttpURLConnection conn = null;
            URL url = new URL(u);

            if (USING_PROXY) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP,
                        new InetSocketAddress("proxy.autozone.com", 8080));
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");



                try {
                    OutputStream os = conn.getOutputStream();
                    os.write(jsonData.getBytes());
                    os.flush();

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));


                    System.out.println("Output from Server .... \n");
                    String output = br.readLine();
                    System.out.println(output);


                    if (Integer.parseInt(((JSONObject) parser.parse(output)).get("errorId").toString()) == 0) {

                        if(     ((JSONObject) parser.parse(output)).get("status").toString().equals("ready")    ){
                            return output;
                        }else{
                            conn.disconnect();
                            os.close();
                            br.close();
                            //tries counter
                        }

                    } else {
                        System.out.println("anti-captcha returned error > 0");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (!isResolved);

            return null;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}