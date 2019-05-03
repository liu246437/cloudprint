package cwu.cs.cloudprint.controller;

import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.service.PrintFileService;
import cwu.cs.cloudprint.util.FileOperateUtil;
import cwu.cs.cloudprint.verify.SystemUserVerify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Slf4j
@Validated
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    PrintFileService printFileService;

    @Autowired
    FileOperateUtil fileOperateUtil;

    /**
     * 获取当前用户的上传文件
     * @return
     */
    @GetMapping("/myFiles.do")
    public String myFiles(Model model, HttpSession session){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        // 获取当前用户
        SystemUser user = SystemUserVerify.getCurrentUser(session);
        // 获取当前用户的所有文件
        model.addAllAttributes(printFileService.findByUserId(user.getId()));

        return "user/user_PrintFile";
    }

    /**
     * 上传文件
     */
    @PostMapping("/upload.do")
    public String uploadFile(Model model, MultipartFile file, HttpSession session){

        // 验证用户是否登录
        if(!SystemUserVerify.verifyLogin(session)){
            return "redirect:/wel/login.do";
        }

        // 文件不能为null
        if (file.isEmpty()) {

            return "redirect:/file/myFiles.do";
        }

        fileOperateUtil.uploadSimpleFile(file);

        return "redirect:/file/myFiles.do";
    }
}
