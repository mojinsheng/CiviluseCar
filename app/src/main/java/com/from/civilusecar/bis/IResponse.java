/***********************************************************************
 * Module:  IResponse.java
 * Author:  Administrator
 * Purpose: Defines the Interface IResponse
 ***********************************************************************/

package com.from.civilusecar.bis;

import java.io.Serializable;

/**
 * 响应数据接口
 *
 * @pdOid 9c55efb5-6973-4651-9a49-3bc8be98bf01
 */
public interface IResponse extends Serializable {


    /**
     * 响应状态
     *
     * @return
     */
    int getState();

    /**
     * 响应描述
     *
     * @return
     */
    String getMsg();

    /**
     * 时间戳
     *
     * @return
     */
    long getSystemTime();


}