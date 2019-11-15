package com.from.civilusecar.bis;

/**
 * Created by Administrator on 2017/4/10.
 * 进度回调
 */

public abstract class IProgressCallback<T> extends ICallback {

    /**
     * 响应进度更新
     */
    public abstract void onProgress(long total, long current);

}
