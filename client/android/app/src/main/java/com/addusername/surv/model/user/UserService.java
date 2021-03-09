package com.addusername.surv.model.user;

import com.addusername.surv.dtos.HomeDTO;
import com.addusername.surv.dtos.PiDTO;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

public class UserService {

    private final RestTemplate rt;
    private final String HOST;
    private final HttpHeaders headers;
    private String TOKEN;

    public UserService(String token, String host){
        HOST = host;
        headers = new HttpHeaders();
        headers.set("Authorization","bearer "+token);

        rt = new RestTemplate();
    }
    public HomeDTO doHome() {

        HttpEntity<String> request = new HttpEntity<>("", headers);
        ResponseEntity<HomeDTO> response = rt.exchange(HOST+"/user/home", HttpMethod.GET, request, HomeDTO.class);
        return response.getBody();
    }

    public boolean doAddRpi(PiDTO piDTO) {

        JSONObject body = new JSONObject();
        for (Field field : piDTO.getClass().getDeclaredFields()) {
            try {
                body.put(field.getName(), field.get(piDTO));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<String> response = rt.exchange(HOST+"/user/setRpi", HttpMethod.POST, request, String.class);
        return(response.getStatusCode().is2xxSuccessful());
    }
}
