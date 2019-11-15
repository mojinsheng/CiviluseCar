package com.from.civilusecar.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.from.civilusecar.R;
import com.from.civilusecar.ui.activity.LoginActivity;
import com.xslong.xslonglib.base.app.BaseApplication;
import com.xslong.xslonglib.base.rx.RxManager;
import com.xslong.xslonglib.base.rx.exception.ApiException;
import com.xslong.xslonglib.base.rx.exception.HttpException;
import com.xslong.xslonglib.utils.ToastUtil;
import com.xslong.xslonglib.widget.LoadingDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author xslong
 * @time 2017/8/3  12:30
 * @desc ${TODD}
 */


public abstract class RxObserver<T> implements Observer<T> {
    private final String mTag;
    private Context mContext;
    private String msg;
    private boolean showDialog = true;
    private RxManager mRxManager;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog = true;
    }

    public void hideDialog() {
        this.showDialog = true;
    }

    public RxObserver(Context context, RxManager manager) {
        this(context, context.getApplicationContext().getString(com.xslong.xslonglib.R.string.loading), true, manager, "");
    }

    public RxObserver(Context context, RxManager manager, String tag) {
        this(context, context.getApplicationContext().getString(R.string.loading), true, manager, tag);
    }

    public RxObserver(Context context, boolean showDialog, RxManager manager, String tag) {
        this(context, context.getApplicationContext().getString(com.xslong.xslonglib.R.string.loading), showDialog, manager, tag);
    }

    public RxObserver(Context context, String msg, boolean showDialog, RxManager manager, String tag) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
        this.mRxManager = manager;
        this.mTag = tag;
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mRxManager.add(mTag, d);
        mRxManager.disposables.add(d);
        if (showDialog) {
            try {
                LoadingDialog.showDialogForLoading((Activity) mContext, msg, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        if (showDialog) {
            LoadingDialog.cancelDialogForLoading();
        }
        _onNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (showDialog) {
            LoadingDialog.cancelDialogForLoading();
        }
        if (e != null) {
            e.printStackTrace();
        }
        ToastUtil.showShort(e.getMessage(), mContext);
        if (e instanceof UnknownHostException) {
            ToastUtil.showShort("未知错误", mContext);
        } else if (e instanceof SocketTimeoutException) {
            ToastUtil.showShort("网络请求超时", mContext);
        } else if (e instanceof ConnectException) {
            ToastUtil.showShort("网络请求中断", mContext);
        } else if (e instanceof HttpException) {
            ToastUtil.showShort("网络异常!请检查您的网络状态", mContext);
        } else if (e instanceof IllegalStateException) {
            String message = e.getMessage();
            ToastUtil.showShort(message, mContext);
        } else if (e instanceof ApiException) {
            String message = e.getMessage();
            int code = ((ApiException) e).getCode();
            if (code == 300) {
                Activity context = (Activity) mContext;
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                context.finish();
            }
            ToastUtil.showShort(((ApiException) e).getMessage(), mContext);
            _onError(message, code);
        } else {
            _onError(BaseApplication.getAppContext().getString(com.xslong.xslonglib.R.string.net_error), 0);
            ToastUtil.showShort(BaseApplication.getAppContext().getString(com.xslong.xslonglib.R.string.net_error), mContext);
        }
    }

    @Override
    public void onComplete() {
        if (showDialog) {
            LoadingDialog.cancelDialogForLoading();
        }
        _onComplete();
    }

    protected abstract void _onNext(T t);

    protected void _onError(String message, int code) {
        ToastUtil.showShort(message, mContext);
    }

    protected void _onComplete() {
    }
}
