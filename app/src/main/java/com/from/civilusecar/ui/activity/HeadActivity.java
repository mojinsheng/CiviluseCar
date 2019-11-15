package com.from.civilusecar.ui.activity;

import android.view.View;

import com.from.civilusecar.R;
import com.from.civilusecar.app.Global;
import com.from.civilusecar.app.State;
import com.from.civilusecar.base.BaseTitleActivity;
import com.from.civilusecar.bean.CustomerInfo;
import com.from.civilusecar.bean.ImgUrlListBean;
import com.from.civilusecar.bean.ImgurlBean;
import com.from.civilusecar.constant.Constant;
import com.from.civilusecar.control.BisManagerHandle;
import com.from.civilusecar.mvp.contract.DriverConstract;
import com.from.civilusecar.mvp.contract.HeadConstract;
import com.from.civilusecar.mvp.presenter.DriverPresenter;
import com.from.civilusecar.mvp.presenter.HeadPresenter;
import com.from.civilusecar.serivce.ImagePathService;
import com.from.civilusecar.utils.SPUtils;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;
import com.xslong.xslonglib.utils.ImageUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import me.xiaopan.sketch.SketchImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HeadActivity extends BaseTitleActivity<HeadPresenter> implements HeadConstract.View,GalleryFinal.OnHanlderResultCallback{

    private HeadPresenter headPresenter;

    @BindView(R.id.img_head)
    SketchImageView img_head;
    private ImgurlBean imgurlBean;
    @Override
    protected int getSubLayoutId() {
        return R.layout.activity_head;
    }

    @Override
    public void headsDate(BaseResponse<ImgUrlListBean> data) {
        if(data.getState()== State.SUCCESS){
             imgurlBean=data.getData().getImgdata();
            CustomerInfo customerInfo=BisManagerHandle.getInstance().getLocalUserInfo();
            customerInfo.setHeadImgUrl(imgurlBean.getImgurl());
            BisManagerHandle.getInstance().setLocalUserInfo(customerInfo);

        }

    }

    @Override
    protected HeadPresenter initPresenter() {
        return headPresenter=new HeadPresenter();
    }

    @Override
    protected void initData() {
        mTvTitle.setText("头像");
        mTvBarRight.setVisibility(View.VISIBLE);
        mTvBarRight.setText("添加");
        mTvBarRight.setOnClickListener(view -> {
                ImagePathService.openPhotoPicker(this, false, 1, this);


        });
        CustomerInfo customerInfo= BisManagerHandle.getInstance().getLocalUserInfo();
        if(customerInfo.getHeadImgUrl()!=null){
            String url=new ImagePathService(customerInfo.getHeadImgUrl()).getImageUrl();
            img_head.displayImage(url);

        }
    }

    @Override
    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
        if (resultList == null || resultList.size() != 1) return;
        PhotoInfo photoInfo = resultList.get(0);
        img_head.displayImage(photoInfo.getPhotoPath());



        HashMap<String, String> map = Global.getMap(this);
        File file=new File(photoInfo.getPhotoPath());
        compress(file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(),  RequestBody.create(MediaType.parse("multipart/form-data"), file));
        headPresenter.headsDate(map,body);
    }

    @Override
    public void onHanlderFailure(int requestCode, String errorMsg) {

    }
    private void compress(File file) {
        if (file.length() > 200 * 1024) {
            String filePath = file.getAbsolutePath();
            ImageUtils.compressImage(filePath, filePath, 80);
        }
    }
}
