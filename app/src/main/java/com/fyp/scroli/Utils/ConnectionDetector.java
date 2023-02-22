package com.fyp.scroli.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class ConnectionDetector {

    private final Activity activity;

    public ConnectionDetector(Activity activity) {
        this.activity = activity;
    }

    public boolean hasInternetConnection() {
        ConnectivityManager connectivity =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkCapabilities capabilities;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            // Check wi-fi or mobile data first
//            NetworkInfo activeNetworkInfo = connectivity.getActiveNetworkInfo();
//            if (!activeNetworkInfo.isConnected()) {
//                return false;
//            }

            Network network = connectivity.getActiveNetwork();
            capabilities = connectivity
                    .getNetworkCapabilities(network);
            return capabilities != null
                    && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        }
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
//            Network[] networks = connectivity.getAllNetworks();
//            NetworkInfo networkInfo;
//            for (Network mNetwork : networks) {
//                networkInfo = connectivity.getNetworkInfo(mNetwork);
//                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
//                    return true;
//                }
//            }
//        }
        else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}