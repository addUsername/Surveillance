package com.addusername.surv.view.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.addusername.surv.R;
import com.addusername.surv.interfaces.MenuListener;
import com.addusername.surv.interfaces.ViewFragmentOpsUser;


public class LogoUserFragment extends Fragment implements MenuListener {

    private MenuListener ml;
    private static LogoUserFragment luf;

    public LogoUserFragment() {}
    public static LogoUserFragment newInstance() {
        if(luf == null){
            luf = new LogoUserFragment();
        }
        return luf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logo_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ml  = (UserActivity) getActivity();
    }
    @Override
    public void menuClick(MenuItem menuItem) { ml.menuClick(menuItem); }
}