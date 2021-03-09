package com.addusername.surv.model.auth;

import com.addusername.surv.dtos.LoginForm;
import com.addusername.surv.dtos.RegisterForm;
import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;

import java.io.File;
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
    public String getToken() { return auth.getToken(); }

    @Override
    public String getHost() { return auth.getHost(); }

    @Override
    public boolean existsUser() {
        return auth.existsUser();
    }
}
