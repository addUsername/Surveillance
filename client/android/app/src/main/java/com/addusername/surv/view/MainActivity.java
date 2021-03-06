package com.addusername.surv.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.addusername.surv.R;
import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;
import com.addusername.surv.interfaces.PresenterOpsView;
import com.addusername.surv.interfaces.ViewFragmentOps;
import com.addusername.surv.interfaces.ViewOps;
import com.addusername.surv.model.MainModel;
import com.addusername.surv.presenter.MainPresenter;

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
}