package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.api.RxObserver;
import com.from.civilusecar.bean.ImgUrlListBean;
import com.from.civilusecar.mvp.contract.DriverConstract;
import com.from.civilusecar.mvp.contract.HeadConstract;
import com.from.civilusecar.mvp.model.HeadModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.io.File;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HeadPresenter extends BasePresenter<HeadConstract.View> implements HeadConstract.Presenter {
    private HeadModel headModel;

    public HeadPresenter(){
         headModel=new HeadModel();
    }

    @Override
    public void headsDate(HashMap<String, String> params, MultipartBody.Part file) {
        headModel.heads(params,file).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivityProvider.<BaseResponse<ImgUrlListBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<ImgUrlListBean>>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse<ImgUrlListBean> baseResponse) {
                        mView.headsDate(baseResponse);
                    }
                });
    }
}
