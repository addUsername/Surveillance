package com.addusername.surv.model.user;

import com.addusername.surv.interfaces.ModelOpsUser;

import org.springframework.http.HttpHeaders;

public class UserModel implements ModelOpsUser {

    private HttpHeaders headers = new HttpHeaders();

    public UserModel(String token) {
        headers.set("Authorization","bearer "+token);
    }
}
