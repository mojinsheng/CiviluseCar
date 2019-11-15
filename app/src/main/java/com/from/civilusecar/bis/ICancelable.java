package com.from.civilusecar.bis;

/**
 * Created by Administrator on 2017/4/8.
 * 可撤消的
 */

public interface ICancelable {

    /**
     * 撤消请求
     */
    void cancel();
}
