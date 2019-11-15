package com.from.civilusecar.data;

import android.content.Context;
import android.text.TextUtils;


import com.xslong.xslonglib.data.IDataManager;
import com.xslong.xslonglib.data.IPreferences;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/10.
 */

public class DataManager implements IDataManager {

    private Context context;

    /**
     * 默认
     */
    private final static String DEFAULT_FILENAME = "bee_preferences";

    private final static Map<String, IPreferences> PREFERENCES_MAP = new HashMap<String, IPreferences>();

    public DataManager(Context context) {
        this.context = context;
    }


    @Override
    public IPreferences getPreferences(String name) {
        if (TextUtils.isEmpty(name)) {
            name = DEFAULT_FILENAME;
        }
        IPreferences ip = PREFERENCES_MAP.get(name);
        if (ip == null) {
            ip = new Preferences(context.getSharedPreferences(name, Context.MODE_PRIVATE));
            PREFERENCES_MAP.put(name, ip);
        }
        return ip;
    }

    /**
     * 默认
     *
     * @return
     */
    private IPreferences getPreferences() {
        return getPreferences(DEFAULT_FILENAME);
    }

    /**
     * 保存可序列化对象
     *
     * @param key
     * @param value
     */
    @Override
    public void save(String key, Serializable value) {
        getPreferences().save(key, value);
    }

    @Override
    public void save(String key, String value) {
        getPreferences().save(key, value);
    }

    @Override
    public void save(String key, boolean value) {
        getPreferences().save(key, value);
    }

    @Override
    public void save(String key, long value) {
        getPreferences().save(key, value);
    }

    @Override
    public void save(String key, int value) {
        getPreferences().save(key, value);
    }

    @Override
    public void save(String key, float value) {
        getPreferences().save(key, value);
    }

    @Override
    public void save(String key, Set<String> value) {
        getPreferences().save(key, value);
    }

    @Override
    public <T extends Serializable> T getSerializable(String key) {
        return getPreferences().getSerializable(key);
    }

    @Override
    public String getString(String key) {
        return getPreferences().getString(key);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    @Override
    public boolean getBoolean(String key) {
        return getPreferences().getBoolean(key);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    @Override
    public long getLong(String key) {
        return getPreferences().getLong(key);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return getPreferences().getLong(key, defaultValue);
    }

    @Override
    public int getInt(String key) {
        return getPreferences().getInt(key);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    @Override
    public float getFloat(String key) {
        return getPreferences().getFloat(key);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return getPreferences().getFloat(key, defaultValue);
    }

    @Override
    public Set<String> getStringSet(String key) {
        return getPreferences().getStringSet(key);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return null;
    }

    @Override
    public void remove(String key) {
        getPreferences().remove(key);
    }

    @Override
    public void clear() {
        getPreferences().clear();
    }

    public interface Key {
        String BLUETOOTHCOMMAND = "BLUETOOTHCOMMAND";
    }

}
