/***********************************************************************
 * Module:  ICallback.java
 * Author:  Administrator
 * Purpose: Defines the Interface ICallback
 ***********************************************************************/

package com.from.civilusecar.bis;


/**
 * Http请求回调接口
 *
 * @pdOid b16ccde8-502a-4a59-a0c8-6af2b7b038bc
 */
public abstract class ICallback<T extends IResponse> {
    /**
     * @pdOid 71e8e8e9-8fdd-4496-a99c-b02348193186
     */
    public abstract void onStart();

    /**
     * 请求失败回调方法
     *
     * @param exception
     */
    public abstract  void onFailure(Exception exception);

    /**
     * @param httpResponse
     */
    public abstract  void onSuccess(T httpResponse);

    /**
     * 取消请求时回调
     */
    public void onCancel(){

    }

}