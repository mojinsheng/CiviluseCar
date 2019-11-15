package com.from.civilusecar.ui.view;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public class ListViewHolder implements IViewHolder{

    private SparseArray<View> views;
    private int position;
    private View convertView;

    private ListViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.position = position;
        this.views = new SparseArray<View>();

        convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);

        convertView.setTag(this);

    }

    public static ListViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ListViewHolder(context, parent, layoutId, position);
        } else {
            ListViewHolder holder = (ListViewHolder) convertView.getTag();
            holder.position = position; //即时ViewHolder是复用的，但是position记得更新一下
            return holder;
        }
    }

    /*
    通过viewId获取控件
     */
    //使用的是泛型T,返回的是View的子类
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);

        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }

        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

}