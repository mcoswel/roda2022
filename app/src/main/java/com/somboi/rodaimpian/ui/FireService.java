package com.somboi.rodaimpian.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.somboi.rodaimpian.AndroidLauncherNew;
import com.somboi.rodaimpian.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class FireService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        shownotification(remoteMessage.getNotification());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }


    public void shownotification(RemoteMessage.Notification message){

        Intent intent = new Intent(this, AndroidLauncherNew.class);
        intent.putExtra("invitation","invitation");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.somboi.rodaimpian"; //your app package name

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Notifikasi dari Roda Impian Retro");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Bitmap image = getBitmapfromUrl("https://firebasestorage.googleapis.com/v0/b/roda-impian-acc41.appspot.com/o/avatar_lelex.png?alt=media&token=83d0bf7f-454b-49b7-a102-f030ab2a13df");
        if (message.getImageUrl()!=null) {
             image = getBitmapfromUrl(message.getImageUrl().toString());
        }
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(message.getTitle())
                .setContentText(message.getBody())
                .setLargeIcon(image)
                .setContentIntent(pendingIntent)
                .setContentInfo("Roda Impian Retro Malaysia");

        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());

    }

    private Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
