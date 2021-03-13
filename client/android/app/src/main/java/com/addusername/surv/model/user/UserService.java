package com.addusername.surv.model.user;

import android.util.Log;

import com.addusername.surv.dtos.HomeDTO;
import com.addusername.surv.dtos.PiDTO;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class UserService {

    private final RestTemplate rt;
    private final String HOST;
    private final String PATH;
    private final String FILESQL = "dump2.sql"; //TODO DUPLICATE ATTRIBUTE CHANGE THS TO BE ALWAYS EQUALS TO AuthService.FILESQL
    private final String MOCKIMG = "res/raw/mock.jpg";
    private final String INDEX = "res/raw/index.html";
    private final HttpHeaders headers;
    private final String token;

    public UserService(String token, String host, String absolutePath){
        HOST = host;
        headers = new HttpHeaders();
        headers.set("Authorization","bearer "+token);
        this.token=token;
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
        HttpHeaders custom = headers;
        custom.setContentType(MediaType.APPLICATION_JSON);;
        HttpEntity<String> request = new HttpEntity<>(body.toString(), custom);
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

    public InputStream getImg(Integer i) {

        HttpEntity<String> request = new HttpEntity<>("", headers);
        ResponseEntity<byte[]> response ;

        try {
            response = rt.exchange(HOST + "/user/img/"+i, HttpMethod.GET, request, byte[].class);

            if (response.getStatusCode().is2xxSuccessful()){
                Log.d("user","getImg() ok");
                return new ByteArrayInputStream(response.getBody());
            }else{
                Log.d("user","getImg() BAD response code: "+response.getStatusCode());
            }
        }catch (Exception e){
            Log.d("user","getImg() exception: "+e.getMessage());
            return null;
        }
        return null;
    }

    public InputStream getImgMock() {
        Log.d("user","getImgMock() getting img mock");
        return this.getClass().getClassLoader().getResourceAsStream(MOCKIMG);
    }
    public String takeStream(int rpiId) {
        HttpEntity<String> request = new HttpEntity<>("", headers);
        ResponseEntity<String> response ;

        try {
            response = rt.exchange(HOST + "/user/upload/h264/"+rpiId, HttpMethod.GET, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()){

                Log.d("user","stream() ok");
                return parseHtml(rpiId);
            }else{
                Log.d("user","stream() BAD response code: "+response.getStatusCode());
            }
        }catch (Exception e){
            Log.d("user","stream() exception: "+e.getMessage());
        }
        return null;
    }

    private String parseHtml(int rpiId) {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream(INDEX);
        String text = new BufferedReader( new InputStreamReader(in, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        text = text.replace("###TOKEN###", "bearer "+token);
        text = text.replace("###URL###",HOST+"/temp/stream/"+rpiId);
        Log.d("html",text.substring(text.length()-1000));
        return text;
    }

    public InputStream takeScreenShoot(int rpiId) {
        HttpEntity<String> request = new HttpEntity<>("", headers);
        ResponseEntity<String> response ;

        try {
            response = rt.exchange(HOST + "/user/upload/jpg/"+rpiId, HttpMethod.GET, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()){
                Log.d("user","screennshot() ok");
                return waitForResourceToBeReady(rpiId);
            }else{
                Log.d("user","screenshot() BAD response code: "+response.getStatusCode());
            }
        }catch (Exception e){
            Log.d("user","screenshot() exception: "+e.getMessage());
            return null;
        }
        return null;
    }

    private InputStream waitForResourceToBeReady(int rpiId){
        HttpEntity<String> request = new HttpEntity<>("", headers);
        ResponseEntity<byte[]> response ;

        for(int i = 0; i<10; i++){

            try {
                Log.d("user","waitFor() sleeping");
                Thread.sleep(1000);
                response = rt.exchange(HOST + "/user/download/"+rpiId, HttpMethod.GET, request, byte[].class);

                if (response.getStatusCode().is2xxSuccessful()){
                    Log.d("user","waitForResourceToBeReady() ok");
                    return new ByteArrayInputStream(response.getBody());
                }else{
                    Log.d("user","waitForResourceToBeReady() BAD response code: "+response.getStatusCode());
                }
            }catch (Exception e){
                Log.d("user","waitForResourceToBeReady() exception: "+e.getMessage());
            }
        }
        return null;
    }
}
