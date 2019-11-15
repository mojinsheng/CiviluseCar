package com.from.civilusecar.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.from.civilusecar.R;
import com.xslong.xslonglib.utils.L;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 * 下拉列表界面，可指定泛型，须重写toString()方法，其返回值就是显示在item中的字符串。
 */

public class PullDownListView<T> extends FrameLayout implements AdapterView.OnItemClickListener, ViewDialog.ViewDialogCallback {

    private ListView lv;
    private PullDownAdapter adapter;

    private PullDownListView.OnItemClickListener onItemClickListener;
    private PopupWindow popupWindow;
    private View anchor;
    /**
     * 是否可取消选择
     */
    private boolean cancellable = true;

    /**
     * 是否可以选中
     */
    private boolean selectable = true;
    private Dialog dialog;

    public PullDownListView(@NonNull Context context) {
        super(context);
        init(null, 0);
    }

    public PullDownListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PullDownListView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }


    public PullDownListView(@NonNull Context context, T... items) {
        this(context);
        setItems(items);
    }

    public PullDownListView(Context context, OnItemClickListener<T> onItemClickListener, T... items) {
        this(context, items);
        this.onItemClickListener = onItemClickListener;
    }

    public PullDownListView(Context context, boolean cancellable, boolean selectable, OnItemClickListener<T>
            onItemClickListener, T... items) {
        this(context, items);
        this.cancellable = cancellable;
        this.selectable = selectable;
        this.onItemClickListener = onItemClickListener;
    }

    public PullDownListView(@NonNull Context context, List<T> items) {
        this(context);
        setItems(items);
    }

    public PullDownListView(Context context, OnItemClickListener<T> onItemClickListener, List<T> items) {
        this(context, items);
        this.onItemClickListener = onItemClickListener;
    }

    public PullDownListView(Context context, boolean cancellable, boolean selectable, OnItemClickListener<T>
            onItemClickListener, List<T> items) {
        this(context, onItemClickListener, items);
        this.cancellable = cancellable;
        this.selectable = selectable;
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_pulldown_list, this);
        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
        adapter = new PullDownAdapter(getContext());

        lv.setAdapter(adapter);
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public T getItem(int position) {
        return adapter.getItem(position);
    }

    public void showPopupwindow(View anchor) {
        showPopupwindow(anchor, anchor.getWidth(), null, null);
    }

    public void showPopupwindow(View anchor, int width) {
        showPopupwindow(anchor, width, null, null);
    }

    public void showPopupwindow(View anchor, T selectItem) {
        showPopupwindow(anchor, anchor.getWidth(), null, selectItem);
    }

    public void showPopupwindow(View anchor, int width, List<T> items, T selectItem) {
        setItems(items);
        L.i("getMeasuredWidth()=" + getMeasuredWidth());
        if (popupWindow == null) {
            popupWindow = new PopupWindow(this, width, LayoutParams.WRAP_CONTENT, true);
            popupWindow.setTouchable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        }
        popupWindow.showAsDropDown(anchor);
        L.i("getWidth()=" + getWidth());
        L.i("anchor.getWidth()=" + anchor.getWidth());
        if (selectItem == null) {
            selectItem = (T) anchor.getTag();
        }
        if (selectItem != null) {
            adapter.setSelectItem(selectItem);
        }
        this.anchor = anchor;
    }

    public void setItems(T... items) {
        if (items != null) {
            adapter.setItems(Arrays.asList(items));
        }
    }

    public void setItems(List<T> items) {
        if (items != null) {
            adapter.setItems(items);
        }
    }

    public void setOnItemClickListener(PullDownListView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        T t = adapter.getItem(position);
        boolean isSelected = t.equals(adapter.selectItem);
        if (selectable) {
            //如果已经选中，就取消
            adapter.setSelectItem(isSelected && cancellable ? null : t);
        } else {
            adapter.setSelectItem(null);
        }
        if (anchor instanceof TextView) {
            TextView tv = (TextView) anchor;
            tv.setText(isSelected && cancellable ? "" : t.toString());
            tv.setTag(isSelected && cancellable ? null : t);
        }
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(this, view, position, t.equals(adapter.selectItem));
        }
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 列表数量
     *
     * @return
     */
    public int getCount() {
        return adapter.getCount();
    }

    /**
     * 获取所选的项
     *
     * @return
     */
    public T getSelectedItem() {
        if (adapter == null) {
            return null;
        }
        return adapter.selectItem;
    }

    /**
     * 设置选中项
     *
     * @param item
     */
    public void setSelectedItem(T item) {
        adapter.setSelectItem(item);
        if (item == null) {
            if (anchor instanceof TextView) {
                TextView tv = (TextView) anchor;
                tv.setText("");
                tv.setTag(null);
            }
        }
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    public View binding(Dialog dialog) {
        this.dialog = dialog;
        return this;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(PullDownListView<T> parent, View view, int position, boolean isSelected);
    }

    class PullDownAdapter extends AbsBaseAdapter<T> {

        private T selectItem;

        public PullDownAdapter(Context context) {
            super(context, null, R.layout.item_pulldown_list);
        }


        @Override
        public void binding(int position, View convertView, IViewHolder hd, T item) {
            TextView itemTv = hd.getView(R.id.itemTv);
            itemTv.setText(item.toString());
            itemTv.setTypeface(Typeface.defaultFromStyle(item.equals(selectItem) ? Typeface.BOLD : Typeface.NORMAL));
            itemTv.setSelected(item.equals(selectItem));
        }

        public void setSelectItem(T item) {
            selectItem = item;
            notifyDataSetChanged();
        }
    }
}