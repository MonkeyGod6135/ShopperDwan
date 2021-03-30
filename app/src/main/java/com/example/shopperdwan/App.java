package com.example.shopperdwan;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

/**
 *  This class creates a notifican channel for shopper. Notification channels become necessary
 *  starting with andriod oreo(Api )
 */
public class App extends Application {

    //declare and start a channel id
    public static final String CHANNEL_SHOPPER_ID = "channelshopper";

    //override the onCreate method

    @Override
    public void onCreate() {
        super.onCreate();

        //call method that creates notif channel for shopper
        createNotificationChannel();
    }

    public void createNotificationChannel(){
        //check andriod oreo
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channelshopper = new NotificationChannel(
                    CHANNEL_SHOPPER_ID,
                    "Channel Shopper",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            //customize notif manager
            channelshopper.setDescription("This is a shopper Channel");

            //start a notification manager
            NotificationManager manager = getSystemService(NotificationManager.class);

            //create shopper notif
            manager.createNotificationChannel(channelshopper);
        }
    }
}
