package com.addusername.surv.model.auth;

import com.addusername.surv.dtos.RegisterForm;

import java.util.ArrayList;
import java.util.List;

public class RegisterFormValidator {

    public static List<String> getErrors(RegisterForm rf){
        List<String> list = new ArrayList<>();
        String error;
        if(rf.getUsername().length() < 2){
            error = "Username has to be +2 char long";
            if(rf.getUsername().isEmpty()){
                error = "NOTSHOW";
            }
            list.add(error);
        }
        if(!validateEmail(rf.getEmail())){
            error = "Email not correct";
            if(rf.getEmail().length() < 1){
                error = "NOTSHOW";
            }
            list.add(error);
        }
        if(!validatePass(rf.getPass())){
            error = "Password must contain: pass must contain: lowercase, uppercase, number and special char";
            if(rf.getPass().length() < 1){
                error = "NOTSHOW";
            }
            list.add(error);
        }
        if(!validatePass(rf.getPass2())){
            error = "Password2 must contain: pass must contain: lowercase, uppercase, number and special char";
            if(rf.getPass2().length() < 1){
                error = "NOTSHOW";
            }
            list.add(error);
        }
        if(!rf.getPass().equals(rf.getPass2())){
            list.add("Passwords didn`t match");
        }
        if( 10000 > rf.getPin() || 99999 < rf.getPin() ){
            error = "Pin must have 5 digits";
            if(rf.getPin() == 0){
                error = "NOTSHOW";
            }
            list.add(error);
        }
        return list;
    }

    private static boolean validatePass(String pass) {
        return(pass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{6,15}$"));
    }

    private static boolean validateEmail(String email) {
        return email.matches("^([a-z]|[A-Z]){1,100}@([a-z]|[A-Z]){1,100}\\.([a-z]|[A-Z]){1,10}");
    }
}
