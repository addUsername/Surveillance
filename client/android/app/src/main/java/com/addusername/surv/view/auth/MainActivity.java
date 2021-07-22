package com.addusername.surv.view.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.addusername.surv.R;
import com.addusername.surv.interfaces.PresenterOpsView;
import com.addusername.surv.interfaces.ViewFragmentOps;
import com.addusername.surv.interfaces.ViewOps;
import com.addusername.surv.presenter.MainPresenter;
import com.addusername.surv.view.user.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ViewOps, ViewFragmentOps {

    private PresenterOpsView pov;
    private final FragmentManager fm = getSupportFragmentManager();

    private Process process;
    /**
     * to put log to a file
     */

    public Process launchLogcat(String filename) {
        Process process = null;
        String cmd = "logcat -f " + filename + "\n";
        try {
            Log.d("aa","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            process = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            process = null;
        }
        return process;
    }
    /**
     * Delete??
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        startUserActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        process = launchLogcat("sdcard/hiii/log.txt");

        pov = new MainPresenter(this, getApplicationContext().getFilesDir());
        if (pov.getToken() != null) {
            Log.d("noti","start user");
            startUserActivity();
        }else{
            Log.d("noti",""+pov.getToken());
            loadFragment();
        }
    }
    @Override
    public void loadFragment() {

        FragmentTransaction ft = fm.beginTransaction();
        if(!pov.existUser()){
            ft.replace(R.id.fragment, RegisterFragment.newInstance());
            //ft.remove(LogoFragment.newInstance());
        }else if(!pov.isUserLogged()) {
            ft.replace(R.id.fragment, LoginFragment.newInstance());
        }else {
            startUserActivity();
        }
        ft.commit();
    }

    private void startUserActivity() {
        Intent myIntent = new Intent(this, UserActivity.class);
        myIntent.putExtra("token", pov.getToken());
        myIntent.putExtra("host", pov.getHost());
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myIntent);
        overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
    }

    @Override
    public void login(String pin) {
        pov.login(pin);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, LogoFragment.newInstance());
        ft.remove(LoginFragment.newInstance()).commit();
    }

    @Override
    public boolean validateComponents(HashMap<String, EditText> components) {
        String[] erros = pov.validate(components);
        if(erros == null) return true;
        for(String error: erros){
            if(!error.equals("NOTSHOW"))Toast.makeText(getBaseContext(),error,Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void register(HashMap<String, EditText> components) {
        pov.register(components);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, LogoFragment.newInstance());
        ft.remove(RegisterFragment.newInstance()).commit();
    }
    @Override
    public void showMessage(String error) { Toast.makeText(getBaseContext(),error,Toast.LENGTH_SHORT).show(); }

}