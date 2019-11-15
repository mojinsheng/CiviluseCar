package com.from.civilusecar.ui.activity;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.from.civilusecar.R;
import com.from.civilusecar.app.Global;
import com.from.civilusecar.app.State;
import com.from.civilusecar.base.BaseTitleActivity;
import com.from.civilusecar.bean.CustomerInfo;
import com.from.civilusecar.bean.LoginEntity;
import com.from.civilusecar.constant.Constant;
import com.from.civilusecar.control.BisManagerHandle;
import com.from.civilusecar.mvp.contract.LoginContract;
import com.from.civilusecar.mvp.presenter.LoginPresenter;
import com.from.civilusecar.utils.SPUtils;
import com.xslong.xslonglib.base.bean.BaseResponse;
import com.xslong.xslonglib.utils.MD5;
import com.xslong.xslonglib.utils.T;
import com.xslong.xslonglib.utils.ValidUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseTitleActivity<LoginPresenter> implements LoginContract.View  {

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.btn_code)
    Button btn_code;

    @BindView(R.id.btn_time)
    Button btn_time;

    @BindView(R.id.et_phonenumber)
    EditText et_phonenumber;

    @BindView(R.id.et_code)
    EditText et_code;

    private  String phone="";
    private  String code="";

    private LoginPresenter loginPresenter;

    @Override
    public void returnLoginData(BaseResponse<LoginEntity> data) {

        if(data.getState()==State.SUCCESS){
            CustomerInfo loginEntity=data.getData().getCustomerInfo();
            if(loginEntity!=null){
                //保存用户登录数据
                BisManagerHandle.getInstance().setLocalUserInfo(loginEntity);
                SPUtils.putString(this, Constant.USERNAME, phone);
                startActivity(MainActivity.class);
                finish();
            }

        }else {

        }
        T.showLong(this,data.getMsg());

    }
    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            if(btn_time!=null||btn_code!=null){
                btn_time.setVisibility(View.VISIBLE);
                btn_code.setVisibility(View.GONE);
                btn_time.setText(String.format("%ss", String.valueOf(l / 1000)));
            }

        }

        @Override
        public void onFinish() {
            if(btn_time!=null||btn_code!=null) {
                btn_time.setVisibility(View.GONE);
                btn_code.setVisibility(View.VISIBLE);
            }
        }
    };
    @OnClick({R.id.btn_login,R.id.btn_code,R.id.btn_time})
    public void OnClick(View view){
        phone = et_phonenumber.getText().toString();
        code= et_code.getText().toString();
        switch (view.getId()) {
            case R.id.btn_login :
//              startActivity(MainActivity.class);
//              finish();

                if (!ValidUtils.isPhone(phone)) {
                    T.showShort(LoginActivity.this, "请输入有效的电话号码");
                    return;
                }
                if (ValidUtils.isEmpty(code)) {
                    T.showShort(LoginActivity.this, "请输入验证码");
                    return;
                }

                HashMap<String, String> map = Global.getMap(this);
                map.put("loginType", Constant.loginType+"");
                map.put("phoneNumber", phone);
                map.put("password", code);
                loginPresenter.login(map);

              break;
            case R.id.btn_code :
                 phone = et_phonenumber.getText().toString();
                if (!ValidUtils.isPhone(phone)) {
                    T.showShort(LoginActivity.this, "请输入有效的电话号码");
                    return;
                }
                getSmsCode(phone);
                break;
            case R.id.btn_time :




                break;
        }

    }

    @Override
    public void returnSMSData(BaseResponse data) {

        if(data.getState()== State.SUCCESS){
            T.showShort(this, "验证码已发送");
            countDownTimer.start();
        }

    }

    private void getSmsCode(String phone) {
        HashMap<String, String> map = Global.getMap(this);
        map.put("phoneNumber", phone);
        loginPresenter.smsCode(map);


    }
    @Override
    protected int getSubLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter initPresenter() {
        return loginPresenter=new LoginPresenter();
    }

    @Override
    protected void initData() {
        //隐藏标题
        //去除title
        mToolBar.setVisibility(View.GONE);
         String userName= SPUtils.getString(this,Constant.USERNAME);
         if(!TextUtils.isEmpty(userName)){
             et_phonenumber.setText(userName);
         }

         if(BisManagerHandle.getInstance().getLocalUserInfo()!=null){
             startActivity(MainActivity.class);
             finish();
         }

    }


}
