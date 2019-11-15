package com.from.civilusecar.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * 显示任意View的Dialog，View需实现ViewDialogCallback接口
 */
public class ViewDialog extends AbsCustomDialog {

    private ViewDialogCallback callback;
    private boolean isDismissRemoveView = true;

    public ViewDialog(Context context, ViewDialogCallback callback) {
        super(context, 0);
        this.callback = callback;
    }

    public ViewDialog(Context context, ViewDialogCallback callback, int theme) {
        super(context, 0, theme);
        this.callback = callback;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (isDismissRemoveView) {
            ViewParent parent = getContentView().getParent();
            if (parent != null) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.removeAllViewsInLayout();
            }
        }
    }

    @Override
    public void show() {
        super.show();
    }

    /**
     * 是否dismiss后移除View
     *
     * @param isDismissRemoveView
     */
    public void show(boolean isDismissRemoveView) {
        this.isDismissRemoveView = isDismissRemoveView;
        super.show();
    }

    @Override
    public View getContentView() {
        return callback == null ? null : callback.binding(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    public interface ViewDialogCallback {
        View binding(Dialog dialog);
    }
}

