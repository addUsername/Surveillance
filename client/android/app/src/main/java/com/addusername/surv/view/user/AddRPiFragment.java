package com.addusername.surv.view.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.addusername.surv.R;
import com.addusername.surv.interfaces.ViewFragmentOpsUser;
import com.addusername.surv.view.auth.LoginFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddRPiFragment extends Fragment {

    public static AddRPiFragment af;
    private ViewFragmentOpsUser vfou;
    private EditText alias;
    private EditText location;
    private FloatingActionButton button;

    public AddRPiFragment() {}
    public static AddRPiFragment newInstance() {
        if(af == null){
            af = new AddRPiFragment();
        }
        return af;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_r_pi, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vfou  = (UserActivity) getActivity();
        alias = view.findViewById(R.id.alias);
        location = view.findViewById(R.id.location);
        alias.setOnFocusChangeListener(listener);
        location.setOnFocusChangeListener(listener);
        button = view.findViewById(R.id.add_button);
    }
    private View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(alias.getText() != null && location.getText() != null){
                button.setEnabled(true);
            }
        }
    };
    public void onClick(View view){
        vfou.addRpi(alias.getText().toString(), location.getText().toString());
    }
}