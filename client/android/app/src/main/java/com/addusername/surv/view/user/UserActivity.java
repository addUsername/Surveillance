package com.addusername.surv.view.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
    public void showImg(InputStream is) {
        //TODO put img inside imageView not as backgroundimg
        Context c = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((UserActivity) c).getWindowManager()
                        .getDefaultDisplay()
                        .getMetrics(displayMetrics);
                int height = (int) Math.round(displayMetrics.heightPixels * 0.8);
                int width =  (int) Math.round(displayMetrics.widthPixels * 0.9);
                */
                Dialog imgDialog = new Dialog(c);
                imgDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                LinearLayout ll =  new LinearLayout(c);
                BitmapDrawable img = new BitmapDrawable(is){
                    @Override
                    public void draw(Canvas canvas) {
                        canvas.save();
                        Log.d("img","dim "+(this.getBitmap().getWidth() / 2) + " " + this.getBitmap().getHeight() / 2);
                        super.draw(canvas);
                        canvas.restore();
                    }
                };
                int width = (int) Math.round(img.getBitmap().getWidth() * 2);
                int height =  (int) Math.round(img.getBitmap().getHeight() * 2);
                ll.setLayoutParams(new LinearLayout.LayoutParams(width,height));
                ll.setBackground(img);
                imgDialog.setContentView(ll, new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(width,height)));
                imgDialog.show();
            }
        });
    }

    @Override
    public void showStream(String html) {
        UserActivity c = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                Log.d("user","loadingwebview()");
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((UserActivity) c).getWindowManager()
                        .getDefaultDisplay()
                        .getMetrics(displayMetrics);
                int height = (int) Math.round(displayMetrics.heightPixels * 0.8);
                int width =  (int) Math.round(displayMetrics.widthPixels * 0.9);

                Dialog imgDialog = new Dialog(c);
                WebView webView = new WebView(c);

                DialogInterface.OnDismissListener p = new DialogInterface.OnDismissListener(){
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        c.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        webView.destroy();
                    }
                };
                imgDialog.setOnDismissListener(p);
                webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                //imgDialog.setContentView(webView, new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(width,height)));
                imgDialog.setContentView(webView, new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(height,width)));
                imgDialog.show();
            }
        });

    }

    @Override
    public void addRpi(String alias, String location) { povu.doAddRpi(new PiDTO(alias, location)); }
    @Override
    public void loadImgs(List<Integer> raspberryIds) { povu.loadImgs(raspberryIds);}

    @Override
    public void handleHomeClickEvent(int rpiId, int actionId) {
        String action = null;
        switch (actionId){
            case R.id.popup_img:
                action = "screenshot";
                break;
            case R.id.popup_video:
                action = "stream";
                break;
        }
        if(action !=  null) povu.getFromRPi(rpiId,action);
    }
}