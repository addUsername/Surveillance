package com.addusername.surv.view.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.addusername.surv.R;
import com.addusername.surv.dtos.HomeDTO;
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
        povu = new UserPresenter(token, this);
        povu.doGetHome();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    public void menuClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_account:
                break;
            case R.id.menu_logout:
                break;
            case R.id.menu_settings:
                break;
        }
    }

    @Override
    public void printHome(HomeDTO home) {

    }
}