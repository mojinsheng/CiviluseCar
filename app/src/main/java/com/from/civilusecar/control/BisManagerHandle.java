package com.from.civilusecar.control;

import com.from.civilusecar.bean.CustomerInfo;
import com.xslong.xslonglib.data.IDataManager;
import com.xslong.xslonglib.utils.L;

import java.io.Serializable;

public class BisManagerHandle implements Serializable {
    /**
     * 数据管理器
     */
    private IDataManager dataManager;

    /**
     * 当前用户
     */
    private CustomerInfo localUser;
    /**
     * 用户信息管理器
     */
    private CustomerInfo userManager;
    private static final class BisManagerHandleHolder {
        private static final BisManagerHandle INSTANCE = new BisManagerHandle();
    }

    private BisManagerHandle() {
    }

    public static final BisManagerHandle getInstance() {
        return BisManagerHandleHolder.INSTANCE;
    }

    /**
     * 数据管理器
     *
     * @return
     */
    public IDataManager getDataManager() {
        return dataManager;
    }

    /**
     * 数据管理器
     *
     * @param dataManager
     */
    public void setDataManager(IDataManager dataManager) {
        if (dataManager == null) {
            return;
        }
        this.dataManager = dataManager;
    }



    /**
     * 获取本地用户信息
     *
     * @return
     */
    public CustomerInfo getLocalUserInfo() {
        this.localUser = BisManagerHandle.getInstance().getDataManager().getSerializable("localUserInfo");
        if (this.localUser != null) {
            L.i(this.localUser.toString());
        }
        return this.localUser;
    }

    /**
     * 保存登录用户信息到本地
     *
     * @param userInfo 用户信息
     */
    public void setLocalUserInfo(CustomerInfo userInfo) {
        IDataManager dataManager = BisManagerHandle.getInstance().getDataManager();
        dataManager.getPreferences(null).save("localUserInfo", userInfo);
        //HttpRequest.init(userInfo.getToken(), String.valueOf(userInfo.getId()));
    }

}
