package com.addusername.surv.model.auth;

import android.util.Log;

import androidx.annotation.NonNull;

import com.addusername.surv.dtos.LoginForm;
import com.addusername.surv.dtos.RegisterForm;
import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainModel implements ModelOps {

    private final PresenterOpsModel pom;
    private final ExecutorService bgExecutor = Executors.newSingleThreadExecutor();
    private final AuthService auth;


    public MainModel(PresenterOpsModel pom, File file) {

        this.pom = pom;
    //"http://192.168.1.51:8080"
        this.auth = new AuthService(file.getAbsolutePath(),"http://192.168.1.51:8080"); // ""
        doFCM();
    }

    private void doFCM() {
        //notification push token, send inside LoginForm in doLogin()
        //this method will run always before login
        this.bgExecutor.execute(new Runnable() {
            @Override
            public void run() {
                FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.d("noti", "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            String token = task.getResult();
                            auth.setFCMtoken(token);
                            Log.d("getToken() noti", token);
                            //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
        /*
        FirebaseMessaging.getInstance().subscribeToTopic("news").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                Log.d("subs","yay");
            }
        });
        */
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
