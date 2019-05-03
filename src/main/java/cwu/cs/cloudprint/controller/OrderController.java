package cwu.cs.cloudprint.controller;

import cwu.cs.cloudprint.constant.OrderConstant;
import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.service.PrintOrderService;
import cwu.cs.cloudprint.verify.SystemUserVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Validated
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    PrintOrderService printOrderService;

    /**
     * 最新订单，只含有已提交状态的订单
     * @param session
     * @return
     */
    @GetMapping("submitList.do")
    public String submitList(Model model, HttpSession session, Integer type){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        /**
         * 根据type类型获取指定的list列表
         */
        if(type == OrderConstant.LAST_ALL){
            // 已提交
            model.addAllAttributes(printOrderService.findByOrderStatus(
                    OrderConstant.ORDER_STATUS_SUBMIT));
        }else if(type == OrderConstant.IS_URGENT){
            // 紧急已提交
            model.addAllAttributes(printOrderService.findByOrderStatusAndUrgentStatus(
                    OrderConstant.ORDER_STATUS_SUBMIT, OrderConstant.URGENT_STATUS_IS));
        }else if(type == OrderConstant.NOT_URGENT){
            // 非紧急已提交
            model.addAllAttributes(printOrderService.findByOrderStatusAndUrgentStatus(
                    OrderConstant.ORDER_STATUS_SUBMIT, OrderConstant.URGENT_STATUS_NOT));
        }

        return "admin/admin_Index";
    }

    /**
     * 订单管理页面，含有打印中和已完成的订单
     * @param session
     * @return
     */
    @GetMapping("printList.do")
    public String printList(Model model, HttpSession session, Integer type){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        /**
         * 根据type类型获取指定的list列表
         */
        if(type == OrderConstant.MANAGEMENT_ALL){
            // 打印中和已完成
            model.addAllAttributes(printOrderService.findByOrderStatusNot(OrderConstant.ORDER_STATUS_SUBMIT));
        }else if(type == OrderConstant.MANAGEMENT_PRINTING){
            // 打印中
            model.addAllAttributes(printOrderService.findByOrderStatus(OrderConstant.ORDER_STATUS_PRINTING));
        }else if(type == OrderConstant.MANAGEMENT_OVER){
            // 已完成
            model.addAllAttributes(printOrderService.findByOrderStatus(OrderConstant.ORDER_STATUS_OVER));
        }

        return "admin/admin_OrderManage";
    }

    /**
     * 获取当前用户订单
     * @return
     */
    @GetMapping("/myOrders.do")
    public String myOrders(Model model, HttpSession session){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        // 获取当前用户信息
        SystemUser user = SystemUserVerify.getCurrentUser(session);
        // 获取当前用户所有订单
        model.addAllAttributes(printOrderService.findByUserId(user.getId()));

        return "user/user_MyOrder";
    }
}