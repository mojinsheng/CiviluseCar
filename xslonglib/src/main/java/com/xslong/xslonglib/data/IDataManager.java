package com.xslong.xslonglib.data;


/**
 * Created by Administrator on 2017/3/30.
 */

public interface IDataManager extends IManager,IPreferences {


    /**
     *
     * @param name
     * @return
     */
    IPreferences getPreferences(String name);
}
