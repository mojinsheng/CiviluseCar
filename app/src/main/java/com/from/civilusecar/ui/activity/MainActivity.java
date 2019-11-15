package com.from.civilusecar.ui.activity;




import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.from.civilusecar.R;
import com.from.civilusecar.base.BaseTitleActivity;
import com.from.civilusecar.ui.fragment.MainFragment;
import com.from.civilusecar.ui.fragment.MonitorFragment;
import com.from.civilusecar.ui.fragment.PersonFragment;
import com.from.civilusecar.mvp.presenter.MainPresenter;
import com.from.civilusecar.utils.statusbarutil.SystemBarTintManager;
import com.tbit.tbitblesdk.Bike.ResultCode;
import com.tbit.tbitblesdk.Bike.TbitBle;
import com.xslong.xslonglib.base.BaseActivity;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.utils.T;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
    private FragmentManager manager;
    RadioGroup radioGroup;
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

//    @Override
//    protected int getSubLayoutId() {
//        return;
//    }

//    @Override
//    public void getData(BaseResponse data) {
//
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_fragment, new MainFragment(), MainFragment.class.getSimpleName());
        transaction.commit();
        init();
        // initWindow();

        // setWindowStatusBarColor(this, R.color.welcome,R.color.welcome);
        checkPermissions();
    }
    private SystemBarTintManager tintManager;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (perms.size() == 5) {
//            mapService.init();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        T.showShort(this, "获取权限失败");
    }
    private void checkPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(this, permissions)) {

        } else {
            EasyPermissions.requestPermissions(this, "地图定位需要定位权限",
                    1000, permissions);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (TbitBle.getBleConnectionState() == ResultCode.SUCCEED) {
            TbitBle.disConnect();
            TbitBle.destroy();
        }
    }

    public void init() {
        //  mToolBar.setVisibility(View.GONE);
//        LinearLayout linearLayout=findViewById(R.id.drawer_layout);
//        linearLayout.setVisibility(View.GONE);
        //mContainer.setVisibility(View.GONE);
        manager = this.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        radioGroup = findViewById(R.id.linearLayout);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Fragment fragment = null;
                String tag = "";
                switch (i) {
                    case R.id.mainBtn:
                        //首页
                        fragment = new MainFragment();
                        tag = MainFragment.class.getSimpleName();
                        break;
                    case R.id.annualBtn:
                        //监控
                        fragment = new MonitorFragment();
                        tag = MonitorFragment.class.getSimpleName();
                        break;
                    case R.id.serviceBtn:
                        fragment = new PersonFragment();
                        tag = PersonFragment.class.getSimpleName();
                        //个人中心
                        break;
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(
                        R.anim.fragment_enter_go,
                        R.anim.fragment_exit_go,
                        R.anim.fragment_enter_back,
                        R.anim.fragment_exit_back);
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_fragment, fragment, tag);
                transaction.commitAllowingStateLoss();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }




}
