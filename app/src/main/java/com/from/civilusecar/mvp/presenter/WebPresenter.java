package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.mvp.contract.WebConstract;
import com.from.civilusecar.mvp.contract.WelcomeContract;
import com.xslong.xslonglib.base.BasePresenter;

import java.util.HashMap;

public class WebPresenter extends BasePresenter<WebConstract.View> implements WebConstract.Presenter {
    @Override
    public void webData(HashMap<String, String> params) {

    }
}
