package com.from.civilusecar.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;

import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.TruckRouteRestult;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.WalkStep;
import com.from.civilusecar.R;
import com.from.civilusecar.app.Global;
import com.from.civilusecar.bean.CustomerInfo;
import com.from.civilusecar.bean.QueryCarList;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.constant.Constant;
import com.from.civilusecar.control.BisManagerHandle;
import com.from.civilusecar.mvp.contract.MonitorConstranct;
import com.from.civilusecar.mvp.presenter.MonitorPresenter;
import com.from.civilusecar.serivce.MapManager;
import com.from.civilusecar.utils.SPUtils;
import com.xslong.xslonglib.base.BaseFragment;
import com.xslong.xslonglib.base.bean.BaseResponse;
import com.xslong.xslonglib.utils.ScreenUtils;
import com.xslong.xslonglib.utils.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import freemarker.template.utility.Constants;

import static android.graphics.BitmapFactory.decodeResource;


public class MonitorFragment extends BaseFragment<MonitorPresenter> implements MonitorConstranct.View ,
        CompoundButton.OnCheckedChangeListener,LocationSource,AMapLocationListener,AMap.InfoWindowAdapter{

    public static final String ROUTE_WAIK = "route_walk";
    @BindView(R.id.map)
    MapView mMapView;

//    @BindView(R.id.re_address)
//    RelativeLayout re_address;

//    @BindView(R.id.lmfd_iv_navi)
//    ImageView lmfd_iv_navi;

//    @BindView(R.id.text_address)
//    TextView text_address;
    MyLocationStyle myLocationStyle;
    MonitorPresenter monitorPresenter=null;
    private double myLatitude, myLongitude;
    private double carLatitude, carLongitude;

    private CustomerInfo customerInfo;
    private List<QueryCarList> carList;

    //定位需要的数据
    LocationSource.OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient=null;
    AMapLocationClientOption mLocationOption;
        RouteSearch.OnRouteSearchListener routeSearchListener=new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int code) {
                if (code == 1000) {
                    List<WalkPath> paths = walkRouteResult.getPaths();
                    PolylineOptions polylineOptions = new PolylineOptions();
                    for (WalkPath walkPath : paths) {
                        List<WalkStep> steps = walkPath.getSteps();
                        for (WalkStep walkStep : steps) {
                            List<LatLonPoint> latLonPoints = walkStep.getPolyline();
                            for (LatLonPoint point : latLonPoints) {
                                LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                                polylineOptions.add(latLng);
                            }
                        }
                    }
                    polylineOptions.width(ScreenUtils.dip2px(getContext(), 5))
                            .color(Color.parseColor("#00a9f7"));
                    Polyline polyline = aMap.addPolyline(polylineOptions);
                }

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        };
    private AMap aMap;
    //我的位置
    private ArrayList<Marker> repairMarkerList;
    private RouteSearch routeSearch;
    // 定义 Marker 点击事件监听
    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
        // marker 对象被点击时回调的接口
        // 返回 true 则表示接口已响应事件，否则返回false
        @Override
        public boolean onMarkerClick(Marker marker) {
            routeSearch = new RouteSearch(getContext());
            routeSearch.setRouteSearchListener(routeSearchListener);
            LatLonPoint from=new LatLonPoint(myLatitude,myLongitude);
            double lat=marker.getOptions().getPosition().latitude;
            double lng=marker.getOptions().getPosition().longitude;
            Log.i("mojin","============lng"+lng+",====lat"+lat);

            LatLonPoint to=new LatLonPoint(lat,lng);
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(from, to);
        RouteSearch.WalkRouteQuery query = null;
            query = new RouteSearch.WalkRouteQuery(fromAndTo,RouteSearch.WALK_DEFAULT);
        //开始算路
            routeSearch.calculateWalkRouteAsyn(query);
            return false;

        }
    };

    @Override
    public void queryCarListData(BaseResponse<QueryCarListsBean> data) {
        carList=data.getData().getCarList();
        if(carList!=null){
            initMarkerOptions(carList);
        }

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_monitor;
    }

    @Override
    public MonitorPresenter initPresenter() {
        return monitorPresenter=new MonitorPresenter();
    }

    protected void initData(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        //设置地图的放缩级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
//        aMap.setMyLocationType(AMap.LOCATION_TYPE_FOLLOW_NO_CENTER);
        aMap.setInfoWindowAdapter(this);//AMap类中
        aMap.setOnInfoWindowClickListener(listener);


        //蓝点初始化
        //蓝点初始化
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        //aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        Bitmap persionLocation = decodeResource(getContext().getResources(), R.mipmap.ic_pointer);
        Matrix matrix = new Matrix();
        matrix.postScale(1.5f, 1.5f);
        matrix.postRotate(-118);
        persionLocation = Bitmap.createBitmap(persionLocation, 0, 0, persionLocation.getWidth()
                , persionLocation.getHeight(), matrix, true);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(persionLocation);
        myLocationStyle.myLocationIcon(bitmapDescriptor);
        // 隐藏缩放按钮
        UiSettings mUiSettings;//定义一个UiSettings对象
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        // 禁用倾斜手势
        mUiSettings.setTiltGesturesEnabled(false);
        // 禁用旋转手势
        mUiSettings.setRotateGesturesEnabled(false);
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取
            }
        });
        // 绑定 Marker 被点击事件

        aMap.setOnMarkerClickListener(markerClickListener);

//        carLatitude=Double.parseDouble(SPUtils.getString(getContext(),Constant.CARLATITUDE));
//        carLongitude=Double.parseDouble(SPUtils.getString(getContext(),Constant.CARLONGITUDE));
        customerInfo= BisManagerHandle.getInstance().getLocalUserInfo();
        if(customerInfo!=null){
            //请求绑定的车辆请求
            HashMap<String, String> map = Global.getMap(getContext());
            map.put("userId", customerInfo.getId());
            monitorPresenter.queryCarList(map);
        }
    }

    @Override
    protected void initView() {
        initStatusBar(R.color.color_login_bg);

    }

    /**
     * 必须重写以下方法
     */
    @Override
    public void onResume(){
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    //--定位监听---------

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        }
        else {
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            Activity context=(Activity) monitorPresenter.mContext;
            //初始化定位
            mlocationClient = new AMapLocationClient(context.getApplicationContext());
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);

            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }

    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    //定位回调  在回调方法中调用“mListener.onLocationChanged(amapLocation);”可以在地图上显示系统小蓝点。
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                if (myLongitude == 0 || myLatitude == 0) { //第一次进入地图，首次定位成功时，移动屏幕并请求站点信息
                    mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                    myLatitude = aMapLocation.getLatitude();
                    myLongitude = aMapLocation.getLongitude();

                    LatLng myLatLng = new LatLng(myLatitude, myLongitude);
                    CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(myLatLng, 17f, 0, 0));
                    aMap.moveCamera(mCameraUpdate);

                }else{
                    myLatitude = aMapLocation.getLatitude();
                    myLongitude = aMapLocation.getLongitude();
                }
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("定位AmapErr", errText);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if(mMapView!=null){
            mMapView.onDestroy();

        }
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    //加载车辆位置
    public void initMarkerOptions(List<QueryCarList> carList){
        repairMarkerList = new ArrayList<>();

//        double lng=Double.parseDouble(SPUtils.getString(getContext(), Constant.CARLONGITUDE));
//        double lat=Double.parseDouble(SPUtils.getString(getContext(), Constant.CARLATITUDE));
//
//        Log.i("mojin","CARLONGITUDE："+lng+",CARLATITUDE"+lat);
//
//        LatLng latLng = new LatLng(lat,lng);
//
//        markerOption.position(latLng);
        for(int index = 0; index < carList.size(); index++) {
            MarkerOptions markerOption = new MarkerOptions();

            QueryCarList info = carList.get(index);
            if(!TextUtils.isEmpty(info.getLongitude())&&!TextUtils.isEmpty(info.getLatitude())){
                double lng=Double.parseDouble(info.getLongitude());
                double lat=Double.parseDouble(info.getLatitude());
                Log.i("mojin","CARLONGITUDE："+lng+",CARLATITUDE"+lat);
                LatLng latLng = new LatLng(lat, lng);
                markerOption.position(latLng);
            }




            markerOption.draggable(false);//设置Marker可拖动
            markerOption.visible(true).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),R.mipmap.diandian_flag)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果
            Marker rMarker = aMap.addMarker(markerOption);
            repairMarkerList.add(rMarker);
        }

//        Marker rMarker=aMap.addMarker(markerOption);
//        repairMarkerList.add(rMarker);
//        LatLng latLnga = new LatLng(lat,lng);
//        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLnga).title("北京").snippet("DefaultMarker"));
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = null;

        if(infoWindow == null) {
            infoWindow = LayoutInflater.from(getActivity()).inflate(
                    R.layout.layout_monitor, null);
        }
        TextView text_address=infoWindow.findViewById(R.id.text_address);
        GeocodeSearch geocoderSearch = new GeocodeSearch(getContext());
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                if(regeocodeResult.getRegeocodeAddress().getCity()!=null){
                    //re_address.setVisibility(View.VISIBLE);
                    text_address.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());

                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });

        LatLonPoint latLonPoint = new LatLonPoint(marker.getOptions().getPosition().latitude, marker.getOptions().getPosition().longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);

        //地图上的按钮
        ImageView btn_navigation = infoWindow.findViewById(R.id.lmfd_iv_navi);
        btn_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat=marker.getOptions().getPosition().latitude;
            double lng=marker.getOptions().getPosition().longitude;
            MapManager.navigation(getContext(),lat,lng,"神州行",null,null);
            }
        });
//        lmfd_iv_navi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                double lat=marker.getOptions().getPosition().latitude;
//                double lng=marker.getOptions().getPosition().longitude;
//                MapManager.navigation(getContext(),lat,lng,"神州行",null,null);
//            }
//        });

        render(marker, infoWindow);
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {



    }
    AMap.OnInfoWindowClickListener listener = new AMap.OnInfoWindowClickListener() {

        @Override
        public void onInfoWindowClick(Marker marker) {
//            double lat=marker.getOptions().getPosition().latitude;
//            double lng=marker.getOptions().getPosition().longitude;
//            MapManager.navigation(getContext(),lat,lng,"智能家居",null,null);

        }
    };
}
