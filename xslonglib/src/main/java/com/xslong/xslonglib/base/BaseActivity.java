package com.xslong.xslonglib.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xslong.xslonglib.R;
import com.xslong.xslonglib.base.app.AppManager;
import com.xslong.xslonglib.base.rx.RxManager;
import com.xslong.xslonglib.utils.ToastUtil;
import com.xslong.xslonglib.utils.tcolor.StatusBarUtil;
import com.xslong.xslonglib.widget.LoadingDialog;
import com.xslong.xslonglib.widget.StatusBarCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 * 基类
 */

/***************使用例子
 * @author xslong*********************/
public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity {
    public RxManager mRxManager;
    protected String TAG;
    protected T mPresenter;
    public Context mContext;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getPackageName() + "." + getClass().getSimpleName();
        mRxManager = RxManager.getInstance();
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        // 默认着色状态栏
        SetStatusBarColor();
        setWindowStatusBarColor(this, R.color.colorPrimary,R.color.welcome);

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        mContext = this;
        mPresenter = this.initPresenter();
        if (mPresenter != null) {
            mPresenter.mContext = this;
            mPresenter.mRxManage = this.mRxManager;
            mPresenter.attachView(this);
            mPresenter.setActivityProvider(this);
        }
        this.initView();
        mUnbinder = ButterKnife.bind(this);
        this.initData();
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
       requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /*********************子类实现*****************************/
    //获取布局文件
    protected abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    protected abstract T initPresenter();

    //初始化view
    protected abstract void initData();

    protected abstract void initView();

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }
    public static void setWindowStatusBarColor(Activity activity, int colorResId, int colorId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                window.setNavigationBarColor(activity.getResources().getColor(colorId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    protected void getBundleExtras(Bundle extras){}

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog(Activity context) {
        LoadingDialog.showDialogForLoading(context);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(Activity context, String msg) {
        LoadingDialog.showDialogForLoading(context, msg, true);
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUtil.showShort(text, getApplicationContext());
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUtil.showShort(resId, getApplicationContext());
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUtil.showLong(resId, getApplicationContext());
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUtil.showLong(text, getApplicationContext());
    }

    /**
     * 带图片的toast
     *
     * @param text
     * @param res
     */
    public void showToastWithImg(String text, int res) {
        ToastUtil.showToastWithImg(text, res, getApplicationContext());
    }

    /**
     * 网络访问错误提醒
     */
    public void showNetErrorTip() {
        ToastUtil.showToastWithImg(getText(R.string.net_error).toString(), R.drawable.ic_wifi_error, getApplicationContext());
    }

    public void showNetErrorTip(String error) {
        ToastUtil.showToastWithImg(error, R.drawable.ic_wifi_error, getApplicationContext());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mRxManager.clear(this.TAG);
        LoadingDialog.cancelDialogForLoading();
        mUnbinder.unbind();
        AppManager.getAppManager().finishActivity(this);
    }
}
