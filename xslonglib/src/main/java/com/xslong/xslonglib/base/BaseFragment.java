package com.xslong.xslonglib.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.xslong.xslonglib.R;
import com.xslong.xslonglib.base.rx.RxManager;
import com.xslong.xslonglib.utils.TUtil;
import com.xslong.xslonglib.utils.ToastUtil;
import com.xslong.xslonglib.utils.tcolor.StatusBarUtil;
import com.xslong.xslonglib.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * des:基类fragment
 */
public abstract class BaseFragment<T extends BasePresenter> extends RxFragment {
    protected View rootView;
    public T mPresenter;
    protected String TAG;
    public RxManager mRxManager;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
        }
        mRxManager = RxManager.getInstance();
        TAG = getClass().getName();
        mUnbinder = ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        mPresenter =  initPresenter();
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
            mPresenter.mRxManage = this.mRxManager;
            mPresenter.attachView(this);
            mPresenter.setFragmentProvider(this);
        }
        initView();
        initData();
        initData(savedInstanceState);
        return rootView;
    }

    protected void initData() {
    }
    protected void initData(Bundle savedInstanceState) {
    }

    //获取布局文件
    protected abstract int getLayoutResource();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract T initPresenter();

    //初始化view
    protected abstract void initView();


    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()){
            lazyLoad();
        }
    }

    protected void lazyLoad(){}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()){
            onResume();
        }
    }
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
        intent.setClass(getActivity(), cls);
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
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(getActivity());
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(getActivity(), msg, true);
}

    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }


    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        ToastUtil.showShort(text,getActivity());
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
        ToastUtil.showShort(resId, getActivity());
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    public void showLongToast(int resId) {
        ToastUtil.showLong(resId, getActivity());
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        ToastUtil.showLong(text, getActivity());
    }


    public void showToastWithImg(String text, int res) {
        ToastUtil.showToastWithImg(text, res, getActivity());
    }

    /**
     * 网络访问错误提醒
     */
    public void showNetErrorTip() {
        ToastUtil.showToastWithImg(getText(R.string.net_error).toString(), R.drawable.ic_wifi_error, getActivity());
    }

    public void showNetErrorTip(String error) {
        ToastUtil.showToastWithImg(error, R.drawable.ic_wifi_error, getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mRxManager.clear(TAG);
    }
    public void initStatusBar(@ColorRes int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            // 获取状态栏高度
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            View rectView = new View(getContext());
            // 绘制一个和状态栏一样高的矩形，并添加到视图中
            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            rectView.setLayoutParams(params);
            //设置状态栏颜色
            rectView.setBackgroundColor(getResources().getColor(colorRes));
            // 添加矩形View到布局中
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            ViewGroup decorView = (ViewGroup) getActivity().getWindow().getDecorView();
            decorView.addView(rectView);
            StatusBarUtil.setImmersiveStatusBar(getActivity(),true);

            ViewGroup rootView = (ViewGroup) ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }
}
