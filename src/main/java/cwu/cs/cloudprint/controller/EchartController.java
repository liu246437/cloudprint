package cwu.cs.cloudprint.controller;

import cwu.cs.cloudprint.result.Result;
import cwu.cs.cloudprint.service.PrintOrderService;
import cwu.cs.cloudprint.verify.SystemUserVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@Validated
@RequestMapping("/chart")
public class EchartController {

    @Autowired
    PrintOrderService printOrderService;

    /**
     * 进入echart图页面-Bar
     * @param session
     * @return
     */
    @GetMapping("/showBar.do")
    public String showBarChart(HttpSession session){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        return "user/user_Index";
    }

    /**
     * 进入echart图页面-Pie
     * @param session
     * @return
     */
    @GetMapping("/showPie.do")
    public String showPieChart(HttpSession session){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        return "admin/admin_Pie";
    }

    /**
     * 所有echart图订单统计结果
     * @return
     */
    @ResponseBody
    @PostMapping("/bar.do")
    public Result getBarData(){

        return Result.ok(printOrderService.getBarData());
    }

    /**
     * 获取打印类型分类数据
     * @return
     */
    @ResponseBody
    @PostMapping("/pie.do")
    public Result getPieData(){

        return Result.ok(printOrderService.getPieData());
    }
}
