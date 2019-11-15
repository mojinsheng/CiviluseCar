package com.xslong.xslonglib.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xslong.xslonglib.R;

/**
 * Created by Administrator on 2017/3/7.
 */

public class T {

    private static Toast toast;

    private T() {

    }

    /**
     * 显示时间较长
     *
     * @param context
     * @param text
     */
    public static void showLong(Context context, String text) {
        show(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 显示时间较长
     *
     * @param context
     * @param text
     */
    public static void showLong(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 显示时间较长
     *
     * @param context
     * @param resId
     */
    public static void showLong(Context context, int resId) {
        if (context == null) {
            return;
        }
        show(context, context.getString(resId), Toast.LENGTH_LONG);
    }

    /**
     * 显示时间较短
     *
     * @param context
     * @param text
     */
    public static void showShort(Context context, String text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示时间较短
     *
     * @param context
     * @param text
     */
    public static void showShort(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示时间较短
     *
     * @param context
     * @param resId
     */
    public static void showShort(Context context, int resId) {
        if (context == null) {
            return;
        }
        show(context, context.getString(resId), Toast.LENGTH_SHORT);
    }

    private static void show(Context context, CharSequence text, int length) {
        if (context == null) {
            return;
        }
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, length);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View view = toast.getView();
        int padding = ScreenUtils.dip2px(context, 6);
        view.setPadding(padding * 2, padding * 2, padding * 2, padding * 2);
        view.setBackgroundResource(R.drawable.toast_bg);
        TextView tv = ((TextView) view.findViewById(
                android.R.id.message));
        tv.setTextColor(0xffffffff);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setCompoundDrawablesWithIntrinsicBounds(0,
                R.drawable.information, 0, 0);
        tv.setCompoundDrawablePadding(padding);
        toast.show();
    }

    public static void cancel(boolean cancel) {
        if (toast != null) {
            if (cancel) {
                toast.cancel();
            }
            toast = null;
        }
    }
}
