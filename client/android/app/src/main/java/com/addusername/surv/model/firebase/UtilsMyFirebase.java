package com.addusername.surv.model.firebase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;

public class UtilsMyFirebase {

    public static Bitmap getImg(String url){

        RestTemplate rt = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>("", new HttpHeaders());
        ResponseEntity<byte[]> response ;

        try {
            response = rt.exchange(url, HttpMethod.GET, request, byte[].class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Log.d("FCM", "getImg() ok");
                Bitmap img = BitmapFactory.decodeStream(new ByteArrayInputStream(response.getBody()));
                if(img != null){
                    return img;
                }
                Log.d("FCM","bitmap is null");
            }
            Log.d("FCM","getImg() BAD response code: "+response.getStatusCode());

        }catch (Exception e){
            Log.d("FCM","getImg() exception: "+e.getMessage());
            return null;
        }
        return null;
    }
}
