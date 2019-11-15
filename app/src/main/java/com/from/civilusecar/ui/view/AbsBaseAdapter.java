package com.from.civilusecar.ui.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的ListView的BaseAdapter，所有的ListView的自定义adapter都可以继承这个类
 */
public abstract class AbsBaseAdapter<T> extends BaseAdapter implements IListAdapter<T> {

    //为了让子类访问，于是将属性设置为protected
    protected Context context;
    protected List<T> itemDatas = new ArrayList<T>();
    private int layoutId; //不同的ListView的item布局肯能不同，所以要把布局单独提取出来

    /**
     * 选中的Item
     */
    private List<T> selectedItems = new ArrayList<T>();

    /**
     * 是否可编辑
     */
    private boolean isEditable = false;

    /**
     * 是否单选，true只能选一个，false可以选多个
     */
    private boolean isSingleSelect = false;


    public AbsBaseAdapter(Context context, List<T> itemDatas, int layoutId) {
        this.context = context;
        addItems(itemDatas);
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return itemDatas.size();
    }

    @Override
    public T getItem(int position) {
        return itemDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //初始化ViewHolder,使用通用的ViewHolder，一样代码就搞定ViewHolder的初始化咯
        ListViewHolder holder = ListViewHolder.get(context, convertView, parent, layoutId, position);//layoutId就是单个item的布局
        binding(position, holder.getConvertView(), holder, getItem(position));
        return holder.getConvertView(); //这一行的代码要注意了
    }

    @Override
    public void setItems(List<T> itemDatas) {
        this.itemDatas.clear();
        if (itemDatas != null) {
            this.itemDatas.addAll(itemDatas);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置单个数据（替换指定下标的数据）
     *
     * @param item
     * @param position
     */
    @Override
    public void setItem(T item, int position) {
        if (position < 0 || position >= getCount()) {
            return;
        }
        itemDatas.set(position, item);
        notifyDataSetChanged();
    }

    /**
     * 批量移除数据
     *
     * @param items
     * @return 是否移除成功
     */
    @Override
    public boolean removeItems(List<T> items) {
        if (items == null || items.isEmpty()) {
            return false;
        }
        selectedItems.removeAll(items);
        boolean result = itemDatas.removeAll(items);
        notifyDataSetChanged();
        return result;
    }

    /**
     * 已选中的数据
     *
     * @return
     */
    @Override
    public List<T> getSelectedItems() {
        return selectedItems;
    }

    /**
     * 选中指定下标的数据
     *
     * @param position
     */
    @Override
    public void select(int position) {
        if (position < 0 || position >= getCount()) {
            return;
        }
        T t = getItem(position);
        select(t);
    }

    /**
     * 取消选中指定下标的数据
     *
     * @param position
     */
    @Override
    public void deselect(int position) {
        if (position < 0 || position >= getCount()) {
            return;
        }
        T t = getItem(position);
        if (selectedItems.contains(t)) {
            selectedItems.remove(t);
        }
        notifyDataSetChanged();
    }

    @Override
    public boolean selectAll() {
        if (isSingleSelect) {
            return false;
        }
        if (!isSelectAll()) {
            selectedItems.clear();
            selectedItems.addAll(itemDatas);
        }
        notifyDataSetChanged();
        return true;
    }

    @Override
    public void clear() {
        itemDatas.clear();
        selectedItems.clear();
        notifyDataSetChanged();
    }

    /**
     * 是否已全选
     *
     * @return
     */
    @Override
    public boolean isSelectAll() {
        return itemDatas.size() > 0 && selectedItems.containsAll(itemDatas);
    }

    /**
     * 取消全选
     */
    @Override
    public void deselectAll() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    /**
     * 移除所选
     *
     * @return 移除成功
     */
    @Override
    public boolean removeSelectedItems() {
        boolean result = itemDatas.removeAll(selectedItems);
        if (result) {
            selectedItems.clear();
        }
        notifyDataSetChanged();
        return result;
    }

    /**
     * 移除指定下标
     *
     * @param position
     * @return 移除出来的对象，null表示没有对象被移除
     */
    @Override
    public T remove(int position) {
        if (position < 0 || position >= getCount()) {
            return null;
        }
        T t = itemDatas.remove(position);
        if (isSelected(t)) {
            selectedItems.remove(t);
        }
        notifyDataSetChanged();
        return t;
    }

    /**
     * 数据列表
     *
     * @return
     */
    @Override
    public List<T> getItems() {
        return itemDatas;
    }

    /**
     * 移除指定对象
     *
     * @param t
     * @return true移除成功，false移除失败
     */
    @Override
    public boolean remove(T t) {
        if (t != null && itemDatas.contains(t)) {
            if (isSelected(t)) {
                selectedItems.remove(t);
            }
            boolean result = itemDatas.remove(t);
            notifyDataSetChanged();
            return result;
        }
        return false;
    }

    /**
     * 反选
     *
     * @return true=反选成功，false=当前为单选模式，不可反选
     */
    @Override
    public boolean reverseSelect() {
        if (isSingleSelect) {
            return false;
        }
        List<T> list = new ArrayList<T>(itemDatas);
        list.removeAll(selectedItems);
        selectedItems.addAll(list);
        notifyDataSetChanged();
        return true;
    }

    /**
     * 选中一条数据
     *
     * @param t
     */
    @Override
    public void select(T t) {
        if (t != null && !selectedItems.contains(t)) {
            if (isSingleSelect) {
                selectedItems.clear();
            }
            selectedItems.add(t);
        }
        notifyDataSetChanged();
    }

    /**
     * 选中多条数据
     *
     * @param items
     * @return true=多选成功，false=当前为单选模式，不可多选
     */
    @Override
    public boolean selectAll(List<T> items) {
        if (isSingleSelect) {
            //单选状态下不可多选
            return false;
        }
        if (items != null) {
            selectedItems.addAll(items);
            notifyDataSetChanged();
        }
        return true;
    }

    /**
     * 取消选中的数据
     *
     * @param t
     */
    @Override
    public void deselect(T t) {
        if (t != null && selectedItems.contains(t)) {
            selectedItems.remove(t);
        }
        notifyDataSetChanged();
    }

    /**
     * 判断指定下标是否被选中
     *
     * @param position
     * @return
     */
    @Override
    public boolean isSelected(int position) {
        if (position < 0 || position >= getCount()) {
            return false;
        }
        T t = getItem(position);
        return selectedItems.contains(t);
    }

    /**
     * 判断指定数据是否被选中
     *
     * @param t
     * @return
     */
    @Override
    public boolean isSelected(T t) {
        if (t != null) {
            return selectedItems.contains(t);
        }
        return false;
    }


    //将convert方法公布出去
    public abstract void binding(int position, View convertView, IViewHolder hd, T item);

    @Override
    public void setEditable(boolean editable) {
        isEditable = editable;
        notifyDataSetChanged();
    }

    @Override
    public boolean isEditable() {
        return isEditable;
    }

    @Override
    public void setSingleSelect(boolean singleSelect) {
        isSingleSelect = singleSelect;
    }

    @Override
    public boolean isSingleSelect() {
        return isSingleSelect;
    }

    /**
     * 批量添加数据
     *
     * @param items
     */
    @Override
    public void addItems(List<T> items) {
        if (items != null) {
            this.itemDatas.addAll(items);
        }
        notifyDataSetChanged();
    }

    /**
     * 批量添加数据
     *
     * @param items
     * @param position
     */
    @Override
    public void addItems(List<T> items, int position) {
        if (items != null) {
            this.itemDatas.addAll(position, items);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加单个数据
     *
     * @param item
     */
    @Override
    public void addItem(T item) {
        if (item != null) {
            this.itemDatas.add(item);
        }
        notifyDataSetChanged();
    }

    @Override
    public void addItem(T item, int position) {
        if (item != null) {
            this.itemDatas.add(position, item);
        }
        notifyDataSetChanged();
    }
}
