package com.xslong.xslonglib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * 适配器基类
 */
public abstract class BaseDataAdapter<TItem> extends BaseAdapter {

    protected LayoutInflater inflater;
    protected Context mContext;

    private ArrayList<TItem> list = new ArrayList<TItem>();

    public BaseDataAdapter(Context c, ArrayList<TItem> list){
        this.mContext = c;
        this.list = list;
        this.inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void insert(TItem data) {
        list.add(0, data);
        this.notifyDataSetChanged();
    }

    public void append(TItem data) {
        list.add(data);
        this.notifyDataSetChanged();
    }

    public void replace(TItem data) {
        int idx = this.list.indexOf(data);
        this.replace(idx, data);
    }

    public void replace(int index, TItem data) {
        if(index < 0) {
            return;
        }
        if(index > list.size() - 1) {
            return;
        }
        list.set(index, data);
        this.notifyDataSetChanged();
    }

    public ArrayList<TItem> getItems() {
        return list;
    }

    public TItem getItemT(int position) {
        return list.get(position);
    }

    public void removeItem(int position) {
        if(list.size() <= 0) {
            return;
        }
        if(position < 0) {
            return;
        }
        if(position > list.size() - 1) {
            return;
        }
        list.remove(position);
        this.notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        this.notifyDataSetChanged();
    }

    public abstract int[] getFindViewByIDs();
    public abstract View getLayout(int position, BaseViewHolder viewHolder);
    public final View getResourceView(int id){
        return inflater.inflate(id, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new BaseViewHolder();
            convertView = this.getLayout(position, viewHolder);
            if(convertView == null) {
                return null;
            }
            int[] idArray = this.getFindViewByIDs();
            if(idArray == null) {
                idArray = new int[]{};
            }
            for(int id : idArray) {
                viewHolder.setView(id, convertView.findViewById(id));
            }
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (BaseViewHolder) convertView.getTag();
        }
        this.renderData(position, viewHolder,convertView);
        return convertView;
    }
    public abstract void renderData(int position, BaseViewHolder viewHolder,View convertView);
}
