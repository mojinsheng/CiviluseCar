package com.from.civilusecar.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.from.civilusecar.R;

/**
 * 自定义无边框对话框抽象类
 *
 * @author huangzhongwen
 */
public abstract class AbsCustomDialog extends Dialog {

    public Window mWindow;

    protected Context context;

    private View view;

    private int layoutResId;

    public AbsCustomDialog(Context context, int layoutResId) {
        super(context);
        this.context = context;
        this.layoutResId = layoutResId;
    }

    public AbsCustomDialog(Context context, int layoutResId, int theme) {
        super(context, theme);
        this.context = context;
        this.layoutResId = layoutResId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(getCancelable());
        setCanceledOnTouchOutside(getCanceledOnTouchOutside());
        view = getContentView();
        if (view != null) {
            setContentView(view);
        }
        ViewGroup.LayoutParams glp = view.getLayoutParams();
        if (glp instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) glp;
            int margin = getMargin();
            if (margin > 0) {
                mlp.setMargins(margin, margin, margin, margin);
            } else {
                mlp.setMargins(getLeftMargin(), getToptMargin(),
                        getRightMargin(), getBottomMargin());
            }
        }
        mWindow = getWindow();
        mWindow.setBackgroundDrawableResource(getBackgroundDrawableResourceId());
        if (getType() != -1) {
            mWindow.setType(getType());
        }
        if (getWindowAnimationsResId() != -1) {
            mWindow.setWindowAnimations(getWindowAnimationsResId());
        } else if (getGravity() == Gravity.BOTTOM) {
            mWindow.setWindowAnimations(R.style.bottomDialogWindowAnim);
        }
        if (getDimEnabled()) {
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            mWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        WindowManager.LayoutParams wlp = mWindow.getAttributes();
        wlp.width = getWidth();
        wlp.height = getHeight();
        wlp.gravity = getGravity();
        onWindowAttributesChanged(wlp);
        initView();
        initData();
        initListener();
    }

    /**
     * 是否背景模糊
     *
     * @return
     */
    public boolean getDimEnabled() {
        return true;
    }

    /**
     * 是否可取消
     *
     * @return
     */
    public boolean getCancelable() {
        return true;
    }

    /**
     * 触摸外部是否可取消
     *
     * @return
     */
    public boolean getCanceledOnTouchOutside() {
        return true;
    }

    /**
     * 背景资源ID
     *
     * @return
     */
    public int getBackgroundDrawableResourceId() {
        return android.R.color.transparent;
    }

    /**
     * Dialog类型
     *
     * @return
     */
    public int getType() {
        return -1;
    }

    ;

    /**
     * 动画资源ID
     *
     * @return
     */
    public int getWindowAnimationsResId() {
        return -1;
    }

    ;

    /**
     * Dialog宽
     *
     * @return
     */
    public int getWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    /**
     * Dialog高
     *
     * @return
     */
    public int getHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /**
     * 显示位置
     *
     * @return
     */
    public int getGravity() {
        return Gravity.CENTER;
    }

    /**
     * Dialog外边距（左）,getMargin()>0时无效
     *
     * @return
     */
    public int getLeftMargin() {
        return 0;
    }

    /**
     * Dialog外边距（右）,getMargin()>0时无效
     *
     * @return
     */
    public int getRightMargin() {
        return 0;
    }

    /**
     * Dialog外边距（上）,getMargin()>0时无效
     *
     * @return
     */
    public int getToptMargin() {
        return 0;
    }

    /**
     * Dialog外边距（下）,getMargin()>0时无效
     *
     * @return
     */
    public int getBottomMargin() {
        return 0;
    }

    /**
     * Dialog外边距（上下左右）
     *
     * @return
     */
    public int getMargin() {
        return 0;
    }

    public View getContentView() {
        if (view == null && layoutResId != 0) {
            view = getLayoutInflater().inflate(layoutResId, null);
        }
        return view;
    }

    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 显示数据
     */
    public abstract void initData();

    /**
     * 初始化监听器
     */
    public abstract void initListener();
}

