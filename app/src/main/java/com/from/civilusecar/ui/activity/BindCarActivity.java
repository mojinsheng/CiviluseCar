package com.from.civilusecar.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.from.civilusecar.R;
import android.view.View;
import android.widget.Toast;

import com.from.civilusecar.app.Global;
import com.from.civilusecar.app.State;
import com.from.civilusecar.base.BaseTitleActivity;
import com.from.civilusecar.bean.CustomerInfo;
import com.from.civilusecar.constant.Constant;
import com.from.civilusecar.control.BisManagerHandle;
import com.from.civilusecar.mvp.contract.BindConstract;
import com.from.civilusecar.mvp.presenter.BindPresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;
import com.xslong.xslonglib.utils.MD5;
import com.xslong.xslonglib.utils.T;
import com.xslong.xslonglib.utils.ValidUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yzq.zxinglibrary.bean.ZxingConfig;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BindCarActivity extends BaseTitleActivity<BindPresenter> implements BindConstract.View {
    private final static int REQUEST_CODE_SCAN = 1001;
    private String car_id;



    private BindPresenter bindPresenter;


    @BindView(R.id.img_or)
    ImageView img_or;

    @BindView(R.id.btn_bindcar)
    Button btn_bindcar;

    @BindView(R.id.et_carcode)
    EditText et_carcode;

    CustomerInfo customerInfo= BisManagerHandle.getInstance().getLocalUserInfo();


    @Override
    public void unbindData(BaseResponse data) {
        T.showLong(this,data.getMsg());
    }

    @Override
    protected int getSubLayoutId() {
        return R.layout.activity_bind;
    }

    @Override
    public void bindData(BaseResponse data) {

        if(data.getState()== State.SUCCESS){
            T.showLong(this,data.getMsg());
            finish();
        }else {
            T.showLong(this,data.getMsg());

        }

    }

    @Override
    protected BindPresenter initPresenter() {
        return bindPresenter=new BindPresenter();
    }

    @Override
    protected void initData() {
        mTvTitle.setText("绑定车辆");

    }

    @OnClick({R.id.img_or,R.id.btn_bindcar})
    public void OnClick(View view) {
        switch (view.getId()){
            case R.id.img_or :
                AndPermission.with(this)
                        .permission(com.yanzhenjie.permission.Permission.CAMERA, com.yanzhenjie.permission.Permission.READ_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent = new Intent(BindCarActivity.this, com.yzq.zxinglibrary.android.CaptureActivity.class);
                                /*ZxingConfig是配置类
                                 *可以设置是否显示底部布局，闪光灯，相册，
                                 * 是否播放提示音  震动
                                 * 设置扫描框颜色等
                                 * 也可以不传这个参数
                                 * */
                                ZxingConfig config = new ZxingConfig();
                                // config.setPlayBeep(false);//是否播放扫描声音 默认为true
                                //  config.setShake(false);//是否震动  默认为true
                                // config.setDecodeBarCode(false);//是否扫描条形码 默认为true
//                                config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
//                                config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
//                                config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
                                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描

                                startActivityForResult(intent, REQUEST_CODE_SCAN);
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Uri packageURI = Uri.parse("package:" + getPackageName());
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);

                                Toast.makeText(BindCarActivity.this, "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
                            }
                        }).start();
                break;
            case R.id.btn_bindcar :
               String carNo=et_carcode.getText().toString().trim();
                if (ValidUtils.isEmpty(carNo)) {
                    T.showShort(BindCarActivity.this, "设备号");
                    return;
                }
                customerInfo= BisManagerHandle.getInstance().getLocalUserInfo();
                if(customerInfo!=null){
                    //请求绑定的车辆请求
                    HashMap<String, String> map = Global.getMap(this);
                    map.put("userId", customerInfo.getId());
                    map.put("carId", carNo);
                    bindPresenter.bindCar(map);
                }
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN) {
            if (data != null) {
                //获取扫描结果
                Bundle bundle = data.getExtras();
                String result = data.getStringExtra(com.yzq.zxinglibrary.common.Constant.CODED_CONTENT);
                car_id = result.substring(result.lastIndexOf("=") + 1);
                if(customerInfo!=null){
                    //请求绑定的车辆请求
                    HashMap<String, String> map = Global.getMap(this);
                    map.put("userId", customerInfo.getId());
                    map.put("carId", car_id);
                    bindPresenter.bindCar(map);
                }
            }
        }
    }
}
