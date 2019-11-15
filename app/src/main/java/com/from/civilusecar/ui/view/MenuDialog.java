package com.from.civilusecar.ui.view;

import android.content.Context;
import android.view.Gravity;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 */

public class MenuDialog<T> extends ViewDialog {

    public MenuDialog(Context context, PullDownListView.OnItemClickListener<T> onItemClickListener, T... items) {
        this(context, false, false, onItemClickListener, items);
    }

    public MenuDialog(Context context, PullDownListView.OnItemClickListener<T> onItemClickListener, List<T> items) {
        this(context, false, false, onItemClickListener, items);
    }

    public MenuDialog(Context context, boolean cancelable, boolean selectable, PullDownListView.OnItemClickListener<T>
            onItemClickListener, T... items) {
        super(context, new PullDownListView<T>(context, cancelable, selectable, onItemClickListener, items));
    }

    public MenuDialog(Context context, boolean cancelable, boolean selectable, PullDownListView.OnItemClickListener<T>
            onItemClickListener, List<T> items) {
        super(context, new PullDownListView<T>(context, cancelable, selectable, onItemClickListener, items));
    }

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

//    @Override
//    public int getWindowAnimationsResId() {
//        return R.style.bottomDialogWindowAnim;
//    }

    @Override
    public boolean getDimEnabled() {
        return true;
    }

    @Override
    public void show() {
        super.show(false);
    }
}

