/**
 * newline
 * Utils
 * zhoushujie
 * 2016-7-29 下午3:36:05
 */
package com.xslong.xslonglib.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AppOpsManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;


/**
 *
 */
public class Utils {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 26;

    public static boolean requestCameraPermission(Activity activity) {
        boolean isOpenCamera = false;
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = PermissionChecker.checkSelfPermission(activity, Manifest.permission.CAMERA);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            } else {
                isOpenCamera = true;
            }

        } else {
            int checkPermission = checkPermission(activity, 26);
            if (checkPermission == AppOpsManager.MODE_ALLOWED) {
                isOpenCamera = true;
            } else if (checkPermission == AppOpsManager.MODE_IGNORED) {
                isOpenCamera = false;
            }
        }
        return isOpenCamera;
    }

    /**
     * 反射调用系统权限,判断权限是否打开
     *
     * @param permissionCode 相应的权限所对应的code
     * @see {@link AppOpsManager }
     */
    private static int checkPermission(Activity activity, int permissionCode) {
        int checkPermission = 0;
        if (Build.VERSION.SDK_INT >= 19) {
            AppOpsManager _manager = (AppOpsManager) activity.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class<?>[] types = new Class[]{int.class, int.class, String.class};
                Object[] args = new Object[]{permissionCode, Binder.getCallingUid(), activity.getPackageName()};
                Method method = _manager.getClass().getDeclaredMethod("noteOp", types);
                method.setAccessible(true);
                Object _o = method.invoke(_manager, args);
                if ((_o instanceof Integer)) {
                    checkPermission = (Integer) _o;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            checkPermission = 0;
        }
        return checkPermission;
    }

    public static void requestLocationPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    100);//自定义的code
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    101);//自定义的code
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS},
                    102);//自定义的code
        }
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    public static void requestStoragePermissions(Activity activity) {
// Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
// We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }


    /**
     * 获取应用包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            // 当前版本的包名
            String packageNames = info.packageName;
            return packageNames;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 复制文本到剪切板
     *
     * @param context
     * @param content
     */
    @SuppressWarnings("deprecation")
    public static void copy(Context context, String content) {
        // 得到剪贴板管理器
        if (Build.VERSION.SDK_INT < 11) {
            android.text.ClipboardManager cmb = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(content.trim());
        } else {
            android.content.ClipboardManager cmb = (android.content.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setPrimaryClip(ClipData.newPlainText("text", content));
        }
        T.showLong(context, "“" + content + "”已复制到剪切版！");
    }

    /**
     * Hides the input method.
     *
     * @param context context
     * @param view    The currently focused view
     * @return success or not.
     */
    public static boolean hideInputMethod(Context context, View view) {
        if (context == null || view == null) {
            return false;
        }

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        return false;
    }

    /**
     * Show the input method.
     *
     * @param context context
     * @param view    The currently focused view, which would like to receive soft
     *                keyboard input
     * @return success or not.
     */
    public static boolean showInputMethod(Context context, View view) {
        if (context == null || view == null) {
            return false;
        }

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.showSoftInput(view, 0);
        }

        return false;
    }

    /**
     * 获取应用本地版本代码
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        try {
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            L.e(e.getMessage());
        }
        return versionCode;
    }

    /**
     * 获取应用本地版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            L.e(e.getMessage());
        }
        return versionName;
    }

    /**
     * 替换"null"字符串
     *
     * @param oldStr
     * @param newStr
     * @return
     */
    public static String replaceNullString(String oldStr, String newStr) {
        if ((!TextUtils.isEmpty(oldStr)) && oldStr.toLowerCase().equals("null")) {
            return newStr == null ? "" : newStr;
        }
        return oldStr;
    }

    /**
     * 替换NaN
     *
     * @param d
     * @return 0
     */
    public static double replaceNaNDouble(double d) {
        if (Double.isNaN(d)) {
            return 0;
        }
        return d;
    }

    /**
     * 替换NaN
     *
     * @param f
     * @return 0
     */
    public static float replaceNaNFloat(float f) {
        if (Float.isNaN(f)) {
            return 0;
        }
        return f;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param context
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context context, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(Integer.MAX_VALUE);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param activityName 某个界面名称
     */
    public static boolean isActivityForeground(Context context, String activityName) {
        if (context == null || TextUtils.isEmpty(activityName)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (activityName.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isApkIntalled(Context context, String packageName) {
        if (packageName == null || "".equals(packageName.trim())) {
            return false;
        }
        final PackageManager packageManager = context.getPackageManager();//获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
        for (int i = 0; i < pinfo.size(); i++) {
            if (packageName.equals(pinfo.get(i).packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 打开应用商店
     *
     * @param context
     * @param packageName 查询指定包名的应用
     */
    public static void openMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 判断 APP是否运行
     *
     * @param context
     * @return
     */
    public static boolean isAppForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }

    /**
     * 判断是否手机号码
     *
     * @param phone
     * @return
     */
    public static boolean isModilePhone(String phone) {
        return phone.matches("1\\d{10}");
    }

    public static boolean isCarPlate(String carPlate) {
        return carPlate.matches("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}([A-Z0-9挂学警港澳]{1}|[A-Z0-9]{2})$");
    }

    /**
     * 获取异常信息
     *
     * @param e
     * @return
     */
    public static String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e2) {
            return "ErrorInfoFromException";
        }
    }

    public static Uri getUri(Context context, String id, File file) {
        Uri uri;
//        // 小米
//        if (RomUtils.isMiui()) {
//            uri = FileProvider.getUriForFile(context, id + ".fileProvider", file);
//        }
//        // 华为
//        else if (RomUtils.isEmui()) {
//            uri = FileProvider.getUriForFile(context, id + ".fileProvider", file);
//        } else {
//            if (Build.VERSION.SDK_INT >= 24) {
//                uri = FileProvider.getUriForFile(context, id + ".fileProvider", file);
//            } else {
//                uri = Uri.fromFile(file);
//            }
//        }
        //2018-11-12注释：小米手机 低版本系统也采用Android N的安装方式，所以这里统一处理，不在区分版本
   //    if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, id + ".fileProvider", file);
//        } else {
//            uri = Uri.fromFile(file);
//        }
        return uri;
    }

    public static void main(String[] args) {
        System.out.printf(isCarPlate("粤T1234AB") + "");
    }
}
