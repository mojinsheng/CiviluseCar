package com.from.civilusecar.ui.view;

import android.view.View;

/**
 * Created by Administrator on 2017/3/25.
 */

public interface IViewHolder {

    <T extends View> T getView(int viewId);
}

