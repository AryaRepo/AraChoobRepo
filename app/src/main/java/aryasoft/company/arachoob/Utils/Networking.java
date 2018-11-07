package aryasoft.company.arachoob.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Networking {

    public static void checkNetwork(Context context, NetworkStatusListener networkStatusListener, int requestCode) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean status = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (status)
            networkStatusListener.onNetworkConnected(requestCode);
        else
            networkStatusListener.onNetworkDisconnected();
    }

    public interface NetworkStatusListener {
        void onNetworkConnected(int requestCode);

        void onNetworkDisconnected();
    }

}
