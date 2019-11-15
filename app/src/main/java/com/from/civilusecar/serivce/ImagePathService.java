package com.from.civilusecar.serivce;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;

import com.from.civilusecar.api.Api;
import com.from.civilusecar.control.BisManagerHandle;
import com.from.civilusecar.ui.view.MenuDialog;
import com.from.civilusecar.ui.view.PullDownListView;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;

public class ImagePathService {

    String url;
    private static int OPEN_CANMER = 0;

    //private ICommonManager commonManager = BisManagerHandle.getInstance().getCommonManager();

    public ImagePathService(String url) {
        url = Api.BASE_URL_IMG + url;
        this.url = url.replace("\\", "/");
    }

    public String getImageUrl() {
        return url;
    }

    /**
     * 打开相片选择器
     *
     * @param context
     * @param multiselect 是否多选
     * @param max         多选最大数量，多选状态下有效
     * @param callback
     */
    public static void openPhotoPicker(final Context context, final boolean multiselect, final int max, final GalleryFinal.OnHanlderResultCallback callback) {
        new MenuDialog(context, new PullDownListView.OnItemClickListener() {
            @Override
            public void onItemClick(PullDownListView parent, View view, int position, boolean isSelected) {
                if (position == 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
                        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, OPEN_CANMER);
                            return;
                        } else {
                            showCamera(100, callback);
                        }
                    } else {
                        showCamera(100, callback);
                    }

                } else {
                    //打开相册
                    if (multiselect) {
                        GalleryFinal.openGalleryMuti(101, max, callback);
                    } else {
                        GalleryFinal.openGallerySingle(101, callback);
                    }
                }
            }
        }, "拍照", "相册") {
            @Override
            public int getGravity() {
                return Gravity.BOTTOM;
            }
        }.show();
    }
    /**
     * 调用相机
     *
     * @param requestCode
     * @param callback
     */
    public static void showCamera(int requestCode, GalleryFinal
            .OnHanlderResultCallback
            callback) {
        //打开相机
        FunctionConfig.Builder builder = new FunctionConfig.Builder();
        builder.setEnableEdit(false);
        builder.setEnableCrop(false);
        builder.setEnablePreview(false);
        builder.setEnableRotate(false);
        GalleryFinal.openCamera(requestCode, builder.build(), callback);
    }
}
