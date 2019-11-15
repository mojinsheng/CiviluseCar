package com.xslong.xslonglib.base.app;

import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * APPLICATION
 *
 * @author xslong
 */
public class BaseApplication extends MultiDexApplication {

    private static BaseApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = BaseApplication.this;

    }

    public static Context getAppContext() {
        if (baseApplication == null) {
            throw new RuntimeException("Application context is null. Maybe you configured your application name in your AndroidManifest.xml");
        } else {
            return baseApplication;
        }
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }

    /**
     * 分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
