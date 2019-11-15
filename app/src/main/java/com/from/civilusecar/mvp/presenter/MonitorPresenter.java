package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.api.RxObserver;
import com.from.civilusecar.bean.QueryCarListsBean;
import com.from.civilusecar.mvp.contract.MainConstract;
import com.from.civilusecar.mvp.contract.MonitorConstranct;
import com.from.civilusecar.mvp.model.MainModel;
import com.from.civilusecar.mvp.model.MonitorModel;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.xslong.xslonglib.base.BasePresenter;
import com.xslong.xslonglib.base.bean.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MonitorPresenter extends BasePresenter<MonitorConstranct.View> implements MonitorConstranct.Presenter {

    private final MonitorModel monitorModel;

    public MonitorPresenter() {
        monitorModel = new MonitorModel();
    }

    @Override
    public void queryCarList(HashMap<String, String> params) {
        monitorModel.queryCarList(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragmentProvider.<BaseResponse<QueryCarListsBean>>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new RxObserver<BaseResponse<QueryCarListsBean>>(mContext, mRxManage) {
                    @Override
                    protected void _onNext(BaseResponse<QueryCarListsBean> baseResponse) {
                        mView.queryCarListData(baseResponse);
                    }
                });
    }
}
