package cwu.cs.cloudprint.service.impl;

import cwu.cs.cloudprint.entity.PriceSet;
import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.model.CreateOrderInfo;
import cwu.cs.cloudprint.repository.PriceSetRepository;
import cwu.cs.cloudprint.repository.SystemUserRepository;
import cwu.cs.cloudprint.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PayServiceImpl implements PayService {

    @Autowired
    PriceSetRepository priceSetRepository;
    @Autowired
    SystemUserRepository systemUserRepository;

    /**
     * 就算支付价格
     * @param orderInfo
     * @param user
     * @return
     */
    @Override
    public CreateOrderInfo calPrice(CreateOrderInfo orderInfo, SystemUser user){

        Optional<SystemUser> currentUser = systemUserRepository.findById(user.getId());
        if(currentUser.isPresent()){
            user = currentUser.get();
        }

        // 获取价格信息
        PriceSet priceSet = this.findByUrgentStatusAndPrintType(
                orderInfo.getUrgentStatus(), orderInfo.getPrintType());

        // 设置支付价格
        orderInfo.setPayAccount(priceSet.getPayAmount()
                .multiply(new BigDecimal(orderInfo.getFilePages().toString())));

        // 设置余额不足的信息提示
        if(user.getRemainSum().compareTo(orderInfo.getPayAccount()) == -1){
            orderInfo.setErrmsg("账户余额不足，请联系管理员充值。");
        }

        return orderInfo;
    }

    /**
     * 根据打印类型及状态获取价格信息
     * @param urgentStatus
     * @param printType
     * @return
     */
    @Override
    public PriceSet findByUrgentStatusAndPrintType(Integer urgentStatus, Integer printType){

        return priceSetRepository.findByUrgentStatusAndPrintType(urgentStatus, printType);
    }
}
