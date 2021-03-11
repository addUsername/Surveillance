package com.addusername.surv.model;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class myFirebase extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            Log.d("noti","mssg received(), body: " + remoteMessage.getNotification().getBody());

        }
        if (remoteMessage.getData().size() > 0) {
            Log.d("noti","mssg received(), data size > 0 " +  remoteMessage.getData().toString());
        }
    }
}
