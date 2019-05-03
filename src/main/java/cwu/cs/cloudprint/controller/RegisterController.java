package cwu.cs.cloudprint.controller;

import cwu.cs.cloudprint.constant.RegisterConstant;
import cwu.cs.cloudprint.model.RegisterInfo;
import cwu.cs.cloudprint.model.UserInfo;
import cwu.cs.cloudprint.result.CodeMsg;
import cwu.cs.cloudprint.result.Result;
import cwu.cs.cloudprint.service.LoginService;
import cwu.cs.cloudprint.service.RegisterService;
import cwu.cs.cloudprint.util.SMSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@Validated
@Controller
@RequestMapping("/wel")
public class RegisterController {

    @Autowired
    SMSUtil smsUtil;

    @Autowired
    LoginService loginService;

    @Autowired
    RegisterService registerService;

    /**
     * 进入用户注册
     * @return
     */
    @GetMapping("/register.do")
    public String Register(Model model, HttpSession session){

        // 获取状态码
        Integer code = (Integer) session.getAttribute("regCode");

        // 获取注册信息
        RegisterInfo registerInfo = (RegisterInfo) session.getAttribute("registerInfo");

        // 信息设置
        if(code == RegisterConstant.PHONE_EXIST){
            model.addAttribute(registerInfo);
            model.addAttribute(Result.error(CodeMsg.PHONE_EXIST));
            session.removeAttribute("regCode");
        }else if(code == RegisterConstant.CODE_ERROR){
            model.addAttribute(registerInfo);
            model.addAttribute(Result.error(CodeMsg.CODE_ERROR));
            session.removeAttribute("regCode");
        }else if(code == RegisterConstant.PWD_NOT_SAME){
            model.addAttribute(registerInfo);
            model.addAttribute(Result.error(CodeMsg.PWD_NOT_SAME));
            session.removeAttribute("regCode");
        }else if(code == RegisterConstant.PWD_LENGTH){
            model.addAttribute(registerInfo);
            model.addAttribute(Result.error(CodeMsg.PWD_LENGTH));
            session.removeAttribute("regCode");
        }else {
            model.addAttribute(RegisterInfo.builder().build());
            model.addAttribute(Result.error(CodeMsg.BLANK));
        }

        return "user/user_Register";
    }

    /**
     * 执行用户注册
     * @param model
     * @param session
     * @param registerInfo
     * @return
     */
    @PostMapping("/register.do")
    public String Register(Model model, HttpSession session, RegisterInfo registerInfo){

        // 获取验证码
        String code = session.getAttribute(registerInfo.phoneNumber).toString();
        // 设置注册信息
        session.setAttribute("registerInfo", registerInfo);

        // 验证手机号是否存在
        if(loginService.verifyPhone(registerInfo.getPhoneNumber())){
            session.setAttribute("regCode", RegisterConstant.PHONE_EXIST);
            return "redirect:/wel/register.do";
        }

        // 验证验证码
        if(!code.equals(registerInfo.getVerifyCode())){
            session.setAttribute("regCode", RegisterConstant.CODE_ERROR);
            return "redirect:/wel/register.do";
        }

        // 验证密码是否一致
        if(!registerInfo.getPassword().equals(registerInfo.signWord)){
            session.setAttribute("regCode", RegisterConstant.PWD_NOT_SAME);
            return "redirect:/wel/register.do";
        }

        // 密码至少为6位
        if(registerInfo.getPassword().length() < 6){
            session.setAttribute("regCode", RegisterConstant.PWD_LENGTH);
            return "redirect:/wel/register.do";
        }

        // 注册用户
        registerService.createUser(registerInfo);
        session.setAttribute("code", RegisterConstant.REDIRECT);
        session.setAttribute("username", registerInfo.getPhoneNumber());
        // 注册码只能使用一次
        session.removeAttribute(registerInfo.getPhoneNumber());

        return "redirect:/wel/login.do";
    }

    /**
     * 获取验证码
     * @param session
     * @param userInfo
     * @return
     */
    @ResponseBody
    @PostMapping("/code.do")
    public Result getVerifyCode(HttpSession session, UserInfo userInfo){

        if(loginService.verifyPhone(userInfo.getPhoneNumber())){
            return Result.error(CodeMsg.PHONE_EXIST);
        }
        return Result.ok(smsUtil.sentSimpleMessage(session, userInfo.getPhoneNumber()));
    }
}
