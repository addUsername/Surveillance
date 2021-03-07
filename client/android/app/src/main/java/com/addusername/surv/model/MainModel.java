package com.addusername.surv.model;

import android.util.Log;

import com.addusername.surv.dtos.LoginForm;
import com.addusername.surv.dtos.RegisterForm;
import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainModel implements ModelOps {

    private PresenterOpsModel pom;
    private final ExecutorService bgExecutor = Executors.newSingleThreadExecutor();
    private RestTemplate rt = new RestTemplate();
    private String FILESDIR;
    private final String FILESQL = "dump.sql";

    public MainModel(PresenterOpsModel pom, File file) {

        this.pom = pom;
        this.FILESDIR = file.getAbsolutePath();
    }

    @Override
    public boolean existsUser() {
        return new File(FILESDIR +"/"+ FILESQL).exists();
    }

    @Override
    public void doLogin(LoginForm loginForm) {

        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() {

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
}
