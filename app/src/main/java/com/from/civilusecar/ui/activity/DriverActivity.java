package com.from.civilusecar.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.from.civilusecar.R;
import com.from.civilusecar.app.Global;
import com.from.civilusecar.app.State;
import com.from.civilusecar.base.BaseTitleActivity;
import com.from.civilusecar.bean.CustomerInfo;
import com.from.civilusecar.constant.Constant;
import com.from.civilusecar.control.BisManagerHandle;
import com.from.civilusecar.mvp.contract.DriverConstract;
import com.from.civilusecar.mvp.presenter.DriverPresenter;
import com.from.civilusecar.utils.SPUtils;
import com.xslong.xslonglib.base.bean.BaseResponse;
import com.xslong.xslonglib.utils.T;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class DriverActivity extends BaseTitleActivity<DriverPresenter> implements DriverConstract.View {
    private DriverPresenter driverPresenter;

    @BindView(R.id.phonenumber)
    EditText phonenumber;

    private String phone;
    private String car_id;
    @Override
    protected int getSubLayoutId() {
        return R.layout.activity_driver;
    }

    @Override
    public void shareCarData(BaseResponse data) {
        T.showLong(this,data.getMsg());
        if(data.getState()== State.SUCCESS){
            finish();
        }
    }
    @OnClick({R.id.btn_sonphone})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_sonphone :
                phone=phonenumber.getText().toString().trim();
                HashMap<String, String> map = Global.getMap(this);
                map.put("phone", phone);
                map.put("carId", car_id);
                CustomerInfo customerInfo= BisManagerHandle.getInstance().getLocalUserInfo();
                map.put("userId", customerInfo.getId());

                driverPresenter.shareCar(map);

                break;
        }

    }
    @Override
    protected DriverPresenter initPresenter() {
        return driverPresenter=new DriverPresenter();
    }

    @Override
    protected void initData() {
        mTvTitle.setText("绑定子账号");
        Bundle bundle = getIntent().getExtras();
        car_id = bundle.getString(Constant.CARID);
    }
}
