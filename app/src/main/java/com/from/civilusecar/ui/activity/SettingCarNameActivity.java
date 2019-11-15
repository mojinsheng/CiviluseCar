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
import com.from.civilusecar.mvp.contract.SettingCarNameContract;
import com.from.civilusecar.mvp.model.SettingCarNamrModel;
import com.from.civilusecar.mvp.presenter.SettingCarNamePresenter;
import com.from.civilusecar.utils.SPUtils;
import com.xslong.xslonglib.base.bean.BaseResponse;
import com.xslong.xslonglib.utils.MD5;
import com.xslong.xslonglib.utils.T;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingCarNameActivity extends BaseTitleActivity<SettingCarNamePresenter> implements SettingCarNameContract.View {

    private SettingCarNamePresenter settingCarNamrModel;
    private CustomerInfo customerInfo;
    private String carId,phone;
    @BindView(R.id.ed_carname)
    EditText ed_carname;
    private String carname;

    @Override
    protected int getSubLayoutId() {
        return R.layout.activity_settingcarname;
    }

    @Override
    public void changeCarNameData(BaseResponse data) {
        T.showLong(this,data.getMsg());
        if(data.getState()== State.SUCCESS){
            finish();
        }
    }

    @Override
    public void unbindData(BaseResponse data) {
        T.showLong(this,data.getMsg());

    }

    @Override
    protected SettingCarNamePresenter initPresenter() {
        return settingCarNamrModel=new SettingCarNamePresenter();
    }

    @Override
    protected void initData() {
        mTvTitle.setText("编辑昵称");
        mTvBarRight.setVisibility(View.VISIBLE);
        mTvBarRight.setText("解绑");
        mTvBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerInfo=BisManagerHandle.getInstance().getLocalUserInfo();
                phone=SPUtils.getString(SettingCarNameActivity.this, Constant.USERNAME);
                HashMap<String, String> map = Global.getMap(SettingCarNameActivity.this);
                map.put("userId",customerInfo.getId());
                map.put("carId", carId);
                map.put("phone",phone);
                settingCarNamrModel.unbindCar(map);
            }
        });
        //Intent i =getIntent();
        Bundle data=getIntent().getExtras();
        carId=data.getString(Constant.BINDCARID);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.btn_chaneName})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_chaneName :
                carname=ed_carname.getText().toString().trim();
                HashMap<String, String> map = Global.getMap(SettingCarNameActivity.this);
                map.put("name",carname);
                map.put("carId", carId);
                settingCarNamrModel.changeCarName(map);
                break;
        }


    }

}
