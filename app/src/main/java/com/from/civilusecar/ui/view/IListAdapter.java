package com.from.civilusecar.ui.view;

import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2017/3/25.
 */

public interface IListAdapter<T> {

    /**
     * 设置可编辑状态，true=列表可编辑，false=不可编辑
     *
     * @param editable
     */
    void setEditable(boolean editable);

    /**
     * 可编辑状态，true=列表可编辑，false=不可编辑
     *
     * @return
     */
    boolean isEditable();

    /**
     * 单选状态，true=单选，false=多选
     *
     * @param isSingleSelect
     */
    void setSingleSelect(boolean isSingleSelect);

    /**
     * 单选状态，true=单选，false=多选
     *
     * @return
     */
    boolean isSingleSelect();

    /**
     * 批量添加数据
     *
     * @param items
     */
    void addItems(List<T> items);

    /**
     * 批量添加数据
     *
     * @param items
     * @param position
     */
    void addItems(List<T> items, int position);

    /**
     * 添加单个数据
     *
     * @param item
     */
    void addItem(T item);

    /**
     * 添加单个数据到指定下标
     *
     * @param item
     * @param position
     */
    void addItem(T item, int position);

    /**
     * 设置数据（完全替换原列表数据）
     *
     * @param items
     */
    void setItems(List<T> items);

    /**
     * 设置单个数据（替换指定下标的数据）
     *
     * @param item
     * @param position
     */
    void setItem(T item, int position);

    /**
     * 批量移除数据
     *
     * @param items
     * @return 是否移除成功
     */
    boolean removeItems(List<T> items);

    /**
     * 移除单个数据
     *
     * @param item
     * @return 是否移除成功
     */
    boolean remove(T item);

    /**
     * 移除指定下标数据
     *
     * @param position
     * @return 移除出来的数据对象
     */
    T remove(int position);

    /**
     * 数据列表
     *
     * @return
     */
    List<T> getItems();

    /**
     * 取出指定下标的数据
     *
     * @param position
     * @return
     */
    T getItem(int position);

    /**
     * 被选中的数据列表
     *
     * @return
     */
    List<T> getSelectedItems();

    /**
     * 选中指定下标数据
     *
     * @param position
     */
    void select(int position);

    /**
     * 选中指定数据
     *
     * @param t
     */
    void select(T t);

    /**
     * 取消指定下标选中
     *
     * @param position
     */
    void deselect(int position);

    /**
     * 取消选中数据
     *
     * @param t
     */
    void deselect(T t);

    /**
     * 判断指定下标数据是否已选中
     *
     * @param position
     * @return
     */
    boolean isSelected(int position);

    /**
     * 判断数据是否已选中
     *
     * @param t
     * @return
     */
    boolean isSelected(T t);

    /**
     * 全选
     *
     * @return true=全选成功，false=当前为单选模式，不可全选
     */
    boolean selectAll();

    /**
     * 多选
     *
     * @param items
     * @return true=多选成功，false=当前为单选模式，不可多选
     */
    boolean selectAll(List<T> items);

    /**
     * 取消已选
     */
    void deselectAll();

    /**
     * 是否已全选
     *
     * @return
     */
    boolean isSelectAll();

    /**
     * 反选
     *
     * @return
     */
    boolean reverseSelect();

    /**
     * 从列表中移除所选中的数据
     *
     * @return
     */
    boolean removeSelectedItems();

    /**
     * 清空
     */
    void clear();

    /**
     * 绑定数据到View
     *
     * @param position    数据下标
     * @param convertView 显示数据的View
     * @param hd          ListViewHolder
     * @param item        数据对象
     */
    void binding(int position, View convertView, IViewHolder hd, T item);
}

