package com.example.allu.hrm_superviser.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.allu.hrm_superviser.utils.Utils;


/**
 * Created by allu on 3/18/17.
 */

public class InternetReceiver extends BroadcastReceiver {
    Utils utils;
    @Override
    public void onReceive(Context context, Intent intent) {
        utils = new Utils(context);
        utils.Toast("Internet connected..");
    }
}
