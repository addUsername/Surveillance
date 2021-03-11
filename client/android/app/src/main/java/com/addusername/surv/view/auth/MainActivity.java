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

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ViewOps, ViewFragmentOps {

    private PresenterOpsView pov;
    private final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pov = new MainPresenter(this, getApplicationContext().getFilesDir());
        loadFragment();

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