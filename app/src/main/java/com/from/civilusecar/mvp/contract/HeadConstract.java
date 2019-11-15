package com.from.civilusecar.mvp.contract;

import com.from.civilusecar.bean.ImgUrlListBean;
import com.xslong.xslonglib.base.BaseModel;
import com.xslong.xslonglib.base.BaseView;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface HeadConstract {
    //Model把视图view和后台耗时的页面，被Presenter调用
    public interface Model extends BaseModel {
        Observable<BaseResponse<ImgUrlListBean>> heads(HashMap<String, String> params, MultipartBody.Part body);

    }

    //把请求到的数据返回到View，在Presenter调用
    public interface View extends BaseView {
        void headsDate(BaseResponse<ImgUrlListBean> data);

    }

    //存储View中的对象封装到Map中，被View调用
    public interface Presenter {
        void headsDate(HashMap<String, String> params,MultipartBody.Part body);

    }
}
