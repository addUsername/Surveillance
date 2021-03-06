package com.addusername.surv.model;

import com.addusername.surv.dtos.LoginForm;
import com.addusername.surv.dtos.RegisterForm;
import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;

import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainModel implements ModelOps {

    PresenterOpsModel pom;
    ExecutorService bgExecutor = Executors.newSingleThreadExecutor();
    RestTemplate rt = new RestTemplate();

    public MainModel(PresenterOpsModel pom) {
        this.pom = pom;
    }

    @Override
    public boolean existsUser() {
        return false;
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

    }
}
