package com.addusername.surv.view.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.addusername.surv.R;
import com.addusername.surv.dtos.HomeDTO;
import com.addusername.surv.dtos.PiDTO;
import com.addusername.surv.interfaces.MenuListener;
import com.addusername.surv.interfaces.PresenterOpsViewUser;
import com.addusername.surv.interfaces.SetImages;
import com.addusername.surv.interfaces.ViewFragmentOpsUser;
import com.addusername.surv.interfaces.ViewOpsHome;
import com.addusername.surv.presenter.UserPresenter;

import java.io.InputStream;
import java.util.List;

public class UserActivity extends AppCompatActivity implements ViewOpsHome, ViewFragmentOpsUser, MenuListener {


    private PresenterOpsViewUser povu;
    private SetImages homeFragment;
    private final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        String token = getIntent().getStringExtra("token");
        String host = getIntent().getStringExtra("host");

        Log.d("user","onCreate() token= "+token);
        povu = new UserPresenter(token, this, host, getApplicationContext().getFilesDir());
        povu.doGetHome();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }
    @Override
    public void menuClick(MenuItem item) {

        FragmentTransaction ft = fm.beginTransaction();
        Log.d("user","menuClick() itemID= "+item.getItemId());
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
            case R.id.menu_addpi:
                Log.d("user","menu_addpi");
                ft.replace(R.id.fragmentUser, AddRPiFragment.newInstance());
                ft.commit();
                break;
            case R.id.menu_home:
                Log.d("user","menu_home");
                povu.doGetHome();

        }
    }
    @Override
    public void printHome(HomeDTO home) {
        //TODO identify and delete old fragment
        FragmentTransaction ft = fm.beginTransaction();
        HomeFragment hf =  new HomeFragment(home);
        homeFragment = hf;
        ft.replace(R.id.fragmentUser, hf);
        ft.commit();
    }

    @Override
    public void showMessage(String mssg) {
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), mssg, Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public void setImg(InputStream img, Integer id) {
        runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  homeFragment.setImg(img,id);
              }
          });
    }
    @Override
    public void addRpi(String alias, String location) { povu.doAddRpi(new PiDTO(alias, location)); }
    @Override
    public void loadImgs(List<Integer> raspberryIds) { povu.loadImgs(raspberryIds);}
}