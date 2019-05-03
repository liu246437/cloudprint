package cwu.cs.cloudprint.service;

import cwu.cs.cloudprint.entity.SystemUser;

public interface LoginService {

    /**
     * 验证手机号是否注册
     * @param phoneNumber
     * @return
     */
    boolean verifyPhone(String phoneNumber);

    /**
     * 查找用户
     * @param param
     * @return
     */
    SystemUser findSystemUser(String param);

    /**
     * 通过手机号查找
     * @param phone
     * @return
     */
    SystemUser findByPhone(String phone);

    /**
     * 通过用户名查找
     * @param userName
     * @return
     */
    SystemUser findByUserName(String userName);
}
