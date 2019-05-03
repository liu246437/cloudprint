package cwu.cs.cloudprint.controller;

import cwu.cs.cloudprint.constant.RegisterConstant;
import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.model.UserInfo;
import cwu.cs.cloudprint.result.CodeMsg;
import cwu.cs.cloudprint.result.Result;
import cwu.cs.cloudprint.service.LoginService;
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
@RequestMapping("/wel")
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * 用户首先访问
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/login.do")
    public String login(Model model, HttpSession session){

        String code = "";
        if(session.getAttribute("code") != null){
            code = session.getAttribute("code").toString();
        }

        if(code == "100"){
            model.addAttribute(Result.error(CodeMsg.ACCOUNT_ERROR));
            model.addAttribute(UserInfo.builder().username(session.getAttribute("username").toString()).build());
            session.removeAttribute("code");
        }else if(code == "102"){
            model.addAttribute(Result.error(CodeMsg.PWD_ERROR));
            model.addAttribute(UserInfo.builder().username(session.getAttribute("username").toString()).build());
            session.removeAttribute("code");
        }else if(code.equals(String.valueOf(RegisterConstant.REDIRECT))){
            model.addAttribute(Result.error(CodeMsg.BLANK));
            model.addAttribute(UserInfo.builder().username(session.getAttribute("username").toString()).build());
            session.removeAttribute("code");
        }else {
            model.addAttribute(Result.error(CodeMsg.BLANK));
            model.addAttribute(UserInfo.builder().build());
        }

        return "login";
    }

    /**
     * 用户登录
     * @param model
     * @param userInfo
     * @param session
     * @return
     */
    @PostMapping("/doLogin.do")
    public String login(Model model, UserInfo userInfo, HttpSession session){

        // 获取用户信息
        SystemUser user = loginService.findSystemUser(userInfo.getUsername());

        // 如果用户不存在，重新返回登录页面
        if(user == null){
            // 重定向时携带前台输入用户名
            session.setAttribute("username", userInfo.getUsername());
            session.setAttribute("code", "100");
            return "redirect:/wel/login.do";
        }

        // 用过密码不正确
        if(!user.getUserPwd().equals(userInfo.getPassword())){
            // 重定向时携带前台输入用户名
            session.setAttribute("username", userInfo.getUsername());
            session.setAttribute("code", "102");
            return "redirect:/wel/login.do";
        }

        // 设置session登录信息
        session.setAttribute("username", user.getUserName());
        session.setAttribute("currentUser", user);

        if(user.getUserRole() == 2){

            // 普通用户
            return "redirect:/chart/showBar.do";
        }else {

            // 管理员
            return "redirect:/order/submitList.do";
        }
    }

    @GetMapping("/logout.do")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/wel/login.do";
    }
}
