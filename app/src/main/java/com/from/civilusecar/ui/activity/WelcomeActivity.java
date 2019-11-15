package com.from.civilusecar.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.from.civilusecar.R;
import com.from.civilusecar.app.Global;
import com.from.civilusecar.app.State;
import com.from.civilusecar.bean.AdvertisementInfo;
import com.from.civilusecar.bean.AdvertisementList;
import com.from.civilusecar.mvp.contract.WelcomeContract;
import com.from.civilusecar.mvp.presenter.WelcomePresenter;
import com.from.civilusecar.serivce.ImagePathService;
import com.squareup.picasso.Picasso;
import com.xslong.xslonglib.base.BaseActivity;
import com.xslong.xslonglib.base.bean.BaseResponse;
import com.xslong.xslonglib.utils.T;
import com.xslong.xslonglib.utils.ValidUtils;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import me.militch.widget.bannerholder.BannerHolderView;
import me.militch.widget.bannerholder.BannerLoader;
import me.militch.widget.bannerholder.HolderAttr;

public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.View {

    @BindView(R.id.tv_count)
    TextView tvCount;

    @BindView(R.id.banner)
    BannerHolderView banner;

    //private WelcomePresenter welcomePresenter;

    private ScheduledExecutorService timmer;

//    @Override
//    protected int getSubLayoutId() {
//        return R.layout.activity_welcome;
//    }

    @Override
    protected WelcomePresenter initPresenter() {
        return new WelcomePresenter();
    }

    @Override
    protected void initData() {
        //隐藏标题
        //去除title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // mToolBar.setVisibility(View.GONE);

        timmer = Executors.newScheduledThreadPool(1);


            HashMap<String, String> map = Global.getMap(WelcomeActivity.this);
            map.put("advertType", "1");
            mPresenter.advert(map);



    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void returnWelcomeData(BaseResponse<AdvertisementList> data) {


        if(data.getState()== State.SUCCESS){
            if (data == null || data.getData().getAdvertisementList().size() < 1) {
                delay(false);
                return;
            }
            List<AdvertisementInfo> advertisementInfoList = data.getData().getAdvertisementList();
            delay(true);
            HolderAttr.builder()
                    .setBanners(advertisementInfoList)
                    //是否自动轮播
                    .setAutoLooper(true)
//                .setIndicatorResId(R.drawable.banner_indicator)
                    .setSwitchDuration(600)
                    .setLooperTime(3000)
                    //设置Banner加载器
                    .setBannerLoader(new BannerLoader() {
                        @Override
                        public void loadImage(Context context, Object path, View view) {
                            if (path instanceof AdvertisementInfo) {
                                AdvertisementInfo advertisementInfo = (AdvertisementInfo) path;
                                String url = ValidUtils.isEmpty(advertisementInfo.getAndroid()) ?
                                        advertisementInfo.getIphoneImgUrl()
                                        : advertisementInfo.getAndroid();
                                //String oo=new ImagePathService(url).getImageUrl();
                               String uril= new ImagePathService(url).getImageUrl();
                                Picasso.with(context).load(new ImagePathService(url).getImageUrl())
//                                    .placeholder(R.mipmap.bg_img_gd)
//                                    .error(R.mipmap.bg_img_gd)
                                        .fit().into((ImageView) view);
                                //delay(true);
                            }
                        }

                        @Override
                        public View createView(Context context) {
                            return new ImageView(context);
                        }
                    })
                    //绑定BannerHolder控件
                    .builder(banner).start();
        }else {
            T.showLong(this,data.getMsg());
        }
    }
    @OnClick({R.id.tv_count})
    void click() {
        toMain();
    }

    private int count = 5;

    private void delay(boolean flag) {
        if (!flag) {
            toMain();
            return;
        }
        tvCount.setVisibility(View.VISIBLE);
        timmer.scheduleAtFixedRate(() -> {
            if (--count < 0) {
                toMain();
            } else {
                runOnUiThread(() -> tvCount.setText(String.format("跳过|%sS", count)));

            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    private void toMain() {
        if (timmer != null) {
            timmer.shutdown();
            timmer = null;
        }
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


}
