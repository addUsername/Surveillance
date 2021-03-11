package com.addusername.surv.dtos;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class RegisterForm {

    public String username;
    public String pass;
    public String pass2;
    public String email;
    public Integer pin;

    public RegisterForm(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }
    public String toJsonString(){

        JSONObject body = new JSONObject();
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                body.put(field.getName(), field.get(this));
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return body.toString();
    }
}
