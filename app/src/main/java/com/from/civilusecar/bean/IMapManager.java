package com.from.civilusecar.bean;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.RouteSearch;
import com.xslong.xslonglib.data.IManager;

import java.util.List;
/**
        * Created by Administrator on 2017/4/12.
        * 地图管理器
        */

public interface IMapManager extends IManager {

    /**
     * 常用地图应用包名
     */
    public interface MapAppPackageName {
        /**
         * 百度地图
         */
        String BAIDU_MAP = "com.baidu.BaiduMap";

        /**
         * 高德地图
         */
        String GAODE_MAP = "com.autonavi.minimap";

        /**
         * 谷歌地图
         */
        String GOOGLE_MAP = "com.google.android.apps.maps";
    }

    /**
     * 常用地图导航URI
     */
    public interface MapAppNavURI {
        /**
         * 百度地图
         */
//        String BAIDU_NAV_URI = "intent://map/direction?destination=latlng:%s,%s|name:%s&mode=driving&region=%s&src=%s#Intent;" +
//                "scheme=bdapp;package=com.baidu.BaiduMap;end";
        String BAIDU_NAV_URI = "baidumap://map/navi?location=%s,%s&query=%s";

        /**
         * 百度路线规划
         */
        String BAIDU_ROUTE_URI = "baidumap://map/direction?origin=latlng:%s,%s|name:%s&destination=latlng:%s,%s|name:%s&mode=%s&sy=0&index=0&target=1";

        /**
         * 高德地图
         */
        String GAODE_NAV_URI = "androidamap://navi?sourceApplication=%s&poiname=%s&lat=%s&lon=%s&dev=0";

        /**
         * 高德路线规划
         */
        String GAODE_ROUTE_URI = "amapuri://route/plan/?sid=&slat=%s&slon=%s&sname=%s&did=&dlat=%s&dlon=%s&dname=%s&dev=0&t=%s";

        /**
         * 谷歌地图
         */
        String GOOGLE_NAV_URI = "google.navigation:q=%s,%s,Sydney +Australia";

        /**
         * 谷歌路线规划
         */
        String GOOGLE_ROUTE_URI = "";
    }

    public enum RouteSearchType {
        DRIVE, BUS, WALK, RIDE
    }

}

