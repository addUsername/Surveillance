package com.addusername.surv.model;

import android.util.Log;

import com.addusername.surv.dtos.LoginForm;
import com.addusername.surv.dtos.RegisterForm;
import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainModel implements ModelOps {

    private PresenterOpsModel pom;
    private final ExecutorService bgExecutor = Executors.newSingleThreadExecutor();
    private RestTemplate rt;
    private String FILESDIR;
    private final String FILESQL = "dump2.sql";

    public MainModel(PresenterOpsModel pom, File file) {

        this.pom = pom;
        this.FILESDIR = file.getAbsolutePath();
        this.rt = new RestTemplate();
    }

    @Override
    public void doLogin(LoginForm loginForm) {

        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() {

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
                HttpEntity<Resource> requestEntityBody = new HttpEntity<Resource>(content, headers);

                // pinDTO
                HttpHeaders head = new HttpHeaders();
                head.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> pinEntity = new HttpEntity<>("{ \"pin\":"+loginForm.getPin()+"}",head);

                body.add("data", requestEntityBody);
                body.add("pin", pinEntity);

                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, requestHeaders);
                ResponseEntity<String> response = null;
                try {

                    response = rt.exchange("http://192.168.1.51:8080/auth/login", HttpMethod.POST, requestEntity, String.class);

                    Log.d("Response:", response.toString());
                } catch (Exception exception) {
                    Log.d("loging", exception.getMessage());
                }
            }
        });
    }

    @Override
    public void doRegister(RegisterForm registerForm) {

        Log.d("reg","doRegister");
        this.bgExecutor.execute(new Runnable() {

            @Override
            public void run() {
                Log.d("reg","run");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                JSONObject body = new JSONObject();
                for (Field field : registerForm.getClass().getDeclaredFields()) {
                    try {
                        body.put(field.getName(), field.get(registerForm));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.d("reg","json = "+body.toString());
                HttpEntity<String> request =
                        new HttpEntity<String>(body.toString(), headers);

                ResponseEntity<byte[]> response = rt.exchange("http://192.168.1.51:8080/auth/register", HttpMethod.POST, request, byte[].class);
                Log.d("reg","body size = "+response.getBody().length);

                try {
                    Log.d("reg","writting file to "+FILESDIR +"/"+ FILESQL);
                    FileOutputStream fos = new FileOutputStream(FILESDIR +"/"+ FILESQL);
                    fos.write(response.getBody());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("reg","exist file: "+existsUser());
            }
        });
    }

    @Override
    public String[] validate(RegisterForm rf) {

        List<String> list = RegisterFormValidator.getErrors(rf);
        return (list.size() > 0)? list.toArray(new String[list.size()]) : null;
    }

    public class MultipartByteArrayResource extends ByteArrayResource{

        private String fileName;

        public MultipartByteArrayResource(byte[] byteArray, String filename) {
            super(byteArray);
            this.fileName = filename;
        }

        public String getFilename() {
            return fileName;
        }

        public void setFilename(String fileName) {
            this.fileName= fileName;
        }
    }
    
    @Override
    public boolean existsUser() {
        return new File(FILESDIR +"/"+ FILESQL).exists();
    }
}
