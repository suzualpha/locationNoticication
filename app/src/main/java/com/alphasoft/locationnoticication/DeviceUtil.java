package com.alphasoft.locationnoticication;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class DeviceUtil {

    public static String getConnectedSSID(Context context){
        // WIFI
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        WifiInfo w_info = wifiManager.getConnectionInfo();
//        Log.i("Sample", "SSID:" + w_info.getSSID());
//        Log.i("Sample", "BSSID:" + w_info.getBSSID());
//        Log.i("Sample", "IP Address:" + w_info.getIpAddress());
//        Log.i("Sample", "Mac Address:" + w_info.getMacAddress());
//        Log.i("Sample", "Network ID:" + w_info.getNetworkId());
//        Log.i("Sample", "Link Speed:" + w_info.getLinkSpeed());
        int ip_addr_i = w_info.getIpAddress();
        String ip_addr = ((ip_addr_i >> 0) & 0xFF) + "." + ((ip_addr_i >> 8) & 0xFF) + "." + ((ip_addr_i >> 16) & 0xFF) + "." + ((ip_addr_i >> 24) & 0xFF);
//        Log.i("Sample", "IP Address:" + ip_addr);


        return w_info.getSSID();
    }
}
