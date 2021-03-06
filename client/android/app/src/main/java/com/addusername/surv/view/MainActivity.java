package com.addusername.surv.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.addusername.surv.R;
import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.PresenterOpsModel;
import com.addusername.surv.interfaces.PresenterOpsView;
import com.addusername.surv.interfaces.ViewOps;
import com.addusername.surv.model.MainModel;
import com.addusername.surv.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements ViewOps {

    private PresenterOpsView pov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ini();
        loadFragment();
    }

    private void loadFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(pov.loginFragment()){
            ft.replace(R.id.fragment,LoginFragment.newInstance());
        }else {
            ft.replace(R.id.fragment, RegisterFragment.newInstance());
        }
    }

    private void ini() {
        this.pov = new MainPresenter(this);
    }
}