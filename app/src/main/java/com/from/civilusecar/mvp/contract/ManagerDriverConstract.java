package com.from.civilusecar.mvp.contract;

import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.bean.UserByUserListBean;
import com.xslong.xslonglib.base.BaseModel;
import com.xslong.xslonglib.base.BaseView;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public interface ManagerDriverConstract {
    //Model把视图view和后台耗时的页面，被Presenter调用
    public interface Model extends BaseModel {
        Observable<BaseResponse<UserByUserListBean>> queryCarLists(HashMap<String, String> params);
        Observable<BaseResponse> unbindCar(HashMap<String, String> params);

    }

    //把请求到的数据返回到View，在Presenter调用
    public interface View extends BaseView {
        void queryCarListsData(BaseResponse<UserByUserListBean> data);
        void unbindData(BaseResponse data);

    }

    //存储View中的对象封装到Map中，被View调用
    public interface Presenter {
        void queryCarLists(HashMap<String, String> params);
        void unbindCar(HashMap<String, String> params);

    }
}
