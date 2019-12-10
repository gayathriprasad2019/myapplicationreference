package com.example.myapplication.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;
import com.example.myapplication.activity.LoginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static com.example.myapplication.R.drawable.ic_assistant_photo_black_24dp;

public class FCMPushNotificationService extends FirebaseMessagingService {
    private static final String TAG="FCMPushNotificationSrv";
    private static final String CHANNEL_ID="101";

    @Override
    public void onNewToken(String token){
        super.onNewToken(token);
        Log.d(TAG,"New Token:"+token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,"Message Data Payload:"+remoteMessage.getData());

        //STEP 1: Prepare and Show Notification
        //STEP2: Update Android View Model (Contains the entire JSON as a single string)

        if (remoteMessage.getData()!=null){
            Map<String,String> data = remoteMessage.getData();
            showNotification(data);
           // update(data);

        }
    }
    private void showNotification(Map<String,String> data){
        Intent intent=new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK/Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder=
                new NotificationCompat.Builder(this,CHANNEL_ID)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))
                .setSmallIcon(ic_assistant_photo_black_24dp)
                .setContentTitle(data.get("contentTitle"))
                .setContentText(data.get("contentText"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManager notificationManager=
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager !=null){
            createNotificationChannel(notificationManager);
            notificationManager.notify(0,notificationBuilder.build());
        }
    }
//Create NotificationChannel --for API26 and above
   // @param notificationManager NotificationManager Object

    private void createNotificationChannel (NotificationManager notificationManager){
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,
                    "Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("MPOB Notifications");
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    //Update the Status(Enabled/Disabled), for a single camera/all cameras in JsonDataViewModel
    //@param data Data PayLoad from RemoteMessage
}
