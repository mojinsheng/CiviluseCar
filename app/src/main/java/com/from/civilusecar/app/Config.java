package com.from.civilusecar.app;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.xslong.xslonglib.utils.LogUtils;

/**
 * Desc : app全局配置
 */

public class Config {

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    public static void init(Application app) {
        //Initialize screen size
        getScreenSize(app);
        //        //Initialize logger
        LogUtils.logInit(true);

//        Bugly.init(app.getApplicationContext(), "4363f69075", false);//正式
//        //        //初始化Bugly之前通过以下接口把调试设备设置成“开发设备”。
//        CrashReport.setIsDevelopmentDevice(app.getApplicationContext(), BuildConfig.DEBUG);
    }

    private static void getScreenSize(Application app) {
        WindowManager windowManager = (WindowManager) app.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }
}
