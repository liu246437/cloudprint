package cwu.cs.cloudprint.service.impl;

import cwu.cs.cloudprint.constant.OrderConstant;
import cwu.cs.cloudprint.entity.PrintOrder;
import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.model.CreateOrderInfo;
import cwu.cs.cloudprint.model.PieData;
import cwu.cs.cloudprint.repository.PrintOrderRepository;
import cwu.cs.cloudprint.repository.SystemUserRepository;
import cwu.cs.cloudprint.service.PayService;
import cwu.cs.cloudprint.service.PrintOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@Transactional
public class PrintOrderServiceImpl implements PrintOrderService {

    @Autowired
    PrintOrderRepository printOrderRepository;

    @Autowired
    SystemUserRepository systemUserRepository;

    @Autowired
    PayService payService;

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

    /**
     * 创建订单
     * @param orderInfo
     * @param user
     */
    @Override
    public void createOrder(CreateOrderInfo orderInfo, SystemUser user){
        payService.subBalance(orderInfo, user);
        this.saveOrder(orderInfo, user);
    }

    /**
     * 更新订单状态：打印中-->已完成
     * @param id
     */
    @Override
    public void updateStatus(Integer id){

        Optional<PrintOrder> printOrderOptional = printOrderRepository.findById(id);
        if(printOrderOptional.isPresent()){
            PrintOrder printOrder = printOrderOptional.get();
            printOrder.setOrderStatus(OrderConstant.ORDER_STATUS_OVER);
            printOrderRepository.save(printOrder);
        }
    }

    /**
     * 生成订单
     * @param orderInfo
     * @param user
     */
    @Override
    public void saveOrder(CreateOrderInfo orderInfo, SystemUser user){

        Calendar calendar = Calendar.getInstance();
        PrintOrder printOrder = PrintOrder.builder().fileId(orderInfo.getFileId())
                .fileName(orderInfo.getFileName())
                .orderStatus(OrderConstant.ORDER_STATUS_SUBMIT)
                .printType(orderInfo.getPrintType())
                .payAmount(orderInfo.getPayAccount())
                .printCopies(orderInfo.getPrintCopies())
                .urgentStatus(orderInfo.getUrgentStatus())
                .printRemark(orderInfo.getPrintRemark())
                .userId(user.getId())
                .filePage(orderInfo.getFilePages())
                .createTime(calendar.getTime())
                .timeInterval(calendar.get(Calendar.HOUR_OF_DAY)).build();

        printOrderRepository.save(printOrder);
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

    /**
     * 获取用户已完成的订单
     * @param userId
     * @param orderStatus
     * @return
     */
    @Override
    public List<PrintOrder> findByUserIdAndAndOrderStatus(Integer userId, Integer orderStatus){
        return printOrderRepository.findByUserIdAndAndOrderStatus(userId, orderStatus);
    }

    /**
     * 获取用户未完成的订单
     * @param userId
     * @param orderStatus
     * @return
     */
    @Override
    public List<PrintOrder> findByUserIdAndAndOrderStatusNot(Integer userId, Integer orderStatus){
        return printOrderRepository.findByUserIdAndAndOrderStatusNot(userId, orderStatus);
    }


    /**
     * 创饼图单个value
     * @param result
     * @param type
     * @param name
     * @return
     */
    public PieData getPieData(int[] result, Integer type, String name){
        return PieData.builder().value(result[type]).name(name).build();
    }
}
