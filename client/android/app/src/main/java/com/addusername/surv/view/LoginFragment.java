package com.addusername.surv.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.addusername.surv.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    private String pin = "";
    private TextView text;
    private Button[] numButtons;
    private static LoginFragment lf;

    public LoginFragment() {
        // Required empty public constructor
    }
    public static LoginFragment newInstance() {
        if(lf == null){
            lf = new LoginFragment();
        }
        return lf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text = view.findViewById(R.id.loginInput);
        numButtons = new Button[10];
        numButtons[0] = view.findViewById(R.id.zero);
        numButtons[1] = view.findViewById(R.id.one);
        numButtons[2] = view.findViewById(R.id.two);
        numButtons[3] = view.findViewById(R.id.three);
        numButtons[4] = view.findViewById(R.id.four);
        numButtons[5] = view.findViewById(R.id.five);
        numButtons[6] = view.findViewById(R.id.six);
        numButtons[7] = view.findViewById(R.id.seven);
        numButtons[8] = view.findViewById(R.id.eight);
        numButtons[9] = view.findViewById(R.id.nine);
        for(Button b: numButtons){
            b.setOnClickListener(numButtonsListener);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    private View.OnClickListener numButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            pin += b.getText().toString();
            writeText();
            if (pin.length() == 4) {
                ((MainActivity) getActivity()).login(pin);
                pin = "";
            }
        }
    };

    private void writeText() {
        String t = "";
        for(int i = 0; i < pin.length(); i++){
            t += " * ";
        }
        text.setText(t);
    }
}