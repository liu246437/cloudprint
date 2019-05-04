package cwu.cs.cloudprint.service;

import cwu.cs.cloudprint.entity.PriceSet;
import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.model.CreateOrderInfo;

public interface PayService {

    /**
     * 就算支付价格
     * @param orderInfo
     * @param user
     * @return
     */
    CreateOrderInfo calPrice(CreateOrderInfo orderInfo, SystemUser user);

    /**
     * 根据打印类型及状态获取价格信息
     * @param urgentStatus
     * @param printType
     * @return
     */
    PriceSet findByUrgentStatusAndPrintType(Integer urgentStatus, Integer printType);
}
