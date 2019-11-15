package com.xslong.xslonglib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xslong.xslonglib.R;


/**
 * Toast统一管理类
 */
public abstract class ToastUtil {
    private static Toast toast;
    private static Toast toast2;
    private static View mView;
    private static TextView mTextView;

    private static Toast initToast(CharSequence message, Context context, int duration) {
        if (toast == null) {
            synchronized (context) {
                if (toast == null) {
                    toast = Toast.makeText(context, message, duration);
                }
            }
        } else {
            showMessage(context, (String) message, duration);
        }
        return toast;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message, Context context) {
        initToast(message, context, Toast.LENGTH_SHORT).show();
    }


    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId, Context context) {
        initToast(context.getResources().getText(strResId), null, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message, Context context) {
        initToast(message, context, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(int strResId, Context context) {
        initToast(context.getResources().getText(strResId), null, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration, Context context) {
        initToast(message, context, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param strResId
     * @param duration
     */
    public static void show(Context context, int strResId, int duration) {
        initToast(context.getResources().getText(strResId), null, duration).show();
    }

    public static void showMessage(Context context, String msg, int duration) {
        if (mView == null) {
            mView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.toast_view, null);
            mTextView = (TextView) mView.findViewById(R.id.toast_text);
        }
        mTextView.setText(msg);
        // 自定义Toast
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.setView(mView);
        toast.show();
    }

    /**
     * 显示有image的toast
     *
     * @param tvStr
     * @param imageResource
     * @return
     */
    public static Toast showToastWithImg(final String tvStr, final int imageResource, Context context) {
        if (toast2 == null) {
            toast2 = new Toast(context);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        TextView tv = (TextView) view.findViewById(R.id.toast_custom_tv);
        tv.setText(TextUtils.isEmpty(tvStr) ? "" : tvStr);
        ImageView iv = (ImageView) view.findViewById(R.id.toast_custom_iv);
        if (imageResource > 0) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(imageResource);
        } else {
            iv.setVisibility(View.GONE);
        }
        toast2.setView(view);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        toast2.show();
        return toast2;
    }
}
