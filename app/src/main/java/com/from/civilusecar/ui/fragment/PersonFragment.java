package com.from.civilusecar.ui.fragment;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.from.civilusecar.R;
import com.from.civilusecar.bean.CustomerInfo;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.constant.Constant;
import com.from.civilusecar.control.BisManagerHandle;
import com.from.civilusecar.serivce.ImagePathService;
import com.from.civilusecar.ui.activity.BindCarActivity;
import com.from.civilusecar.ui.activity.HeadActivity;
import com.from.civilusecar.ui.activity.LoginActivity;
import com.from.civilusecar.ui.activity.MyCarActivity;
import com.from.civilusecar.mvp.contract.MonitorConstranct;
import com.from.civilusecar.mvp.presenter.MonitorPresenter;
import com.from.civilusecar.ui.webView.WebViewActivity;
import com.from.civilusecar.ui.windon.MyDialog;
import com.from.civilusecar.utils.SPUtils;
import com.othershe.library.NiceImageView;
import com.squareup.picasso.Picasso;
import com.xslong.xslonglib.base.BaseFragment;
import com.xslong.xslonglib.base.bean.BaseResponse;

import butterknife.BindView;
import butterknife.OnClick;
import me.xiaopan.sketch.SketchImageView;


public class PersonFragment extends BaseFragment<MonitorPresenter> implements MonitorConstranct.View {

    @BindView(R.id.uc_rl_check_record)
    RelativeLayout uc_rl_check_record;


    @BindView(R.id.person_logo)
    NiceImageView person_logo;


    @BindView(R.id.text_phone)
    TextView text_phone;

    @BindView(R.id.rl_aboutus)
    RelativeLayout rl_aboutus;

    @BindView(R.id.rl_version)
    RelativeLayout rl_version;

    @Override
    public void queryCarListData(BaseResponse<QueryCarListsBean> data) {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_person;
    }

    @Override
    public MonitorPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initStatusBar(R.color.color_login_bg);


        }
    @OnClick({R.id.uc_rl_check_record,R.id.btn_logout,R.id.person_logo,R.id.rl_aboutus,R.id.rl_version})
    public void click(View view){
        switch (view.getId()) {
            case R.id.uc_rl_check_record :
                startActivity(MyCarActivity.class);
                break;
            case R.id.btn_logout :
                BisManagerHandle.getInstance().setLocalUserInfo(null);
               startActivity(LoginActivity.class);
               getActivity().finish();
                break;
            case R.id.person_logo:
                startActivity(HeadActivity.class);
                break;
            case R.id.rl_aboutus :
                startActivity(WebViewActivity.class);
                break;
            case R.id.rl_version :
                MyDialog myDialog;
                myDialog=new MyDialog(getContext());
                myDialog.setTitle("温馨提示");
                myDialog.setMessage("你已是最新版本");
                myDialog.setYesOnclickListener("取消", new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesOnclick() {

                        myDialog.dismiss();
                    }
                });
                myDialog.setNoOnclickListener("确定", new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {

                        myDialog.dismiss();
                    }
                });
                myDialog.show();
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        CustomerInfo customerInfo=BisManagerHandle.getInstance().getLocalUserInfo();
        text_phone.setText(SPUtils.getString(getActivity(), Constant.USERNAME));
        String url=new ImagePathService(customerInfo.getHeadImgUrl()).getImageUrl();
        Log.i("mojin","========displayImage===="+url);
//        person_logo.setZoomEnabled(true);
//        person_logo.setBlockDisplayLargeImageEnabled(true);
//        person_logo.setClickRetryOnDisplayErrorEnabled(true);
//        if(customerInfo!=null){
//            person_logo.displayImage(new ImagePathService(customerInfo.getHeadImgUrl()).getImageUrl());
//        }
        Picasso.with(getContext()).load(url)
                .placeholder(R.mipmap.bg_personal_img).error(R.mipmap.bg_personal_img)
                .fit().into(person_logo);
    }
}
