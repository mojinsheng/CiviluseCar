package com.from.civilusecar.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;


import com.from.civilusecar.bean.LoginEntity;
import com.xslong.xslonglib.utils.L;
import com.xslong.xslonglib.utils.MD5;
import com.xslong.xslonglib.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Desc : 全局数据
 */
public class Global {
    public static LoginEntity loginData;
    protected final static String APP_KEY = "b02r18o15a01d04o15c03e05a01n14e05v22o15p16";
    private static String PLATFORM="Android";
    private static Context context;

    public static HashMap<String, String> getMap(Context _context) {
        context=_context;
        HashMap<String, String> params = new HashMap<>();
        long timestamp = System.currentTimeMillis();
        if (loginData != null) {
            if(loginData.getCustomerInfo()!=null) {
                params.put("id", loginData.getCustomerInfo().getId());
            }
        } else {
            loginData=new LoginEntity();
            if(loginData.getCustomerInfo()!=null){
                params.put("id", loginData.getCustomerInfo().getId());

            }

        }
        params.put("timestamp", System.currentTimeMillis()+"");
        String nonceStr = createNonce();
        params.put("sig", createSign(String.valueOf(timestamp), nonceStr));

        params.put("randomNum", nonceStr);
        params.put("appVer",  Utils.getVersionName(context));
        params.put("platform", PLATFORM);
        params.put("osVer", String.format("Android %s", Build.VERSION.RELEASE));
        params.put("appVerNum",Utils.getVersionCode(context)+"");
        params.put("phoneBrand", Build.MODEL);
        return params;
    }

    public static HashMap<String, RequestBody> getQqMap(Context _context) {
        context=_context;
        HashMap<String, RequestBody> params = new HashMap<>();
        long timestamp = System.currentTimeMillis();
        if (loginData != null) {
            if(loginData.getCustomerInfo()!=null) {
                params.put("id", RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),loginData.getCustomerInfo().getId()));
            }
        } else {
            loginData=new LoginEntity();
            if(loginData.getCustomerInfo()!=null){
                params.put("id", RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),loginData.getCustomerInfo().getId()));

            }

        }
        params.put("timestamp", RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),System.currentTimeMillis()+""));
        String nonceStr = createNonce();
        params.put("sig", RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),createSign(String.valueOf(timestamp), nonceStr)));

        params.put("randomNum", RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),nonceStr));
        params.put("appVer",  RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),Utils.getVersionName(context)));
        params.put("platform", RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),PLATFORM));
        params.put("osVer", RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),String.format("Android %s", Build.VERSION.RELEASE)));
        params.put("appVerNum",RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),Utils.getVersionCode(context)+""));
        params.put("phoneBrand", RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"),Build.MODEL));
        return params;
    }

    public static HashMap<String, Object> getObjectMap(Context _context) {
        context=_context;
        HashMap<String, Object> params = new HashMap<>();
        long timestamp = System.currentTimeMillis();
        if (loginData != null) {
            if(loginData.getCustomerInfo()!=null) {
                params.put("id", loginData.getCustomerInfo().getId());
            }
        } else {
            loginData=new LoginEntity();
            if(loginData.getCustomerInfo()!=null){
                params.put("id",loginData.getCustomerInfo().getId());

            }

        }
        params.put("timestamp", System.currentTimeMillis()+"");
        String nonceStr = createNonce();
        params.put("sig", createSign(String.valueOf(timestamp), nonceStr));

        params.put("randomNum", nonceStr);
        params.put("appVer",  Utils.getVersionName(context));
        params.put("platform", PLATFORM);
        params.put("osVer", String.format("Android %s", Build.VERSION.RELEASE));
        params.put("appVerNum",Utils.getVersionCode(context)+"");
        params.put("phoneBrand", Build.MODEL);
        return params;
    }
    /**
     * 生成32位随机字符串
     *
     * @return
     */
    public static String createNonce() {
        String s = UUID.randomUUID().toString().replace("-", "");
        if (s.length() > 32) {
            s = s.substring(0, 32);
        }
        L.i(s);
        return s;
    }
 /**
     * 根据规则生成签名
     *
     * @param timestamp
     * @param nonceStr
     * @return
     */
    public static String createSign(String timestamp, String nonceStr) {
        if (timestamp == null) {
            timestamp = "";
        }
        if (nonceStr == null) {
            nonceStr = "";
        }
//        if (uid == null) {
//            uid = "";
//        }
//        if (token == null) {
//            token = "";
//        }
        L.i("timestamp=" + timestamp);
        L.i("nonceStr=" + nonceStr);
        //L.i("token=" + token);
        L.i("appKey=" + APP_KEY);
        String sign = timestamp + nonceStr + APP_KEY;
        L.i("待签名字串=" + sign);
        sign = MD5.getMD5(sign).toUpperCase();
        L.i("签名结果=" + sign);
        return sign;
    }


}
