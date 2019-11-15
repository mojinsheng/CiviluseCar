package com.from.civilusecar.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.from.civilusecar.R;
import com.from.civilusecar.app.Global;
import com.from.civilusecar.bean.CustomerInfo;
import com.from.civilusecar.bean.QueryCarList;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.constant.Constant;
import com.from.civilusecar.control.BisManagerHandle;
import com.from.civilusecar.base.BaseTitleActivity;
import com.from.civilusecar.base.CarBean;
import com.from.civilusecar.mvp.contract.MyCarContract;
import com.from.civilusecar.mvp.presenter.MyCarPresenter;
import com.from.civilusecar.ui.windon.MyDialog;
import com.from.civilusecar.utils.SPUtils;
import com.xslong.xslonglib.base.bean.BaseResponse;
import com.xslong.xslonglib.utils.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MyCarActivity extends BaseTitleActivity<MyCarPresenter> implements MyCarContract.View {


    private MyCarPresenter myCarPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private CarAdapter carAdapter;

    private List<CarBean> carBeanList = new ArrayList<>();
    private   List<QueryCarList> carList;
    private  RadioButton rb_check;

    @Override
    protected int getSubLayoutId() {
        return R.layout.activity_mycar;
    }

    @Override
    public void queryCarListData(BaseResponse<QueryCarListsBean> data) {
         carList=data.getData().getCarList();
        MyDialog myDialog;
        myDialog = new MyDialog(MyCarActivity.this);

        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        carAdapter = new CarAdapter(this,carList);
        carAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(carAdapter);
        recycler_view.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                int itemViewId = view.getId();
                 rb_check= view.findViewById(R.id.rb_check );

               // Log.i("mojin","carId:"+carId+",mojin:"+carList.get(position).getCarSn());
                switch (itemViewId){
                    case R.id.rb_check :
                        if(carList.size()==1){
                            rb_check.setChecked(true);
                            T.showLong(MyCarActivity.this,"当前已是默认车辆");
                            return;
                        }

                        myDialog.setTitle("温馨提示");
                        myDialog.setMessage("是否设置当前车辆为默认车辆？");
                        myDialog.setYesOnclickListener("取消", new MyDialog.onYesOnclickListener() {
                            @Override
                            public void onYesOnclick() {
                                rb_check.setChecked(false);
                                myDialog.dismiss();

                            }
                        });
                        myDialog.setNoOnclickListener("确定", new MyDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                myDialog.dismiss();
                                HashMap<String, String> map = Global.getMap(MyCarActivity.this);
                                CustomerInfo customerInfo= BisManagerHandle.getInstance().getLocalUserInfo();

                                if(customerInfo!=null){
                                    map.put("userId", customerInfo.getId());
                                    map.put("carId", carList.get(position).getCarSn());
                                    myCarPresenter.changeDefaultCar(map);
                                }

                            }
                        });
                        myDialog.show();
                    break;
                }

            }
        });

    }

    @Override
    public void changeDefaultCarData(BaseResponse data) {

        T.showLong(this,data.getMsg());
        refData();
        //refData();
    }

    @Override
    protected MyCarPresenter initPresenter() {
        return myCarPresenter=new MyCarPresenter();
    }

    @Override
    protected void initData() {
        mTvTitle.setText("我的车辆");
        mTvBarRight.setVisibility(View.VISIBLE);
        mTvBarRight.setText("绑定");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        carList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BindCarActivity.class);
            }
        });
        refData();
    }
    public void refData(){
        HashMap<String, String> map = Global.getMap(this);
        CustomerInfo customerInfo= BisManagerHandle.getInstance().getLocalUserInfo();

        if(customerInfo!=null){
            map.put("userId", customerInfo.getId());
            myCarPresenter.queryCarListData(map);
        }
    }


    class CarAdapter extends BaseQuickAdapter<QueryCarList, BaseViewHolder> {

        Context context;
        Button btn_setting;
        private int flag;
        private RadioButton rb_check;

        public CarAdapter(Context _context,@Nullable List<QueryCarList> data) {
            super(R.layout.item_mycar, data);
            context=_context;
        }

        @Override
        protected void convert(BaseViewHolder helper, QueryCarList item) {
            helper.setText(R.id.text_carname, item.getCarPlate());
            Button btn_setting=helper.getView(R.id.btn_setting);
            rb_check=helper.getView(R.id.rb_check);
            helper.addOnClickListener(R.id.rb_check);
            if(item.getIsDefault().equals("1")){
                rb_check.setChecked(true);
            }else{
                rb_check.setChecked(false);
            }

            btn_setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,SettingCarNameActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString(Constant.BINDCARID,item.getCarSn()+"");
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            Button car_constrol=helper.getView(R.id.car_constrol);
            car_constrol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ManagerCarsActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString(Constant.CARID,item.getCarSn()+"");
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }
        @Override
        public int getItemViewType(int position) {

            flag=position;

            return super.getItemViewType(position);
        }

    }
}
