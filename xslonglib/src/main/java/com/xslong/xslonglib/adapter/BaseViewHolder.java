package com.xslong.xslonglib.adapter;

import android.view.View;

import java.util.HashMap;
import java.util.Objects;

/**
 * ViewHolder基类
 */

public class BaseViewHolder {
    HashMap<Integer, View> mapView = new HashMap<Integer, View>();
    HashMap<String, Objects> mapData = new HashMap<String, Objects>();

    public void setView(int key, View v) {
        this.mapView.put(key, v);
    }

    public <T> T getView(int key) {
        return (T)this.mapView.get(key);
    }

    public <T> T getView(Class<T> clazz, int key) {
        return (T)this.mapView.get(key);
    }

    public void setData(String key, Objects value) {
        mapData.put(key, value);
    }

    public <T> T getData(String key) {
        return (T)mapData.get(key);
    }

    public <T> T getData(Class<T> clazz, String key) {
        return (T)mapData.get(key);
    }


}
