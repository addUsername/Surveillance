package com.addusername.surv.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.addusername.surv.R;
import com.addusername.surv.interfaces.ModelOps;
import com.addusername.surv.interfaces.ViewOps;
import com.addusername.surv.model.MainModel;
import com.addusername.surv.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements ViewOps {

    private MainPresenter mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }

    private void setup() {
        this.mp = new MainPresenter(new MainModel(), this);
    }
}