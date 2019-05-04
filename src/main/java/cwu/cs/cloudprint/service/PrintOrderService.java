package cwu.cs.cloudprint.service;

import cwu.cs.cloudprint.entity.PrintOrder;
import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.model.CreateOrderInfo;
import cwu.cs.cloudprint.model.PieData;

import java.util.List;

public interface PrintOrderService {

    /**
     * 获取所有信息在各个时段的统计结果
     * @return
     */
    int[] getBarData();

    /**
     * 统计打印类型
     * @return
     */
    List<PieData> getPieData();

    /**
     * 创建订单
     * @param orderInfo
     * @param user
     */
    void createOrder(CreateOrderInfo orderInfo, SystemUser user);

    /**
     * 生成订单
     * @param orderInfo
     * @param user
     */
    void saveOrder(CreateOrderInfo orderInfo, SystemUser user);

    /**
     * 获取当前用户的所有订单
     * @param userId
     * @return
     */
    List<PrintOrder> findByUserId(Integer userId);

    /**
     * 获取全部订单
     * @return
     */
    List<PrintOrder> getAllOrders();

    /**
     * 根据订单状态获取订单信息
     * @param orderStatus
     * @return
     */
    List<PrintOrder> findByOrderStatus(Integer orderStatus);

    /**
     * 获取非指定类型的订单列表
     * @param orderStatus
     * @return
     */
    List<PrintOrder> findByOrderStatusNot(Integer orderStatus);

    /**
     * 根据订单状态和紧急与否获取订单列表
     * @param orderStatus
     * @param urgentStatus
     * @return
     */
    List<PrintOrder> findByOrderStatusAndUrgentStatus(Integer orderStatus, Integer urgentStatus);
}
