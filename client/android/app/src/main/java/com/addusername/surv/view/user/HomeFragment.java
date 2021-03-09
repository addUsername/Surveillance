package com.addusername.surv.view.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addusername.surv.R;
import com.addusername.surv.dtos.HomeDTO;
import com.addusername.surv.interfaces.ViewFragmentOpsUser;
import com.addusername.surv.view.auth.LoginFragment;
import com.addusername.surv.view.user.UserActivity;


public class HomeFragment extends Fragment {

    private ViewFragmentOpsUser vfou;
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
        Log.d("aa", "aaaaaaa");
        //Create layout
        //create and add viewcards

    }
}