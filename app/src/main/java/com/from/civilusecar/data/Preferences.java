package com.from.civilusecar.data;

import android.content.SharedPreferences;
import android.util.Base64;


import com.xslong.xslonglib.data.IPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/11.
 */

public class Preferences implements IPreferences {

    private SharedPreferences sharedPreferences;

    public Preferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * 保存可序列化对象
     *
     * @param key
     * @param value
     */
    @Override
    public void save(String key, Serializable value) {
        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            //然后通过将字对象进行64转码，写入key值为key的sp中
            out = new ObjectOutputStream(baos);
            out.writeObject(value);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, objectVal);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public void save(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public void save(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    @Override
    public void save(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @Override
    public void save(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    @Override
    public void save(String key, Set<String> value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }

    /**
     * 取出可序列化对象
     *
     * @param key
     * @return
     */
    @Override
    public <T extends Serializable> T getSerializable(String key) {
        if (sharedPreferences.contains(key)) {
            String objectVal = sharedPreferences.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public String getString(String key) {
        return getString(key, "");
    }

    @Override
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public long getLong(String key) {
        return getLong(key, 0);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public int getInt(String key) {
        return getInt(key, 0);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public Set<String> getStringSet(String key) {
        return getStringSet(key, new HashSet<String>());
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return sharedPreferences.getStringSet(key, defaultValue);
    }

    @Override
    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
