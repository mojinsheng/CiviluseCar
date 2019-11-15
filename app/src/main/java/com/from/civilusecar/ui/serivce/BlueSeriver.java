package com.from.civilusecar.ui.serivce;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class BlueSeriver extends IntentService {
    public BlueSeriver() {
        super("BlueSeriver");
    }

    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        timer = new Timer();//创建timer对象
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


            }
        }, 0, 2 * 1000);//4000表示开始时4秒后发送一次，10*10000表示美10秒后发送一次
    }


}
