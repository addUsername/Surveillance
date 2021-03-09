package com.addusername.surv.model.user;

import com.addusername.surv.dtos.PiDTO;
import com.addusername.surv.interfaces.ModelOpsUser;
import com.addusername.surv.interfaces.PresenterOpsModelUser;

import org.springframework.http.HttpHeaders;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserModel implements ModelOpsUser {

    private final PresenterOpsModelUser pomu;
    private final HttpHeaders headers = new HttpHeaders();
    private final ExecutorService bgExecutor = Executors.newSingleThreadExecutor();
    private final UserService us;

    public UserModel(PresenterOpsModelUser pomu, String token, String host) {
        this.pomu = pomu;
        us = new UserService(token,host);
    }

    @Override
    public void doGetHome() {
        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() { pomu.homeReturn(us.doHome()); }
        });

    }

    @Override
    public void doAddRpi(PiDTO piDTO) {
        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() {
                pomu.addRpiReturn(us.doAddRpi(piDTO));
            }
        });
    }
}
