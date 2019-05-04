package cwu.cs.cloudprint.controller;

import cwu.cs.cloudprint.constant.OrderConstant;
import cwu.cs.cloudprint.entity.PrintOrder;
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
import java.util.List;

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
        List<PrintOrder> orders;

        if(type == null || type == OrderConstant.LAST_ALL){
            // 已提交
            orders = printOrderService.findByOrderStatus(
                    OrderConstant.ORDER_STATUS_SUBMIT);
        }else if(type == OrderConstant.IS_URGENT){
            // 紧急已提交
            orders = printOrderService.findByOrderStatusAndUrgentStatus(
                    OrderConstant.ORDER_STATUS_SUBMIT, OrderConstant.URGENT_STATUS_IS);
        }else if(type == OrderConstant.NOT_URGENT){
            // 非紧急已提交
            orders = printOrderService.findByOrderStatusAndUrgentStatus(
                    OrderConstant.ORDER_STATUS_SUBMIT, OrderConstant.URGENT_STATUS_NOT);
        }else {
            // 已提交
            orders = printOrderService.findByOrderStatus(
                    OrderConstant.ORDER_STATUS_SUBMIT);
        }

        model.addAttribute("orders", orders);

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
        List<PrintOrder> orders;

        if(type ==null || type == OrderConstant.MANAGEMENT_ALL){
            // 打印中和已完成
            orders = printOrderService.findByOrderStatusNot(OrderConstant.ORDER_STATUS_SUBMIT);
        }else if(type == OrderConstant.MANAGEMENT_PRINTING){
            // 打印中
            orders = printOrderService.findByOrderStatus(OrderConstant.ORDER_STATUS_PRINTING);
        }else if(type == OrderConstant.MANAGEMENT_OVER){
            // 已完成
            orders = printOrderService.findByOrderStatus(OrderConstant.ORDER_STATUS_OVER);
        }else {
            // 打印中和已完成
            orders = printOrderService.findByOrderStatusNot(OrderConstant.ORDER_STATUS_SUBMIT);
        }

        model.addAttribute("orders", orders);

        return "admin/admin_OrderManage";
    }

    /**
     * 获取当前用户订单
     * @return
     */
    @GetMapping("/myOrders.do")
    public String myOrders(Model model, HttpSession session, Integer type){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        // 获取当前用户信息
        SystemUser user = SystemUserVerify.getCurrentUser(session);
        List<PrintOrder> orders;

        if(type == null){

            // 所有
            orders = printOrderService.findByUserId(user.getId());
        }else if(type == OrderConstant.ORDER_TYPE_IS_OVER){

            // 已完成
            orders = printOrderService.findByUserIdAndAndOrderStatus(user.getId(), OrderConstant.ORDER_STATUS_OVER);
        }else if(type == OrderConstant.ORDER_TYPE_NOT_OVER){

            // 未完成
            orders = printOrderService.findByUserIdAndAndOrderStatusNot(user.getId(), OrderConstant.ORDER_STATUS_OVER);
        }else {

            // 所有
            orders = printOrderService.findByUserId(user.getId());
        }

        // 获取当前用户所有订单
        model.addAttribute("orders", orders);

        return "user/user_MyOrder";
    }

    /**
     * 更新订单状态：打印中-->已完成
     * @param session
     * @param id
     * @return
     */
    @GetMapping("/update.do")
    public String updateStatus(HttpSession session, Integer id){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        printOrderService.updateStatus(id);

        return "redirect:/order/printList.do";
    }
}
