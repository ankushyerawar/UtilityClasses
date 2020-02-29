package com.example.utilityclasses.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityManagerHelper {

    public static boolean isConnected(Context context) {

        /*Add these three permissions
         * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
         * <uses-permission android:name="android.permission.INTERNET" />
         * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
         */

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) ||
                    (wifi != null && wifi.isConnectedOrConnecting());
        } else {
            return false;
        }
    }
}
