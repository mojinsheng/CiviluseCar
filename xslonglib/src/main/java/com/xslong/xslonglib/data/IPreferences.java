package com.xslong.xslonglib.data;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/11.
 */

public interface IPreferences extends Serializable {

    /**
     * 保存可序列化对象
     *
     * @param key
     * @param value
     */
    void save(String key, Serializable value);

    void save(String key, String value);

    void save(String key, boolean value);

    void save(String key, long value);

    void save(String key, int value);

    void save(String key, float value);

    void save(String key, Set<String> value);

    /**
     * 取出可序列化对象
     *
     * @param key
     * @param <T>
     * @return
     */
    <T extends Serializable> T getSerializable(String key);
    String getString(String key);


    String getString(String key, String defaultValue);

    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean defaultValue);

    long getLong(String key);

    long getLong(String key, long defaultValue);

    int getInt(String key);

    int getInt(String key, int defaultValue);

    float getFloat(String key);

    float getFloat(String key, float defaultValue);

    Set<String> getStringSet(String key);

    Set<String> getStringSet(String key, Set<String> defaultValue);

    void remove(String key);

    void clear();
}
