package com.addusername.surv.model.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.addusername.surv.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class myFirebase extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            Log.d("noti","mssg received(), body: " + remoteMessage.getNotification().getBody());
        }
        String rpiId = remoteMessage.getData().get("rpiId") ;
        String extension = remoteMessage.getData().get("extension") ;
        String body = remoteMessage.getData().get("body") ;
        String title = remoteMessage.getData().get("title") ;
        String image = remoteMessage.getData().get("image") ;

        Intent intent = null;
        switch (remoteMessage.getData().get("action")){
            case "STREAM":
                Log.d("noti","STREAM");
                intent = new Intent("com.addusername.surv.STREAM");
                intent.putExtra("rpiId", rpiId);
                intent.putExtra("extension", extension);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case "INFO":
                break;
        }
        Log.d("noti","Pedning intent");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String id = "_channel_01";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(id, "notification", importance);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,id);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);

        if(image !=null){
            Bitmap img = UtilsMyFirebase.getImg(image);
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(img));
        }

        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(mChannel);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
