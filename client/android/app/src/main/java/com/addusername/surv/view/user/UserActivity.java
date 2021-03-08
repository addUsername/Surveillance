package com.addusername.surv.view.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.addusername.surv.R;
import com.addusername.surv.interfaces.PresenterOpsViewUser;
import com.addusername.surv.interfaces.ViewOpsHome;
import com.addusername.surv.presenter.UserPresenter;

public class UserActivity extends AppCompatActivity implements ViewOpsHome {

    private PresenterOpsViewUser povu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        String token = getIntent().getStringExtra("token");
        Log.d("token user",token);
        povu = new UserPresenter(token);
    }
}