package com.addusername.surv.model.auth;

import android.util.Log;

import com.addusername.surv.dtos.LoginForm;
import com.addusername.surv.dtos.RegisterForm;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class AuthService {

    private final RestTemplate rt;
    private final String FILESDIR;
    private final String HOST;
    private final String FILESQL = "dump2.sql";
    private String TOKEN;
    private String FCMtoken = "";

    public AuthService(String path, String host) {
        this.FILESDIR = path;
        this.HOST = host;
        this.rt = new RestTemplate();
    }

    public boolean doLogin(LoginForm loginForm) {

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        Resource content = null;
        try {
            content = new MultipartByteArrayResource(FileUtils.readFileToByteArray(new File(FILESDIR +"/"+ FILESQL)), FILESQL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // dump.sql
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<Resource> requestEntityBody = new HttpEntity<>(content, headers);

        // pinDTO
        Log.d("token","doLogin() = "+this.FCMtoken);
        loginForm.setToken(this.FCMtoken);
        HttpHeaders head = new HttpHeaders();
        head.setContentType(MediaType.APPLICATION_JSON);
        Log.d("token",loginForm.toJsonString());
        HttpEntity<String> pinEntity = new HttpEntity<>(loginForm.toJsonString(),head);

        body.add("data", requestEntityBody);
        body.add("pin", pinEntity);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, requestHeaders);
        ResponseEntity<String> response = null;
        try {

            response = rt.exchange(HOST+"/auth/login", HttpMethod.POST, requestEntity, String.class);
        } catch (Exception exception) {
            Log.d("auth", "doLogin()"+exception.getMessage());
        }

        if(response.getStatusCode().is2xxSuccessful()){
            this.TOKEN = response.getBody();
            return true;
        }
        return false;
    }

    public boolean doRegister(RegisterForm registerForm) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        /*
        JSONObject body = new JSONObject();
        for (Field field : registerForm.getClass().getDeclaredFields()) {
            try {
                body.put(field.getName(), field.get(registerForm));
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
         */
        Log.d("auth","doReg() json = "+registerForm.toJsonString());
        HttpEntity<String> request = new HttpEntity<>(registerForm.toJsonString(), headers);
        ResponseEntity<byte[]> response = null;

        try{
            response = rt.exchange(HOST+"/auth/register", HttpMethod.POST, request, byte[].class);
        } catch (Exception e){
            Log.d("auth","doReg() exception: "+e.getMessage());
            e.printStackTrace();
        }
        Log.d("auth","doReg() body size = "+response.getBody().length);

        if(response.getStatusCode().is2xxSuccessful()){
            try {
                Log.d("auth","doReg() writting file to "+FILESDIR +"/"+ FILESQL);
                FileOutputStream fos = new FileOutputStream(FILESDIR +"/"+ FILESQL);
                fos.write(response.getBody());
                fos.close();
                return true;
            } catch (IOException e) {
                //e.printStackTrace();
                return false;
            }
        }
        Log.d("auth","doReg() exist file: "+existsUser());
        return true;
    }

    public String[] validate(RegisterForm rf) {

        List<String> list = RegisterFormValidator.getErrors(rf);
        return (list.size() > 0)? list.toArray(new String[list.size()]) : null;
    }
    public boolean existsUser() { return new File(FILESDIR +"/"+ FILESQL).exists(); }
    public boolean isUsserLogged() { return (TOKEN != null); }
    public String getToken(){ return TOKEN; }
    public String getHost() { return HOST; }
    public void setFCMtoken(String fcMtoken) { this.FCMtoken = fcMtoken; }

    public class MultipartByteArrayResource extends ByteArrayResource {

        private final String fileName;

        public MultipartByteArrayResource(byte[] byteArray, String filename) {
            super(byteArray);
            this.fileName = filename;
        }

        public String getFilename() {
            return fileName;
        }
    }
}