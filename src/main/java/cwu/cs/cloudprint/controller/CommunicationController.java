package cwu.cs.cloudprint.controller;

import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.verify.SystemUserVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Validated
@Controller
@RequestMapping("/comm")
public class CommunicationController {

    @GetMapping("/list.do")
    public String getMsgList(Model model, HttpSession session){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        SystemUser user = SystemUserVerify.getCurrentUser(session);
        if(user.getUserRole() == 2){
            return "user/user_communication";
        }else {
            return "admin/admin_communication";
        }
    }
}
