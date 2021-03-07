package com.addusername.surv.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.addusername.surv.R;
import com.addusername.surv.dtos.RegisterForm;
import com.addusername.surv.interfaces.PresenterOpsView;
import com.addusername.surv.interfaces.ViewFragmentOps;
import com.addusername.surv.interfaces.ViewOps;
import com.addusername.surv.presenter.MainPresenter;

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

    private void loadFragment() {

        FragmentTransaction ft = fm.beginTransaction();
        if(pov.existUser()){
            ft.replace(R.id.fragment,LoginFragment.newInstance());
            //ft.remove(LogoFragment.newInstance());
        }else {
            ft.replace(R.id.fragment, RegisterFragment.newInstance());
        }
        ft.commit();
    }

    @Override
    public void login(String pin) {
        Log.d("aa","loggginn");
        pov.login(pin);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, LogoFragment.newInstance());
        ft.remove(LoginFragment.newInstance()).commit();
    }

    @Override
    public boolean validateComponents(HashMap<String, EditText> components) {
        String[] erros = pov.validate(parseRegisterForm(components));
        if(erros == null) return true;
        for(String error: erros){
            if(!error.equals("NOTSHOW"))Toast.makeText(getBaseContext(),error,Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void register(HashMap<String, EditText> components) {

    }

    private RegisterForm parseRegisterForm(HashMap<String, EditText> components){
        RegisterForm rf = new RegisterForm();
        rf.setUsername(components.get("username").getText().toString());
        rf.setPass(components.get("pass").getText().toString());
        rf.setPass2(components.get("pass2").getText().toString());
        rf.setEmail(components.get("email").getText().toString());
        if(components.get("pin").getText().toString().equals("")){
            rf.setPin(0);
        }else{
            rf.setPin(Integer.parseInt(components.get("pin").getText().toString()));
        }
        return rf;
    }
}