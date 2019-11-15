package com.xslong.xslonglib.base.rx;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Desc : 管理订阅
 * Created by huangyue on 2017/10/9.
 */

public class RxManager {
    private static final RxManager ourInstance = new RxManager();
   public static CompositeDisposable disposables = new CompositeDisposable();

    private Map<String, CompositeDisposable> map;

    public static RxManager getInstance() {

        return ourInstance;
    }

    private RxManager() {
        map = new HashMap<>();
    }

    public void add(String key, Disposable disposable) {
        Set<String> keySet = map.keySet();
        if (keySet.contains(key)) {
            CompositeDisposable compositeDisposable = map.get(key);
            compositeDisposable.add(disposable);
        }else {
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(disposable);
            map.put(key, compositeDisposable);
        }
    }
    /**
     * 页面销毁时取消所有网络请求
     */
    public void cancelAllRequest() {
        disposables.clear();
    }
    public void clear(String key) {
        Set<String> keySet = map.keySet();
        if (keySet.equals(key)){
            CompositeDisposable compositeDisposable = map.get(key);
            compositeDisposable.clear();
            map.remove(key);
        }
    }
}
