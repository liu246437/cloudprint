package cwu.cs.cloudprint.service.impl;

import cwu.cs.cloudprint.constant.OrderConstant;
import cwu.cs.cloudprint.entity.PrintOrder;
import cwu.cs.cloudprint.model.PieData;
import cwu.cs.cloudprint.repository.PrintOrderRepository;
import cwu.cs.cloudprint.service.PrintOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class PrintOrderServiceImpl implements PrintOrderService {

    @Autowired
    PrintOrderRepository printOrderRepository;

    /**
     * 获取所有信息在各个时段的统计结果
     * @return
     */
    @Override
    public int[] getBarData(){

        // 获取所有订单
        List<PrintOrder> printOrders = this.getAllOrders();
        // 返回结果
        int[] result = new int[24];
        for (PrintOrder printOrder : printOrders) {
            result[printOrder.getTimeInterval()]++;
        }
        return result;
    }

    /**
     * 统计打印类型
     * @return
     */
    @Override
    public List<PieData> getPieData(){

        // 获取所有订单
        List<PrintOrder> printOrders = this.getAllOrders();
        // 返回结果
        int[] statistics = new int[5];
        for (PrintOrder printOrder : printOrders) {
            statistics[printOrder.getPrintType()]++;
        }

        List<PieData> result = new ArrayList<>();
        result.add(getPieData(statistics, OrderConstant.PRINT_TYPE_BW_SINGLE, OrderConstant.PRINT_NAME_BW_SINGLE));
        result.add(getPieData(statistics, OrderConstant.PRINT_TYPE_BW_DOUBLE, OrderConstant.PRINT_NAME_BW_DOUBLE));
        result.add(getPieData(statistics, OrderConstant.PRINT_TYPE_CO_SINGLE, OrderConstant.PRINT_NAME_CO_SINGLE));
        result.add(getPieData(statistics, OrderConstant.PRINT_TYPE_CO_DOUBLE, OrderConstant.PRINT_NAME_CO_DOUBLE));

        return result;
    }

    public PieData getPieData(int[] result, Integer type, String name){
        return PieData.builder().value(result[type]).name(name).build();
    }

    /**
     * 获取当前用户的所有订单
     * @param userId
     * @return
     */
    @Override
    public List<PrintOrder> findByUserId(Integer userId){

        return printOrderRepository.findByUserId(userId);
    }

    /**
     * 获取全部订单
     * @return
     */
    @Override
    public List<PrintOrder> getAllOrders(){
        return printOrderRepository.findAll();
    }

    /**
     * 根据订单状态获取订单信息
     * @param orderStatus
     * @return
     */
    @Override
    public List<PrintOrder> findByOrderStatus(Integer orderStatus){

        return printOrderRepository.findByOrderStatus(orderStatus);
    }

    /**
     * 获取非指定类型的订单列表
     * @param orderStatus
     * @return
     */
    @Override
    public List<PrintOrder> findByOrderStatusNot(Integer orderStatus){

        return printOrderRepository.findByOrderStatusNot(orderStatus);
    }

    /**
     * 根据订单状态和紧急与否获取订单列表
     * @param orderStatus
     * @param urgentStatus
     * @return
     */
    @Override
    public List<PrintOrder> findByOrderStatusAndUrgentStatus(Integer orderStatus, Integer urgentStatus){

        return printOrderRepository.findByOrderStatusAndUrgentStatus(orderStatus, urgentStatus);
    }
}
