package com.addusername.surv.view.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.addusername.surv.R;
import com.addusername.surv.dtos.HomeDTO;
import com.addusername.surv.dtos.PiDTO;
import com.addusername.surv.interfaces.PresenterOpsViewUser;
import com.addusername.surv.interfaces.ViewFragmentOpsUser;
import com.addusername.surv.interfaces.ViewOpsHome;
import com.addusername.surv.presenter.UserPresenter;

public class UserActivity extends AppCompatActivity implements ViewOpsHome, ViewFragmentOpsUser {


    private PresenterOpsViewUser povu;
    private final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        String token = getIntent().getStringExtra("token");
        String host = getIntent().getStringExtra("host");

        Log.d("token user",token);
        povu = new UserPresenter(token, this, host);
        povu.doGetHome();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    public void menuClick(MenuItem item) {

        FragmentTransaction ft = fm.beginTransaction();

        switch (item.getItemId()) {
            case R.id.menu_account:
                //ft.replace(R.id.fragment, RegisterFragment.newInstance());
                //ft.commit();
                break;
            case R.id.menu_logout:
                //ft.replace(R.id.fragment, RegisterFragment.newInstance());
                break;
            case R.id.menu_settings:
                //ft.replace(R.id.fragment, RegisterFragment.newInstance());
                break;

        }
    }
    @Override
    public void printHome(HomeDTO home) {

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentUser, new HomeFragment(home));
        ft.commit();
    }

    @Override
    public void showMessage(String mssg) { Toast.makeText(getBaseContext(),mssg,Toast.LENGTH_SHORT).show(); }

    @Override
    public void addRpi(String alias, String location) {
        povu.doAddRpi(new PiDTO(alias, location));
    }
}