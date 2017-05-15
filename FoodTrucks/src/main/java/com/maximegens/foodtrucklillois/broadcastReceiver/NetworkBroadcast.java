package com.maximegens.foodtrucklillois.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast Receiver détectant l'activation ou la désactivation d'une connexion internet.
 */
public class NetworkBroadcast extends BroadcastReceiver {

    public static final String INTERNET_DETECTED = "internetdetected";

    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent(INTERNET_DETECTED));
    }
}
