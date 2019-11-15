package com.from.civilusecar.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.from.civilusecar.R;
import com.from.civilusecar.app.Global;
import com.from.civilusecar.app.State;
import com.from.civilusecar.bean.CustomerInfo;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.bean.UserByCarBean;
import com.from.civilusecar.bean.UserByUserListBean;
import com.from.civilusecar.constant.Constant;
import com.from.civilusecar.control.BisManagerHandle;
import com.from.civilusecar.ui.adapter.DriverAdapter;
import com.from.civilusecar.base.BaseTitleActivity;
import com.from.civilusecar.base.CarBean;
import com.from.civilusecar.mvp.contract.ManagerDriverConstract;
import com.from.civilusecar.mvp.presenter.ManagerDriverPresenter;
import com.from.civilusecar.utils.SPUtils;
import com.xslong.xslonglib.base.bean.BaseResponse;
import com.xslong.xslonglib.utils.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class ManagerCarsActivity extends BaseTitleActivity<ManagerDriverPresenter> implements ManagerDriverConstract.View {

    @BindView(R.id.recycler_driver)
    RecyclerView recycler_driver;

    private DriverAdapter carAdapter;
    private ManagerDriverPresenter managerDriverPresenter;
     String car_id="";
     List<UserByCarBean> userList;
     private  CustomerInfo customerInfo;

    private List<CarBean> carBeanList = new ArrayList<>();
    @Override
    protected int getSubLayoutId() {
        return R.layout.activity_managerdriver;
    }

    @Override
    public void queryCarListsData(BaseResponse<UserByUserListBean> data) {
        userList=data.getData().getUserByCarBean();
        if(data.getState()== State.SUCCESS){
            carAdapter = new DriverAdapter(this,userList);
            carAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            recycler_driver.setLayoutManager(new LinearLayoutManager(this));
            recycler_driver.setAdapter(carAdapter);


            recycler_driver.addOnItemTouchListener(new OnItemChildClickListener() {
                @Override
                public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                }

                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    super.onItemChildClick(adapter, view, position);
                    int itemViewId = view.getId();

                    if(position==0){
                        Button btn_jiebang=view.findViewById(R.id.btn_jiebang);
                        Button btn_zhuyi=view.findViewById(R.id.btn_jiebang);
//                        btn_jiebang.setVisibility(View.GONE);
//                        btn_zhuyi.setVisibility(View.GONE);
                    }
                     switch (itemViewId){
                         case R.id.btn_jiebang :
                            // customerInfo= BisManagerHandle.getInstance().getLocalUserInfo();
                           //  phone=SPUtils.getString(ManagerCarsActivity.this, Constant.USERNAME);
                             HashMap<String, String> map = Global.getMap(ManagerCarsActivity.this);
                             Log.i("mojin",userList.get(position).getId()+"");
                             map.put("userId",userList.get(position).getId()+"");
                             map.put("carId", car_id);
                             map.put("phone",userList.get(position).getPhoneNumber());
                             managerDriverPresenter.unbindCar(map);
                             break;
                         case R.id.btn_zhuyi :
                             Bundle bundle=new Bundle();
                             bundle.putString(Constant.CARID,car_id);
                             startActivity(DriverActivity.class,bundle);
                             break;
                     }

                }
            });
        }

    }

    @Override
    public void unbindData(BaseResponse data) {
        if(data.getState()==State.SUCCESS){
            userList.clear();
            HashMap<String, String> map = Global.getMap(this);
            map.put("carId", car_id);
            map.put("userId", customerInfo.getId());
            managerDriverPresenter.queryCarLists(map);

        }

    }

    @Override
    protected ManagerDriverPresenter initPresenter() {
        return managerDriverPresenter=new ManagerDriverPresenter();
    }

    @Override
    protected void initData() {
        Bundle bundle=getIntent().getExtras();
        car_id=bundle.getString(Constant.CARID);
        mTvTitle.setText(car_id);
        mTvBarRight.setVisibility(View.GONE);
        mTvBarRight.setText("绑定");
        mTvBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(DriverActivity.class);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

         customerInfo=BisManagerHandle.getInstance().getLocalUserInfo();

        //请求绑定的车辆请求
        HashMap<String, String> map = Global.getMap(this);
        map.put("carId", car_id);
        map.put("userId", customerInfo.getId());
        managerDriverPresenter.queryCarLists(map);
    }
}
