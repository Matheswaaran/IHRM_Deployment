package com.example.allu.hrm_superviser.Services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by allu on 3/26/17.
 */

public class CloudSync extends FirebaseMessagingService {
    private static final String TAG = CloudSync.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG,"message received from"+remoteMessage.getFrom());

        if(remoteMessage == null){
            return;
        }

        if(remoteMessage.getData().size() > 0){
            Log.e(TAG,"Data received :"+remoteMessage.getData().toString());
        }

    }
}
