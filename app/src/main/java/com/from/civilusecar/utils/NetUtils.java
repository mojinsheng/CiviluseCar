package com.from.civilusecar.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具类
 * 
 * @author huangzhongwen
 * 
 */
public class NetUtils {

    /**
     * 网络类型
     * 
     * @author huangzhongwen
     * 
     */
    public enum NetType {
        /**
         * 无网络
         */
        NONE,

        /**
         * WIFI网络
         */
        WIFI,

        /**
         * 其他网络
         */
        OTHER;
    }

    private NetUtils() {
    }

    /**
     * WIFI是否可用
     * 
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 网络是否可用
     * 
     * @param context
     * @return
     */
    public static boolean isNetConnected(Context context) {
        if (context != null) {
            return getConnectedType(context) != NetType.NONE;
        }
        return false;
    }

    /**
     * 获取网络类型
     * 
     * @param context
     * @return
     */
    public static NetType getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                switch (mNetworkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return NetType.WIFI;
                default:
                    return NetType.OTHER;
                }
            }
        }
        return NetType.NONE;
    }

    /**
     * 打开网络设置
     * 
     * @param context
     */
    public static void openNetSetting(Context context) {
        Intent intent = null;
        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
        if (android.os.Build.VERSION.SDK_INT > 10) {
            intent = new Intent(
                    android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings",
                    "com.android.settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        context.startActivity(intent);
    }

}
