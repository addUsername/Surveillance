package com.addusername.surv.model;

import com.addusername.surv.dtos.LoginForm;
import com.addusername.surv.dtos.RegisterForm;
import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;

import org.springframework.web.client.RestTemplate;

import java.io.File;
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

    }

    @Override
    public String[] validate(RegisterForm rf) {

        List<String> list = RegisterFormValidator.getErrors(rf);
        return (list.size() > 0)? list.toArray(new String[list.size()]) : null;
    }
}
