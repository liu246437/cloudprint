package cwu.cs.cloudprint.controller;

import cwu.cs.cloudprint.entity.PriceSet;
import cwu.cs.cloudprint.entity.PrintOrder;
import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.model.CreateOrderInfo;
import cwu.cs.cloudprint.service.PayService;
import cwu.cs.cloudprint.service.PrintOrderService;
import cwu.cs.cloudprint.verify.SystemUserVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Validated
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    PayService payService;

    @Autowired
    PrintOrderService printOrderService;

    /**
     * 计算价格跳转支付页面
     * @return
     */
    @GetMapping("/cal.do")
    public String calPrice(Model model, HttpSession session, CreateOrderInfo orderInfo){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        // 获取当前用户
        SystemUser user = SystemUserVerify.getCurrentUser(session);
        // 获取订单信息
        CreateOrderInfo createOrderInfo = payService.calPrice(orderInfo, user);
        model.addAttribute(createOrderInfo);

        return "user/user_Pay";
    }

    @PostMapping("/pay.do")
    public String cal(HttpSession session, CreateOrderInfo orderInfo){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        // 获取当前用户
        SystemUser user = SystemUserVerify.getCurrentUser(session);
        printOrderService.createOrder(orderInfo, user);

        return "redirect:/order/myOrders.do";
    }
}
