package com.from.civilusecar.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.from.civilusecar.constant.Constant;


public class SPUtils {
    public static boolean getBoolean(Context context,String key,boolean defValue){
        SharedPreferences sp = getSp(context);
        return sp.getBoolean(key,defValue);
    }

    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences sp = getSp(context);
        //添加保存数据
        sp.edit().putBoolean(key,value).commit();
    }

    public static String getString(Context context,String key){
        SharedPreferences sp = getSp(context);
        return sp.getString(key,null);
    }

    public static void putString(Context context,String key,String value){
        SharedPreferences sp = getSp(context);
        //添加保存数据
        sp.edit().putString(key,value).commit();
    }

    public static  void putInt(Context context,String key,int value){
        SharedPreferences sp = getSp(context);
        sp.edit().putInt(key,value).commit();
    }

    public static  void putLong(Context context,String key,long value){
        SharedPreferences sp = getSp(context);
        sp.edit().putLong(key,value).commit();
    }

    public static long getLong(Context context,String key){
        SharedPreferences sp = getSp(context);
        return sp.getLong(key,0);
    }

    public static int getInt(Context context,String key){
        SharedPreferences sp = getSp(context);
        return sp.getInt(key,0);
    }

    private static SharedPreferences getSp(Context context){
        return context.getSharedPreferences(Constant.SPFILENAME, Context.MODE_PRIVATE);
    }
}
