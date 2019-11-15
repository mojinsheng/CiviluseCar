package com.from.civilusecar.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.flyco.tablayout.SegmentTabLayout;
import com.from.civilusecar.R;
import com.xslong.xslonglib.base.BaseActivity;
import com.xslong.xslonglib.base.BasePresenter;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author xslong
 */
public abstract class BaseTitleActivity<T extends BasePresenter> extends BaseActivity<T> implements View.OnClickListener {
    protected RelativeLayout mContainer;
    protected DrawerLayout mDrawer;
    protected Toolbar mToolBar;
    protected TextView mTvTitle;
    protected CircleImageView mTitleLeft;
    protected SegmentTabLayout mStTitle;
    protected TextView mTvBarRight;
    protected CircleImageView mImgHead;
    protected TextView mName;


    /**
     * 获取基础页面布局
     *
     * @return 布局id
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_base;
    }

    @Override
    protected void initView() {
        setTitleView();
        setContainerView();
        setWindowStatusBarColor(this, R.color.colorPrimary,R.color.welcome);
    }

    protected void setTitleView() {
   mToolBar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolBar);
        mContainer = (RelativeLayout) findViewById(R.id.container);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //关闭DrawLayout的手指滑动
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        mTitleLeft = findViewById(R.id.img_left);
        mTitleLeft.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);

        mTvBarRight = (TextView) findViewById(R.id.tv_bar_right);
        mTvBarRight.setOnClickListener(this);
    }
    public static void setWindowStatusBarColor(Activity activity, int colorResId, int colorId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                window.setNavigationBarColor(activity.getResources().getColor(colorId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setContainerView() {
        if (getSubLayoutId() != 0) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View contentView = inflater.inflate(getSubLayoutId(), null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            contentView.setLayoutParams(layoutParams);
            mContainer.addView(contentView);
        }
    }

    protected abstract int getSubLayoutId();

//    protected Dialog setDialog(Context activity, View contentView) {
//        final Dialog d = new Dialog(activity, R.style.MyTransparentDialog);
//        d.setContentView(contentView);
//        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
//        int dialogWidth = dm.widthPixels;
//        int dialogHeight = (int) (dm.heightPixels * 0.4);
//
//        Window window = d.getWindow();
//        window.setGravity(Gravity.BOTTOM);
////                window.getDecorView().setPadding(0, 0, 0, 0);
//        //设置显示动画
////                window.setWindowAnimations(R.style.main_menu_animstyle);
//        //设置显示位置
//        WindowManager.LayoutParams p = window.getAttributes(); //获取对话框当前的参数值
//        p.width = WindowManager.LayoutParams.MATCH_PARENT;
//        p.height = dialogHeight;
//        window.setAttributes(p);
//
//        d.setCanceledOnTouchOutside(false);
//        return d;
//    }

    //点击间隔时间
    private final long CLICK_DELAY_TIME = 2000;
    /**
     * 后退事件
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.order:
//                startActivity(MyOrderListActivity.class);
//                break;
//            case R.id.account:
//                startActivity(MyAccountActivity.class);
//                break;
//            case R.id.setting:
//                startActivity(SettingActivity.class);
//                break;
//            default:
//        }
//        mDrawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    protected void setBarRightVisible(int visible, String content) {
        mTvBarRight.setVisibility(visible);
        mTvBarRight.setText(content);
    }

    protected void setBarTitle(int visible, String content) {
        mTvTitle.setVisibility(visible);
        mTvTitle.setText(content);
    }

    protected void setBarTitle(String content) {
        mTvTitle.setVisibility(View.VISIBLE);
        mTvTitle.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                onBarLeftClick();
                break;
//            case R.id.tv_bar_right:
//                //下车
//                onBarRightClick();
//                break;
            default:
        }
    }

    protected void onBarRightClick() {

    }

    protected void onBarLeftClick() {
        finish();
    }
}
