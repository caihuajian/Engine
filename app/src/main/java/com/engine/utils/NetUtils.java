package com.engine.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.engine.BaseApplication;

/**
 * 跟网络相关的工具类
 */
public class NetUtils {

    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: doNetType
     * @Description: 获取联网方式
     * @date 2014年12月4日 下午6:10:52
     */
    public static String doNetType() {
        String typeName = "";
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null)
            typeName += info.getTypeName(); // cmwap/cmnet/wifi/uniwap/uninet
        return typeName;
    }

    /**
     * @return void 返回类型
     * @throws
     * @Title: doNetService
     * @Description: 获取当前运营商
     * @date 2014年12月4日 下午6:17:00
     */
    public static String doNetService() {
        TelephonyManager telManager = (TelephonyManager) BaseApplication.getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telManager.getSimOperator();
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002")
                    || operator.equals("46007")) {
                // 中国移动
                return "中国移动";
            } else if (operator.equals("46001")) {
                // 中国联通
                return "中国联通";
            } else if (operator.equals("46003")) {
                // 中国电信
                return "中国电信";
            } else {
                return "无sim卡";
            }
        } else {
            return "无sim卡";
        }
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }


    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 判断目标主机IP是否能调通
     *
     * @param ipAddress
     * @return
     */
    public static boolean pingIp(String ipAddress) {
        int status = -1;
        try {
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 1 " + ipAddress);// ping网址3次
            // ping的状态
            status = p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status == 0;
    }

}
