package com.from.civilusecar.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.from.civilusecar.R;
import com.from.civilusecar.utils.statusbarutil.StatusBarUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/12.
 */

public abstract class AntBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            boolean result = fixOrientation();
            //XLog.i(XLog.BASE, "onCreate fixOrientation when Oreo, result = " + result);
        }

        super.onCreate(savedInstanceState);
        //CrashReport.testJavaCrash();
        setContentView(getContentView());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        initViews(savedInstanceState);
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
           // XLog.i(XLog.BASE, "avoid calling setRequestedOrientation when Oreo.");
            return;

        }
        super.setRequestedOrientation(requestedOrientation);
    }

    private boolean isTranslucentOrFloating(){
        boolean isTranslucentOrFloating = false;
        try {
            int [] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean)m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
    public void initDrawerStatusBar(@ColorRes int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            // 获取状态栏高度
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            View rectView = new View(this);
            // 绘制一个和状态栏一样高的矩形，并添加到视图中
            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            rectView.setLayoutParams(params);
            //设置状态栏颜色
            rectView.setBackgroundColor(getResources().getColor(colorRes));
            // 添加矩形View到布局中
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setImmersiveStatusBar(this,true);

            ViewGroup content = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content));
            ViewGroup rootview = content.findViewById(R.id.rootview);
            rootview.addView(rectView, 0);
        }
    }
    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract int getContentView();


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 关闭软键盘
     *
     * @param editText
     */
    public void hideSoftInput(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    /**
     * 弹出软键盘
     *
     * @param editText
     */
    public void showSoftInput(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            editText.requestFocus();
            inputMethodManager.showSoftInput(editText, 0);
        }
    }
    public static void setWindowStatusBarColor(Activity activity, int color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setWindowStatusBarColor(Dialog dialog, int color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = dialog.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
