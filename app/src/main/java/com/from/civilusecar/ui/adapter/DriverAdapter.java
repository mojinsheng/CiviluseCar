package com.from.civilusecar.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.from.civilusecar.R;
import com.from.civilusecar.base.CarBean;
import com.from.civilusecar.bean.UserByCarBean;

import java.util.List;

public class DriverAdapter extends BaseQuickAdapter<UserByCarBean, BaseViewHolder> {

    Context context;
    Button btn_setting,car_constrol;
    private int flag;

    public DriverAdapter(Context _context, @Nullable List<UserByCarBean> data) {
        super(R.layout.item_managerdriver, data);
        context=_context;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserByCarBean item) {
        helper.setText(R.id.text_carname, item.getUserName());
         btn_setting=helper.getView(R.id.btn_jiebang);
         car_constrol=helper.getView(R.id.btn_zhuyi);
         if(Integer.parseInt(item.getBindType())==1){
             btn_setting.setVisibility(View.VISIBLE);
             car_constrol.setVisibility(View.VISIBLE);
         }
        helper.addOnClickListener(R.id.btn_jiebang);
        helper.addOnClickListener(R.id.btn_zhuyi);
    }

    @Override
    public int getItemViewType(int position) {

              flag=position;

        return super.getItemViewType(position);
    }

}
