package com.addusername.surv.view.user;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addusername.surv.R;
import com.addusername.surv.dtos.HomeDTO;
import com.addusername.surv.dtos.HomePiDTO;
import com.addusername.surv.interfaces.MenuListener;
import com.addusername.surv.interfaces.ViewFragmentOpsUser;

public class HomeFragment extends Fragment implements MenuListener {

    private ViewFragmentOpsUser vfou;
    private MenuListener ml;
    private final HomeDTO homedto;
    public HomeFragment(HomeDTO home) {
        homedto = home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        for(HomePiDTO pi: homedto.getPi_ids()){
            LinearLayout ll = new LinearLayout(this.getContext());
            ll.setPaddingRelative(10,20,10,20);
            ll.setLayoutParams(params);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setClickable(true);
            ll.setElevation(5);
            ll.setBackgroundColor(Color.parseColor("#7dbfd4"));

            TextView text = new TextView(this.getContext());
            text.setText(pi.getAlias());
            text.setPaddingRelative(10,5,10,5);
            text.setTextSize(20);
            ll.addView(text);
            TextView text2 = new TextView(this.getContext());
            text2.setText(pi.getStatus());
            text2.setPaddingRelative(10,5,10,5);
            text2.setTextSize(20);

            ll.addView(text2);
            gl.addView(ll);
        }
        //create and add viewcards

    }
    @Override
    public void menuClick(MenuItem menuItem) { ml.menuClick(menuItem); }
}