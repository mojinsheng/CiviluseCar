package com.from.civilusecar.bis;

import java.util.UUID;

/**
 * Created by Administrator on 2017/6/27.
 */

public class UUIDConstants {

    /**
     * 穿梭巴士
     */
    public interface ShuttleBus {
        /**
         * 穿梭巴士业务信息UUID
         */
        UUID BIS_INFO = UUID.fromString("40cab155-5249-484a-87f3-b4afee8f8700");
        /**
         * 穿梭巴士用户端主界面UUID
         */
        UUID USER_MAIN_ACTIVITY = UUID.fromString("62163f96-749d-449e-be49-4536777a5648");
        /**
         * 穿梭巴士司机端主界面UUID
         */
        UUID DRIVER_MAIN_ACTIVITY = UUID.fromString("d05a7d8e-c290-4ea8-841f-332b95cd936a");
        /**
         * 司机签到界面UUID
         */
        UUID DRIVER_SIGN_ACTIVITY = UUID.fromString("2590bf12-2845-5238-9fd4-65b858f8424a");
        /**
         * 司机日常检查界面UUID
         */
        UUID DRIVER_CHECK_ACTIVITY = UUID.fromString("ce067f01-639c-5ed2-8ce8-5c2ac8f62515");
    }

    /**
     * 免费公交
     */
    public interface CctBus {
        /**
         * 免费公交业务信息UUID
         */
        UUID BIS_INFO = UUID.fromString("d49a9c70-8fe3-4deb-9692-dc445886734c");
        /**
         * 免费公交主界面UUID
         */
        UUID MAIN_ACTIVITY = UUID.fromString("ff4012c9-1ed0-4bd2-86b1-8412d7828e9b");        /**
         * 免费公交旧版主界面UUID
         */
        UUID OLD_MAIN_ACTIVITY = UUID.fromString("040a9a0e-ea05-42ce-a2d9-d3d04185a8ee");
    }

    /**
     * 包车
     */
    public interface CharteredBus {
        /**
         * 包车业务信息UUID
         */
        UUID BIS_INFO = UUID.fromString("d1e7c425-3520-4546-a6ed-11370529d4e2");
        /**
         * 包车主界面UUID
         */
        UUID MAIN_ACTIVITY = UUID.fromString("89ffc12d-3f06-4e33-9081-fa9d89ba5f66");
        /**
         * 包车订单列表UUID
         */
        UUID ORDER_LIST = UUID.fromString("b6f14756-1658-4979-b56b-96fe9b2c824f");
    }

    /**
     * 保险
     */
    public interface Insurance {
        /**
         * 保险业务信息UUID
         */
        UUID BIS_INFO = UUID.fromString("216e468e-3400-4269-afd6-c9c5305f7beb");
        /**
         * 保险主界面UUID
         */
        UUID MAIN_ACTIVITY = UUID.fromString("66f5c841-7280-4899-a2c7-1e329ba2dca7");
    }

    /**
     * 物流
     */
    public interface Logistics {
        /**
         * 物流业务信息UUID
         */
        UUID BIS_INFO = UUID.fromString("610e9664-82eb-4b76-a2fb-1ff943981c83");
        /**
         * 物流主界面UUID
         */
        UUID MAIN_ACTIVITY = UUID.fromString("8f43a111-47a5-45b6-9f6a-6a1ec3ad0f44");
        /**
         * 物流发货车型列表UUID
         */
        UUID CAR_TYPE_LIST = UUID.fromString("cb34e9dd-b5be-4311-80c1-de2e670f3fd9");
        /**
         * 物流订单列表UUID
         */
        UUID ORDER_MGR = UUID.fromString("e8f7a275-11e2-4ed8-b0fa-453f7c468a61");
    }

    /**
     * 租车
     */
    public interface RentCar {

        /**
         * 业务信息UUID
         */
        UUID BIS_INFO = UUID.fromString("dd22a458-143b-4dd3-910c-a90ddcc4958a");
        /**
         * 租车主界面UUID
         */
        UUID MAIN_ACTIVITY = UUID.fromString("ebb495c2-33a0-43cf-98e3-86b784114bf9");
        /**
         * 租车订单列表UUID
         */
        UUID ORDER_LIST = UUID.fromString("37d37d5c-b996-4c89-a940-dda44cab4ba9");
        /**
         * 租车信息面板UUID
         */
        UUID CAR_INFO = UUID.fromString("d1c6aeae-1d43-4989-bd97-c80f2c7e4758");
    }

    /**
     * 专车
     */
    public interface SpecialCar {
        /**
         * 业务信息UUID
         */
        UUID BIS_INFO = UUID.fromString("6fe865a1-2f0b-4fbe-831f-844233d343cd");
        /**
         * 专车主界面UUID
         */
        UUID MAIN_ACTIVITY = UUID.fromString("97596b32-349c-427a-8bbe-d0f3f1847cc6");
        /**
         * 用户订单管理UUID
         */
        UUID ORDER_MGR = UUID.fromString("e11d7760-b2f8-4d48-9e53-b31b9e3e73ab");
        /**
         * 用车审批列表UUID
         */
        UUID APPROVE_LIST = UUID.fromString("d9aa5cb7-4395-4ebf-ab34-35f711f57bb2");
        /**
         * 司机订单列表UUID
         */
        UUID DRIVER_ORDER_LIST = UUID.fromString("e3e0ea8b-db20-4ce5-9e65-7c36b2c05e67");
        /**
         * 管理报告UUID
         */
        UUID MGR_REPORT = UUID.fromString("b7dd6e77-ff0a-4c63-8ecd-8135870e94c2");
        /**
         * 司机接单面板UUID
         */
        UUID DRIVER_ORDERS = UUID.fromString("dbdc6e48-d0b1-4ccd-950a-24d379dc9cbf");
        /**
         * 用户订单跟踪UUID
         */
        UUID USER_TRACK_ORDER = UUID.fromString("0352f48a-298b-4edc-b508-a08a7fc423c2");
    }
}
