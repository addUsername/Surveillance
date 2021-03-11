package com.addusername.surv.dtos;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class LoginForm {
    private Integer pin;
    private String token;

    public LoginForm(Integer pin){
        this.pin = pin;
    }
    public Integer getPin() {
        return pin;
    }
    public void setToken(String token){ this.token = token;}
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
