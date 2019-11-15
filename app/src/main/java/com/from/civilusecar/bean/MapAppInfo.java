package com.from.civilusecar.bean;

import android.net.Uri;

import com.amap.api.services.core.LatLonPoint;
import com.xslong.xslonglib.utils.L;

public class MapAppInfo {
    private String packageName;
    private String appName;
    private String navUriString;
    private String routeUriString;
    private boolean isInstalled;


    public MapAppInfo(String packageName, String appName, String navUri, String routeUri) {
        this.packageName = packageName;
        this.appName = appName;
        this.navUriString = navUri;
        this.routeUriString = routeUri;
    }

    public String getPackageName() {
        return packageName == null ? "" : packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName == null ? "" : appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getNavUriString() {
        return navUriString == null ? "" : navUriString;
    }

    public void setNavUriString(String navUriString) {
        this.navUriString = navUriString;
    }

    public String getRouteUriString() {
        return routeUriString == null ? "" : routeUriString;
    }

    public void setRouteUriString(String routeUriString) {
        this.routeUriString = routeUriString;
    }

    public Uri getNaviUri(double latitude, double longitude, String sourceApp, String poiname, String city) {
        String uriStr = "";
        if (getNavUriString().equals(IMapManager.MapAppNavURI.GAODE_NAV_URI)) {
            uriStr = String.format
                    (getNavUriString(), sourceApp,
                            poiname, latitude == -1 ? "" : latitude, longitude == -1 ? "" : longitude);
        } else if (getNavUriString().equals(IMapManager.MapAppNavURI.BAIDU_NAV_URI)) {
            LatLonPoint latLonPoint = gaodeToBaidu(longitude, latitude);
            uriStr = String.format(getNavUriString(), latLonPoint.getLatitude(), latLonPoint.getLongitude(), poiname);
        } else if (getNavUriString().equals(IMapManager.MapAppNavURI.GOOGLE_NAV_URI)) {
            uriStr = String.format(getNavUriString(), latitude == -1 ? "" :
                    latitude, longitude == -1 ? "" : longitude);
        }
        L.i("uriStr = " + uriStr);
        return Uri.parse(uriStr);
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public void setInstalled(boolean installed) {
        isInstalled = installed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapAppInfo that = (MapAppInfo) o;

        return getPackageName() != null ? getPackageName().equals(that.getPackageName()) : that.getPackageName() == null;

    }

    @Override
    public int hashCode() {
        return getPackageName() != null ? getPackageName().hashCode() : 0;
    }

    @Override
    public String toString() {
        return appName;
    }

    public Uri getRouteUri(double startLatitude, double startLongitude, String startName, double endLatitude, double endLongitude, String endName, IMapManager.RouteSearchType type) {
        if (type == null) {
            type = IMapManager.RouteSearchType.DRIVE;
        }
        int routeGaodeType = 0;
        String routeBaiduType = "driving";
        switch (type) {
            case DRIVE:
                routeGaodeType = 0;
                routeBaiduType = "driving";
                break;
            case BUS:
                routeGaodeType = 1;
                routeBaiduType = "transit";
                break;
            case WALK:
                routeGaodeType = 2;
                routeBaiduType = "walking";
                break;
            case RIDE:
                routeGaodeType = 3;
                routeBaiduType = "riding";
                break;
        }
        String uriStr = "";
        if (getRouteUriString().equals(IMapManager.MapAppNavURI.GAODE_ROUTE_URI)) {
            uriStr = String.format
                    (getRouteUriString(), startLatitude == -1 ? "" : startLatitude, startLongitude == -1 ? "" : startLongitude, startName, endLatitude == -1 ? "" : endLatitude, endLongitude == -1 ? "" :
                            endLongitude, endName, routeGaodeType);
        } else if (getRouteUriString().equals(IMapManager.MapAppNavURI.BAIDU_ROUTE_URI)) {
            LatLonPoint startLatLonPoint = gaodeToBaidu(startLongitude, startLatitude);
            LatLonPoint endLatLonPoint = gaodeToBaidu(endLongitude, endLatitude);
            uriStr = String.format(getRouteUriString(), startLatLonPoint.getLatitude(), startLatLonPoint.getLongitude(), startName, endLatLonPoint.getLatitude(), endLatLonPoint.getLongitude(), endName,
                    routeBaiduType);
        } else if (getRouteUriString().equals(IMapManager.MapAppNavURI.GOOGLE_ROUTE_URI)) {
        }
        L.i("uriStr = " + uriStr);
        return Uri.parse(uriStr);
    }
    public LatLonPoint gaodeToBaidu(double longitude, double latitude) {
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = longitude, y = latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        double bdLongitude = z * Math.cos(theta) + 0.0065;
        double bdLatitude = z * Math.sin(theta) + 0.006;
        LatLonPoint latLonPoint = new LatLonPoint(0, 0);
        return new LatLonPoint(bdLatitude, bdLongitude);
    }

}
