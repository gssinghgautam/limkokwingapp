package com.android.limkokwingapp.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by gautam on 27/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class NetworkUtils {

    /**
     * Get the network info
     *
     * @param context Context of Activity of Fragment
     * @return NetworkInfo if connected to any network
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            return cm.getActiveNetworkInfo();
        }
        return null;
    }


    /**
     * Check if there is any connectivity
     *
     * @param context Context of Activity of Fragment
     * @return true if connected to any network
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

}
