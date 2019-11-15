package com.from.civilusecar.mvp.presenter;

import com.from.civilusecar.mvp.contract.PersonContract;
import com.from.civilusecar.mvp.contract.WelcomeContract;
import com.xslong.xslonglib.base.BasePresenter;

import java.util.HashMap;

public class PersonPresent extends BasePresenter<PersonContract.View> implements PersonContract.Presenter {
    @Override
    public void getData(HashMap<String, String> params) {

    }
}
