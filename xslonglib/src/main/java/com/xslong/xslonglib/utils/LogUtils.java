package com.xslong.xslonglib.utils;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * 如果用于android平台，将信息记录到“LogCat”。如果用于java平台，将信息记录到“Console”
 * 使用logger封装
 */
public class LogUtils {
    public static boolean DEBUG_ENABLE =false;// 是否调试模式
    /**
     * 在application调用初始化
     */
    public static void logInit(boolean debug) {
        DEBUG_ENABLE=debug;
        if (DEBUG_ENABLE) {
            Logger.addLogAdapter(new AndroidLogAdapter());
        }
    }
    public static void d(String tag, String message) {
        if (DEBUG_ENABLE) {
            Logger.d(tag,message);
        }
    }
    public static void d(String message) {
        if (DEBUG_ENABLE) {
            Logger.d(message);
        }
    }
    public static void e(Throwable throwable, String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.e(throwable, message, args);
        }
    }

    public static void e(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.e(message, args);
        }
    }

    public static void i(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.i(message, args);
        }
    }
    public static void v(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.v(message, args);
        }
    }
    public static void w(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.v(message, args);
        }
    }
    public static void wtf(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.wtf(message, args);
        }
    }

    public static void json(String message) {
        if (DEBUG_ENABLE) {
            Logger.json(message);
        }
    }
    public static void xml(String message) {
        if (DEBUG_ENABLE) {
            Logger.xml(message);
        }
    }
}
