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
import com.addusername.surv.interfaces.ViewFragmentOps;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    private static RegisterFragment rf;
    private ViewFragmentOps vfo;
    private HashMap<String, EditText> components;
    private Button button;


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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vfo = ((MainActivity) getActivity());

        components = new HashMap<>();
        components.put("username",view.findViewById(R.id.editTextTextPersonName));
        components.put("email", view.findViewById(R.id.editTextTextEmailAddress));
        components.put("pass", view.findViewById(R.id.editTextTextPassword));
        components.put("pass2", view.findViewById(R.id.editTextTextPassword2));
        components.put("pin", view.findViewById(R.id.editTextNumberPassword));
        for(EditText input: components.values()){
            input.setOnFocusChangeListener(listener);
        }
        button = view.findViewById(R.id.buttonRegister);
        button.setOnClickListener(numButtonsListener);
        button.setEnabled(false);
    }
    private View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(vfo.validateComponents(components)){
                button.setEnabled(true);
            }
        }
    };
    private View.OnClickListener numButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            vfo.register(components);
        }
    };
}