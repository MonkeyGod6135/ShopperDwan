package com.example.shopperdwan

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

/**
 * This class creates a notifican channel for shopper. Notification channels become necessary
 * starting with andriod oreo(Api )
 */
class App : Application() {
    //override the onCreate method
    override fun onCreate() {
        super.onCreate()

        //call method that creates notif channel for shopper
        createNotificationChannel()
    }

    fun createNotificationChannel() {
        //check andriod oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelshopper = NotificationChannel(
                    CHANNEL_SHOPPER_ID,
                    "Channel Shopper",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            //customize notif manager
            channelshopper.description = "This is a shopper Channel"

            //start a notification manager
            val manager = getSystemService(NotificationManager::class.java)

            //create shopper notif
            manager.createNotificationChannel(channelshopper)
        }
    }

    companion object {
        //declare and start a channel id
        const val CHANNEL_SHOPPER_ID = "channelshopper"
    }
}