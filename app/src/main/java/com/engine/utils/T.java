package com.engine.utils;

import android.app.Service;
import android.os.Vibrator;
import android.widget.Toast;

import com.engine.BaseApplication;


/**
 * Toast统一管理类
 */
public class T {

    public static boolean isShow = true;

    private T() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        Vibrator mVibrator = (Vibrator) BaseApplication.getContext()
                .getSystemService(Service.VIBRATOR_SERVICE);
        mVibrator.vibrate(80);
        if (isShow && message != null && !StringUtil.isEmpty(message + ""))
            Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(int message) {
        Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (isShow && message != null && !StringUtil.isEmpty(message + ""))
            Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(int message) {
        if (isShow)
            Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        if (isShow && message != null && !StringUtil.isEmpty(message + ""))
            Toast.makeText(BaseApplication.getContext(), message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(int message, int duration) {
        Toast.makeText(BaseApplication.getContext(), message, duration).show();
    }

}