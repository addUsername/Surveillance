package com.addusername.surv.view.user;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.addusername.surv.R;
import com.addusername.surv.dtos.HomeDTO;
import com.addusername.surv.dtos.HomePiDTO;
import com.addusername.surv.interfaces.MenuListener;
import com.addusername.surv.interfaces.SetImages;
import com.addusername.surv.interfaces.ViewFragmentOpsUser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements MenuListener, SetImages {

    private ViewFragmentOpsUser vfou;
    private MenuListener ml;
    private HomeDTO homedto;
    private HashMap<Integer,LinearLayout> imgs;

    public HomeFragment(HomeDTO home) {
        homedto = home;
    }
    public HomeFragment(){};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vfou = (UserActivity) getActivity();
        ml = (UserActivity) getActivity();
        Log.d("user", "onViewCreated() RPi num = "+ homedto.getPi_ids().size());
        //Create layout
        GridLayout gl = view.findViewById(R.id.home_layout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((UserActivity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.d("user", "onViewCreated() sizes (h,w) = "+ height +" / "+width);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width*10/23, width*10/23);
        params.setMargins(50,20,50,20);

        imgs = new HashMap<>();

        for(HomePiDTO pi: homedto.getPi_ids()){
            LinearLayout ll = new LinearLayout(this.getContext());
            // TODO this is safe?? i need this bc PopupMenu onclick..
            ll.setId(Math.toIntExact(pi.getId()));

            ll.setLayoutParams(params);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setClickable(true);
            ll.setElevation(5);

            TextView text = new TextView(this.getContext());
            text.setText(pi.getStatus()+"  "+pi.getAlias());
            text.setTypeface(null, Typeface.BOLD);
            text.setBackgroundColor(Color.parseColor("#ffffff"));
            text.setAlpha(0.5F);
            text.setTextSize(20);
            ll.addView(text);
            ll.setOnClickListener(imgListener);
            ll.setOnLongClickListener(imgMenuListener);
            imgs.put(pi.getId().intValue(),ll);
            gl.addView(ll);
        }

        vfou.loadImgs(homedto.getPi_ids()
                .stream()
                .map(pi ->  pi.getId().intValue())
                .collect(Collectors.toList())
                );

    }
    @Override
    public void menuClick(MenuItem menuItem) { ml.menuClick(menuItem); }

    private View.OnLongClickListener imgMenuListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Log.d("home","loongclick");

            PopupMenu popup = new PopupMenu(getContext(), v);
            popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                private final int RPi_id = v.getId();
                public boolean onMenuItemClick(MenuItem item) {
                    vfou.handleHomeClickEvent(RPi_id, item.getItemId());
                    return true;
                }
            });
            popup.show();//showing popup menu
            return true; //para diferenciar del onclick
            }
    };
    private View.OnClickListener imgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(Integer id: imgs.keySet()){
                if(imgs.get(id).getId() == v.getId()){
                    //vfou.getStream(id);
                    vfou.handleHomeClickEvent(v.getId(), R.id.popup_video);
                    break;
                }
            }
        }
    };
    @Override
    public void setImg(InputStream in, Integer id) {
        imgs.get(id).setBackground(new BitmapDrawable(in));
    }
}