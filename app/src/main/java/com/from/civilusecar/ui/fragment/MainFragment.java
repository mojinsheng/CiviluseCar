package com.from.civilusecar.ui.fragment;


import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.from.civilusecar.R;
import com.from.civilusecar.app.State;
import com.from.civilusecar.bean.BluetoothCmdInfoYidaBean;
import com.from.civilusecar.bean.CarInfo;
import com.from.civilusecar.bean.CarInfoList;
import com.from.civilusecar.bean.QueryCarList;
import com.from.civilusecar.bean.RefState;
import com.from.civilusecar.bean.RefreshCarBean;
import com.from.civilusecar.constant.Constant;
import com.from.civilusecar.serivce.ImagePathService;
import com.from.civilusecar.ui.activity.BindCarActivity;
import com.from.civilusecar.app.Global;
import com.from.civilusecar.bean.CustomerInfo;
import com.from.civilusecar.bean.FindCarBean;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.control.BisManagerHandle;
import com.from.civilusecar.mvp.contract.MainConstract;
import com.from.civilusecar.mvp.presenter.MainPresenter;
import com.from.civilusecar.ui.serivce.BlueSeriver;
import com.from.civilusecar.ui.windon.MyDialog;
import com.from.civilusecar.utils.SPUtils;
import com.squareup.picasso.Picasso;
import com.tbit.tbitblesdk.Bike.ResultCode;
import com.tbit.tbitblesdk.Bike.TbitBle;
import com.tbit.tbitblesdk.Bike.model.BikeState;
import com.tbit.tbitblesdk.Bike.services.command.callback.StateCallback;
import com.tbit.tbitblesdk.protocol.callback.ResultCallback;
import com.tbit.tbitblesdk.protocol.callback.RssiCallback;
import com.tbit.tbitblesdk.user.entity.W206State;
import com.xslong.xslonglib.base.BaseFragment;
import com.xslong.xslonglib.base.bean.BaseResponse;
import com.xslong.xslonglib.utils.T;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

import butterknife.BindView;
import butterknife.OnClick;


public class MainFragment extends BaseFragment<MainPresenter> implements MainConstract.View {


    private boolean isBind = false;

    private Timer timer;

    private static MainPresenter mainPresenter;
    //寻车
    @BindView(R.id.img_findcar)
    ImageView img_findcar;

    @BindView(R.id.main_closelogo)
    ImageView main_closelogo;

    @BindView(R.id.main_uplogo)
    ImageView main_uplogo;


    @BindView(R.id.diandianlogo)
    ImageView diandianlogo;

    //解锁
    @BindView(R.id.img_openlock)
    ImageView img_openlock;


    //启动
    @BindView(R.id.img_startup)
    ImageView img_startup;

    //关锁
    @BindView(R.id.img_colseup)
    ImageView img_colseup;


    //感应
    @BindView(R.id.img_induction)
    ImageView img_induction;

    @BindView(R.id.img_induction_focus)
    ImageView img_induction_focus;

    @BindView(R.id.text_vi)
    TextView text_vi;

    @BindView(R.id.text_go)
    TextView text_go;

    //电池电量
    @BindView(R.id.text_percentage)
    TextView text_percentage;

    @BindView(R.id.text_connect)
    TextView text_connect;

    @BindView(R.id.text_blueconnect)
    TextView text_blueconnect;

    //里程
    @BindView(R.id.text_kilometers)
    TextView text_kilometers;
    CarInfo carInfo = null;
    private CustomerInfo customerInfo = null;
    private BluetoothCmdInfoYidaBean bluetoothCmdInfoYidaBean;
    private int type;

    private static Handler handler = null;
    LocationManager locationManager = null;
    BluetoothAdapter bluetoothAdapter = null;

    /*
    获取车辆信息
     */
    @Override
    public void queryOrgCarDetailData(BaseResponse<CarInfoList> data) {
        //List<CarInfo> info=data.getData().getCarInfo();
        //T.showLong(getActivity(),info.getCarName());
        carInfo = data.getData().getCarInfo();

        if (data.getState() == State.SUCCESS && carInfo != null) {
            isBind = true;
            text_connect.setText("已连接");
            carInfo = data.getData().getCarInfo();
            SPUtils.putString(getContext(), Constant.CARID, carInfo.getCarSn());
            String url = new ImagePathService(carInfo.getCarTypeImgUrl()).getImageUrl();
            if (!TextUtils.isEmpty(carInfo.getSurplusPercent())) {
                DecimalFormat df = new DecimalFormat("0%");
                BigDecimal bigDecimal = new BigDecimal(carInfo.getSurplusPercent());
                text_percentage.setText(df.format(bigDecimal) + "");

            }

            if (!TextUtils.isEmpty(carInfo.getSurplusDistance())) {
                text_kilometers.setText(carInfo.getSurplusDistance() + "km");
            }
            Picasso.with(getContext()).load(url)
                    .placeholder(R.mipmap.diandian_transparent).error(R.mipmap.diandian_transparent)
                    .fit().into(diandianlogo);
            //查询车辆信息
            //获取蓝牙指令
            HashMap<String, String> btInfoMap = Global.getMap(getContext());
            btInfoMap.put("carId", carInfo.getCarSn() + "");
            btInfoMap.put("id", customerInfo.getId() + "");
            mainPresenter.getNewBtInfo(btInfoMap);
            //获取车辆数据
            HashMap<String, String> refredataMap = Global.getMap(getContext());
            refredataMap.put("id", customerInfo.getId() + "");
            refredataMap.put("carSn", carInfo.getCarSn() + "");
            mainPresenter.refreshCar(refredataMap);


        } else {
            showDialog();

        }


    }

    @OnClick({R.id.img_findcar, R.id.img_openlock, R.id.img_startup, R.id.img_colseup, R.id.img_induction,
            R.id.img_induction_focus})
    public void OnClick(View view) {
        switch (view.getId()) {

            case R.id.img_findcar:
                if (!isBind) {
                    showDialog();
                    return;
                }
                HashMap<String, String> map = Global.getMap(getContext());
                map.put("id", customerInfo.getId() + "");
                if (carInfo != null) {
                    map.put("carId", carInfo.getCarSn() + "");
                }
                map.put("type", 1 + "");
                //map.put("latitude", carList.get(0).getLatitude());
                mainPresenter.findCarData(map);
                break;
            case R.id.img_openlock:
                if (!isBind) {
                    showDialog();
                    return;
                }
                if (type == Constant.STARTLOCK) {
                    T.showLong(getContext(), "你已启动通电");
                    return;
                }
                type = Constant.OPNELOCK;

                HashMap<String, String> open_map = Global.getMap(getContext());
                open_map.put("id", customerInfo.getId() + "");
                if (carInfo != null) {
                    open_map.put("carId", carInfo.getCarSn() + "");
                }
                open_map.put("type", Constant.OPNELOCK + "");
                //map.put("latitude", carList.get(0).getLatitude());
                mainPresenter.doorControl(open_map);
                break;
            case R.id.img_startup:
                if (!isBind) {
                    showDialog();
                    return;
                }

                type = Constant.STARTLOCK;
                HashMap<String, String> start_map = Global.getMap(getContext());
                start_map.put("id", customerInfo.getId() + "");
                if (carInfo != null) {
                    start_map.put("carId", carInfo.getCarSn() + "");
                }
                start_map.put("type", Constant.STARTLOCK + "");
                //map.put("latitude", carList.get(0).getLatitude());
                mainPresenter.doorControl(start_map);
                break;
            case R.id.img_colseup:
                if (!isBind) {
                    showDialog();
                    return;
                }
                type = Constant.CLOSELOCK;
                HashMap<String, String> close_map = Global.getMap(getContext());
                close_map.put("id", customerInfo.getId() + "");
                if (carInfo != null) {
                    close_map.put("carId", carInfo.getCarSn() + "");
                }
                close_map.put("type", Constant.CLOSELOCK + "");
                //map.put("latitude", carList.get(0).getLatitude());
                mainPresenter.doorControl(close_map);
                break;
            case R.id.img_induction:
                if (!isBind) {
                    showDialog();
                    return;
                }
//                if (!bluetoothAdapter.isEnabled()) {
//                    bluetoothAdapter.enable();
//                    return;
//                }
                img_induction_focus.setVisibility(View.VISIBLE);
                text_go.setVisibility(View.VISIBLE);
                text_vi.setVisibility(View.GONE);
                img_induction.setVisibility(View.GONE);
                SPUtils.putString(getContext(), Constant.INDUCTION, Constant.INDUCTION_FLAG);
                if (!TextUtils.isEmpty(SPUtils.getString(getContext(), Constant.INDUCTION))) {
                    handler.postDelayed(runnable, 1000*2);
                    locationManager =
                            (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!bluetoothAdapter.isEnabled()) {
                        bluetoothAdapter.enable();
                        return;
                    }
                }
                break;
            case R.id.img_induction_focus:
                img_induction_focus.setVisibility(View.GONE);
                text_go.setVisibility(View.GONE);
                text_vi.setVisibility(View.VISIBLE);
                img_induction.setVisibility(View.VISIBLE);
                SPUtils.putString(getContext(), Constant.INDUCTION, "");
                break;

        }
    }


    //刷新车辆数据
    @Override
    public void refreshCarData(BaseResponse<RefreshCarBean> params) {
        if (params.getData().getCarData() == null) {
            T.showLong(getContext(), params.getMsg());
            return;
        }
        RefreshCarBean.RefreshCar refreshCar = params.getData().getCarData();

//        SPUtils.putString(getContext(), Constant.CARID, refreshCar.getCarId() + "");
//        SPUtils.putString(getContext(), Constant.CARLATITUDE, refreshCar.getLatitude() + "");
//        SPUtils.putString(getContext(), Constant.CARLONGITUDE, refreshCar.getLongitude() + "");

        int lockStatus = refreshCar.getLockStatus();
        int accStatus = refreshCar.getAccStatus();
        if (lockStatus == 1) {
            img_colseup.setImageResource(R.mipmap.diandian_close_lock);
            main_closelogo.setImageResource(R.mipmap.dinadian_close_lock);

        } else if (lockStatus == 0) {
            img_openlock.setImageResource(R.mipmap.diandian_open_lock);
            main_closelogo.setImageResource(R.mipmap.diandian_unlockx);
        }
        if (lockStatus == 0 && accStatus == 1) {
            img_startup.setImageResource(R.mipmap.diandian_startup);
        } else {
            img_startup.setImageResource(R.mipmap.diandian_startup_focus);

        }
    }

    @Override
    public void getNewBtInfoData(BaseResponse<RefState> data) {
        if (data.getState() == State.SUCCESS) {
            bluetoothCmdInfoYidaBean = data.getData().getRefState();
            if (!TextUtils.isEmpty(SPUtils.getString(getContext(), Constant.INDUCTION))) {
                /*
                获取蓝牙秘钥要判断是否启动蓝牙开锁，不然在启动也就显示蓝牙启动的权限
                 */
                locationManager =
                        (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                handler.postDelayed(runnable, 1000*2);
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();
                    return;
                }

            }

        }else {
            T.showLong(getContext(),data.getMsg());
        }

    }



    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!TextUtils.isEmpty(SPUtils.getString(getContext(), Constant.INDUCTION))) {
                handler.postDelayed(runnable, 1000*13);
            }
            //泰比特 接口
            TbitBle.connect(carInfo.getCarSn(), bluetoothCmdInfoYidaBean.getPwd(), new ResultCallback() {
                        @Override
                        public void onResult(int resultCode) {

                            T.showLong(getContext(),"蓝牙链接的状态码是："+resultCode);
                            Log.i("mojin","蓝牙链接的状态码是："+resultCode+",秘钥:"+bluetoothCmdInfoYidaBean.getPwd());
                            if (resultCode == ResultCode.SUCCEED) {
                                // 读取rssi(在已经连接的前提下)
                                text_blueconnect.setText("已连接");
                                TbitBle.readRssi(new ResultCallback() {
                                    @Override
                                    public void onResult(int resultCode) {
                                        // 状态

                                    }
                                }, new RssiCallback() {
                                    @Override
                                    public void onRssiReceived(int _rssi) {
                                        T.showLong(getContext(),"蓝牙链接的信号强度状态码是："+_rssi);

                                        // rssi
                                        int rssi = Math.abs(_rssi);

                                        if (rssi > 70 || rssi < 90) {
                                            // 解锁
                                            tbtBluetooothOpne();
                                        } else {
                                            //上锁
                                            tbtBluetooothclose();
                                        }

                                    }
                                });
                            }
                        }
                    }, null
            );
        }
    };
    /**
     * 泰比特蓝牙链接的回调
     *
     * @param type
     */
    ResultCallback resultCallback = new ResultCallback() {
        @Override
        public void onResult(int resultCode) {
            T.showLong(getContext(),"蓝牙链接的状态码是："+resultCode);

        }
    };

    //
    public void tbtBluetooothOpne() {
        // 解锁
        TbitBle.unlock(new ResultCallback() {
            @Override
            public void onResult(int resultCode) {
                // 解锁回应
                if (resultCode == ResultCode.SUCCEED) {
                    T.showShort(getActivity(), "开锁成功");
                    main_closelogo.setImageResource(R.mipmap.diandian_unlockx);
                    img_openlock.setImageResource(R.mipmap.diandian_open_lock_focus);
                    img_colseup.setImageResource(R.mipmap.diandian_close_lock_focus);
                    img_startup.setImageResource(R.mipmap.diandian_startup);
                } else {
                    main_closelogo.setImageResource(R.mipmap.dinadian_close_lock);
                    T.showShort(getActivity(), "开锁失败");

                }
            }
        });

    }

    public void tbtBluetooothclose() {
        TbitBle.lock(new ResultCallback() {
            @Override
            public void onResult(int resultCode) {
                // 上锁回应
                // 解锁回应
                if (resultCode == ResultCode.SUCCEED) {
                    T.showShort(getActivity(), "上锁成功");
                    main_closelogo.setImageResource(R.mipmap.dinadian_close_lock);
                    img_openlock.setImageResource(R.mipmap.diandian_open_lock_focus);
                    img_colseup.setImageResource(R.mipmap.diandian_close_lock);
                    img_startup.setImageResource(R.mipmap.diandian_startup_focus);
                } else {
                    T.showShort(getActivity(), "上锁失败");

                }

            }
        });
    }

    //返回绑定的车辆
    @Override
    public void queryCarListData(BaseResponse<QueryCarListsBean> data) {
        List<QueryCarList> carList = data.getData().getCarList();


    }

    //开锁返回数据
    @Override
    public void doorControlData(BaseResponse<FindCarBean> data) {
        T.showLong(getContext(), data.getMsg());
        if (data.getState() == State.SUCCESS) {
            if (type == Constant.OPNELOCK) {
                main_closelogo.setImageResource(R.mipmap.diandian_unlockx);
                img_openlock.setImageResource(R.mipmap.diandian_open_lock);
                img_colseup.setImageResource(R.mipmap.diandian_close_lock_focus);
                img_startup.setImageResource(R.mipmap.diandian_startup_focus);
            }
            if (type == Constant.CLOSELOCK) {
                main_closelogo.setImageResource(R.mipmap.dinadian_close_lock);
                img_openlock.setImageResource(R.mipmap.diandian_open_lock_focus);
                img_colseup.setImageResource(R.mipmap.diandian_close_lock);
                img_startup.setImageResource(R.mipmap.diandian_startup_focus);

            }
            if (type == Constant.STARTLOCK) {
                main_closelogo.setImageResource(R.mipmap.diandian_unlockx);
                img_openlock.setImageResource(R.mipmap.diandian_open_lock_focus);
                img_colseup.setImageResource(R.mipmap.diandian_close_lock_focus);
                img_startup.setImageResource(R.mipmap.diandian_startup);
            }
        }else{

            //网络启动失败，进行蓝牙开锁
            if (type == Constant.STARTLOCK) {
                locationManager =
                        (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();
                    return;
                }
                //泰比特 接口
                TbitBle.connect(carInfo.getCarSn(), bluetoothCmdInfoYidaBean.getPwd(), new ResultCallback() {
                            @Override
                            public void onResult(int resultCode) {

                                if (resultCode == ResultCode.SUCCEED) {
                                    text_blueconnect.setText("已连接");
                                    tbtBluetooothOpne();
                                }
                            }
                        }, null
                );

            }
            //网络开锁失败，进行蓝牙开锁
            if (type == Constant.CLOSELOCK) {
                locationManager =
                        (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();
                    return;
                }
                //泰比特 接口
                TbitBle.connect(carInfo.getCarSn(), bluetoothCmdInfoYidaBean.getPwd(), new ResultCallback() {
                            @Override
                            public void onResult(int resultCode) {

                                if (resultCode == ResultCode.SUCCEED) {
                                    text_blueconnect.setText("已连接");
                                    tbtBluetooothclose();
                                }
                            }
                        }, null
                );
            }



        }
    }

    @Override
    public void findCarData(BaseResponse<FindCarBean> data) {
        FindCarBean findCarBean = data.getData();
        if (findCarBean.getRefState().getRtCode().equals("0")) {
            T.showLong(getContext(), data.getMsg());
            img_findcar.setImageResource(R.mipmap.diandian_find_car);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    img_findcar.setImageResource(R.mipmap.diandian_find_car_focus);

                }
            }, 20 * 1000);
        } else {
            T.showLong(getContext(), data.getMsg());
        }


    }

    public void showDialog() {
        MyDialog myDialog;
        myDialog = new MyDialog(getContext());
        myDialog.setTitle("温馨提示");
        myDialog.setMessage("请先绑定车辆设备编号");
        myDialog.setYesOnclickListener("取消", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesOnclick() {

                myDialog.dismiss();
            }
        });
        myDialog.setNoOnclickListener("绑定", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                startActivity(BindCarActivity.class);

                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mian;
    }

    @Override
    public MainPresenter initPresenter() {
        return mainPresenter = new MainPresenter();
    }

    @Override
    protected void initView() {
        initStatusBar(R.color.color_login_bg);
        main_closelogo.setImageResource(R.mipmap.dinadian_close_lock);


        if (!TextUtils.isEmpty(SPUtils.getString(getContext(), Constant.INDUCTION))) {

            img_induction_focus.setVisibility(View.VISIBLE);
            text_go.setVisibility(View.VISIBLE);
            text_vi.setVisibility(View.GONE);
            img_induction.setVisibility(View.GONE);

        }


//        Intent intent = new Intent(getContext(), BlueSeriver.class);
//        getActivity().startService(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler = new Handler();

        if (carInfo == null) {

            customerInfo = BisManagerHandle.getInstance().getLocalUserInfo();
            if (customerInfo != null) {
                //请求绑定的车辆请求
                HashMap<String, String> map = Global.getMap(getContext());
                map.put("userId", customerInfo.getId() + "");
                mainPresenter.queryOrgCarDetail(map);
            }
  //          Log.i("mojin","22222222222222222222");

//            if (!TextUtils.isEmpty(SPUtils.getString(getContext(), Constant.INDUCTION))) {
//                /*
//                获取蓝牙秘钥要判断是否启动蓝牙开锁，不然在启动也就显示蓝牙启动的权限
//                 */
//                locationManager =
//                        (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
//                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//                handler.postDelayed(runnable, 1000*2);
//                if (!bluetoothAdapter.isEnabled()) {
//                    bluetoothAdapter.enable();
//                    return;
//                }
//
//            }
        }


    }

    @Override
    public void onStop() {
        super.onStop();

        if (mainPresenter != null) {
            Log.i("mojin","onDetach");
            mainPresenter.detachView();//销毁view
            mRxManager.cancelAllRequest();//取消网络请求
        }
    }
}
