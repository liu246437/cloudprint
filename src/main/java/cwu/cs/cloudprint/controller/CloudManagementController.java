package cwu.cs.cloudprint.controller;

import cwu.cs.cloudprint.verify.SystemUserVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Validated
@Controller
@RequestMapping("/cloud")
public class CloudManagementController {

    @GetMapping("/list.do")
    public String getFiles(HttpSession session){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        return "user/user_cloud";
    }
}
