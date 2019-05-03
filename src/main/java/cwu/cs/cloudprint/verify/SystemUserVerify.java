package cwu.cs.cloudprint.verify;

import cwu.cs.cloudprint.entity.SystemUser;

import javax.servlet.http.HttpSession;

public class SystemUserVerify {

    /**
     * 验证用户是否登录
     * @param session
     * @return
     */
    public static boolean verifyLogin(HttpSession session){

        if(getCurrentUser(session)  == null){
            return false;
        }
        return true;
    }

    /**
     * 获取当前系统用户信息
     * @param session
     * @return
     */
    public static SystemUser getCurrentUser(HttpSession session){
        return (SystemUser) session.getAttribute("currentUser");
    }
}
