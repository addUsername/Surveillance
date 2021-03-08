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

    private final PresenterOpsModel pom;
    private final ExecutorService bgExecutor = Executors.newSingleThreadExecutor();
    private final AuthService auth;


    public MainModel(PresenterOpsModel pom, File file) {

        this.pom = pom;
        this.auth = new AuthService(file.getAbsolutePath(),"http://192.168.1.51:8080");
    }

    @Override
    public void doLogin(LoginForm loginForm) {

        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() { pom.loginReturn(auth.doLogin(loginForm)); }
        });
    }
    @Override
    public void doRegister(RegisterForm registerForm) {

        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() { pom.registerReturn(auth.doRegister(registerForm)); }
        });
    }

    @Override
    public String[] validate(RegisterForm rf) {
        return auth.validate(rf);
    }

    @Override
    public boolean isUserLogged() { return auth.isUsserLogged(); }

    @Override
    public boolean existsUser() {
        return auth.existsUser();
    }
}
