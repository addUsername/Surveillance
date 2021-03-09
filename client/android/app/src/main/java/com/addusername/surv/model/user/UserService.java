package com.addusername.surv.model.user;

import android.util.Log;

import com.addusername.surv.dtos.HomeDTO;
import com.addusername.surv.dtos.PiDTO;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.lang.reflect.Field;

public class UserService {

    private final RestTemplate rt;
    private final String HOST;
    private final String PATH;
    private final String FILESQL = "dump2.sql"; //TODO DUPLICATE ATTRIBUTE CHANGE THS TO BE ALWAYS EQUALS TO AuthService.FILESQL
    private final HttpHeaders headers;

    public UserService(String token, String host, String absolutePath){
        HOST = host;
        headers = new HttpHeaders();
        headers.set("Authorization","bearer "+token);
        PATH = absolutePath;

        rt = new RestTemplate();
    }
    public HomeDTO doHome() {

        HttpEntity<String> request = new HttpEntity<>("", headers);
        ResponseEntity<HomeDTO> response = rt.exchange(HOST+"/user/home", HttpMethod.GET, request, HomeDTO.class);
        Log.d("user","doHome() response code: "+response.getStatusCode());
        return response.getBody();
    }

    public boolean doAddRpi(PiDTO piDTO) {
    // todo looks ugly

        JSONObject body = new JSONObject();
        for (Field field : piDTO.getClass().getDeclaredFields()) {
            try {
                body.put(field.getName(), field.get(piDTO));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("user","doAddpi() PIDTO: "+body.toString());
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<String> response ;

        try {
            response = rt.exchange(HOST + "/user/setRpi", HttpMethod.POST, request, String.class);
            Log.d("user","doAddpi() response code: "+response.getStatusCode());
            return(response.getStatusCode().is2xxSuccessful());
        }catch (Exception e){
            Log.d("user","doAddpi() exception: "+e.getMessage());
            return false;
        }

    }

    public void doDump() {

        HttpEntity<String> request = new HttpEntity<>("", headers);
        ResponseEntity<byte[]> response ;

        try {
            response = rt.exchange(HOST + "/user/dump", HttpMethod.POST, request, byte[].class);

            if (response.getStatusCode().is2xxSuccessful()){
                Log.d("user","doDump() ok");
                FileOutputStream fos = new FileOutputStream(PATH +"/"+ FILESQL);
                fos.write(response.getBody());
                fos.close();
            }else{
                Log.d("user","doDump() BAD response code: "+response.getStatusCode());
            }
        }catch (Exception e){
                Log.d("user","doDump() exception: "+e.getMessage());
        }
    }
}
