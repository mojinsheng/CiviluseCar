package com.from.civilusecar.mvp.contract;

import com.xslong.xslonglib.base.BaseModel;
import com.xslong.xslonglib.base.BaseView;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public interface DriverConstract {
    //Model把视图view和后台耗时的页面，被Presenter调用
    public interface Model extends BaseModel {
        Observable<BaseResponse> shareCar(HashMap<String, String> params);
    }

    //把请求到的数据返回到View，在Presenter调用
    public interface View extends BaseView {
        void shareCarData(BaseResponse data);
    }

    //存储View中的对象封装到Map中，被View调用
    public interface Presenter {
        void shareCar(HashMap<String, String> params);
    }
}
