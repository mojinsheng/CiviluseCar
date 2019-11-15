package com.from.civilusecar.serivce;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;

import com.from.civilusecar.bean.IMapManager;
import com.from.civilusecar.bean.MapAppInfo;
import com.from.civilusecar.ui.view.MenuDialog;
import com.from.civilusecar.ui.view.PullDownListView;
import com.xslong.xslonglib.utils.T;
import com.xslong.xslonglib.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MapManager {
    /**
     * 打开地图导航（目前仅支持高德、百度、谷歌）
     *
     * @param context
     * @param latitude  纬度
     * @param longitude 经度
     * @param sourceApp 源APP名称
     * @param poiname   地点名称
     * @param city      城市
     */
    public static void navigation(final Context context, final double latitude, final double longitude, final String sourceApp, final String poiname, final String city) {
        List<MapAppInfo> list = getMapAppList(context);
        if (list.isEmpty()) {
            T.showShort(context, "您手机未安装地图应用，目前仅支持百度地图、高德地图和谷歌地图");
            return;
        }
        new MenuDialog<MapAppInfo>(context, new PullDownListView.OnItemClickListener<MapAppInfo>() {
            @Override
            public void onItemClick(PullDownListView<MapAppInfo> parent, View view, int position, boolean isSelected) {
                MapAppInfo item = parent.getItem(position);
                if (item.isInstalled()) {
                    Uri uri = item.getNaviUri(latitude, longitude, sourceApp, poiname, city);
                    if (uri != null) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setPackage(item.getPackageName());
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            T.showShort(context, item.getAppName() + "打开异常");
                        }
                    } else {
                        T.showShort(context, item.getAppName() + "未安装");
                        Utils.openMarket(context, item.getPackageName());
                    }
                } else {
                    T.showShort(context, item.getAppName() + "未安装");
                    Utils.openMarket(context, item.getPackageName());
                }
            }
        }, list) {
            @Override
            public int getGravity() {
                return Gravity.BOTTOM;
            }
        }.show();
    }
    /**
     * 获取地图应用列表（目前仅支持高德、百度、谷歌）
     *
     * @param context
     * @return
     */
    public static List<MapAppInfo> getMapAppList(Context context) {
        List<MapAppInfo> list = new ArrayList<MapAppInfo>();
        list.add(new MapAppInfo(MapAppPackageName.GAODE_MAP, "高德地图", IMapManager.MapAppNavURI.GAODE_NAV_URI, IMapManager.MapAppNavURI.GAODE_ROUTE_URI));
        list.add(new MapAppInfo(MapAppPackageName.BAIDU_MAP, "百度地图", IMapManager.MapAppNavURI.BAIDU_NAV_URI, IMapManager.MapAppNavURI.BAIDU_ROUTE_URI));
        list.add(new MapAppInfo(MapAppPackageName.GOOGLE_MAP, "谷歌地图", IMapManager.MapAppNavURI.GOOGLE_NAV_URI, IMapManager.MapAppNavURI.GOOGLE_ROUTE_URI));
        for (int i = list.size() - 1; i >= 0; i--) {
            boolean isInstalled = Utils.isApkIntalled(context, list.get(i).getPackageName());
            list.get(i).setInstalled(isInstalled);
            if (!isInstalled) {
                list.remove(i);
            }
        }
        return list;
    }
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
}
