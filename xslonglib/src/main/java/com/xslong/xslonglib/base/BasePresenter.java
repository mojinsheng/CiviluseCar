package com.xslong.xslonglib.base;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.xslong.xslonglib.base.rx.RxManager;


/**
 * des:基类presenter
 * 负责处理业务逻辑代码，处理Model数据，然后将处理完的数据分发到View层
 */
public abstract class BasePresenter<T> {
    public Context mContext;
    public T mView;
    public RxManager mRxManage;
    protected LifecycleProvider<ActivityEvent> mActivityProvider;
    protected LifecycleProvider<FragmentEvent> mFragmentProvider;

    public void setActivityProvider(LifecycleProvider<ActivityEvent> activityProvider) {
        mActivityProvider = activityProvider;
    }

    public void setFragmentProvider(LifecycleProvider<FragmentEvent> fragmentProvider) {
        mFragmentProvider = fragmentProvider;
    }

    public void attachView(T v) {
        this.mView = v;
        this.onStart();
    }

    public void onStart() {

    }

    public void detachView() {
        mView = null;
        mActivityProvider = null;
        mFragmentProvider = null;
        mRxManage = null;
        mContext = null;
    }
}
