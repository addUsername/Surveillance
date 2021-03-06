package com.addusername.surv.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.addusername.surv.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    private static RegisterFragment rf;
    private EditText username;
    private EditText email;
    private EditText pass;
    private EditText pass2;
    private EditText pin;


    public RegisterFragment() {
        // Required empty public constructor
    }
    public static RegisterFragment newInstance() {

        if(rf == null) rf = new RegisterFragment();
        return rf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.editTextTextPersonName);
        email = view.findViewById(R.id.editTextTextEmailAddress);
        pass = view.findViewById(R.id.editTextTextPassword);
        pass2 = view.findViewById(R.id.editTextTextPassword2);
        pin = view.findViewById(R.id.editTextNumberPassword);
    }
    private View.OnClickListener numButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}