package com.addusername.surv.model.user;

import com.addusername.surv.interfaces.ModelOpsUser;
import com.addusername.surv.interfaces.PresenterOpsModelUser;

import org.springframework.http.HttpHeaders;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserModel implements ModelOpsUser {

    private final PresenterOpsModelUser pomu;
    private final HttpHeaders headers = new HttpHeaders();
    private final ExecutorService bgExecutor = Executors.newSingleThreadExecutor();
    private final UserService us = new UserService();
    private final String HOST;

    public UserModel(PresenterOpsModelUser pomu, String token) {
        this.pomu = pomu;
        headers.set("Authorization","bearer "+token);
        HOST = getProperty("host");
    }

    private String getProperty(String prop){
        // todo Search for a properties file with the host persisted in by MainModel
        return "http://192.168.1.51:8080";
    }
    @Override
    public void doGetHome() {
        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() { pomu.homeReturn(us.doHome()); }
        });

    }
}
