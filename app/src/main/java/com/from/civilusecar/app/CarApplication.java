package com.from.civilusecar.app;


import android.os.Build;
import android.os.StrictMode;

import com.from.civilusecar.control.BisManagerHandle;
import com.from.civilusecar.data.DataManager;
import com.from.civilusecar.serivce.PicassoImageLoader;
import com.from.civilusecar.taibite.SecretProtocolAdapter;
import com.orhanobut.logger.Logger;

import com.tbit.tbitblesdk.Bike.TbitBle;
import com.xslong.xslonglib.base.app.BaseApplication;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * @author xslong
 * @desc ${TODD}
 */
public class CarApplication extends BaseApplication {
    private static CarApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        //app全局配置(包括第三方SDK、Log是否打印等)
        Config.init(this);
        mInstance=this;
        BisManagerHandle.getInstance().setDataManager(new DataManager(this));
        //蓝牙初始化
        // isMainProcess();
        TbitBle.initialize(this, new SecretProtocolAdapter());
        //处理全局缓存数据（用户信息、服务器配置表等）
//        Global.init(this);
//        initAppStatusListener();
        //配置imageloader
        ImageLoader imageloader = new PicassoImageLoader();

        //图片选择及加载库
        //设置主题
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder().build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true).build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
                .setFunctionConfig(functionConfig).build();
        GalleryFinal.init(coreConfig);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();

        }

    }
    public static CarApplication getInstance() {
        return mInstance;
    }

//    private void initAppStatusListener() {
//        ForegroundCallbacks.init(this).addListener(new ForegroundCallbacks.Listener() {
//            @Override
//            public void onBecameForeground() {
//                Logger.t("WsManager").d("应用回到前台调用重连方法");
//                WsManager.getInstance().reconnect();
//                WsListenManager.getInstance().reconnect();
//            }
//
//            @Override
//            public void onBecameBackground() {
//
//            }
//        });
//    }
}
