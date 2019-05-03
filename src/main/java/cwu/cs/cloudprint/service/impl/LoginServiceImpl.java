package cwu.cs.cloudprint.service.impl;

import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.repository.SystemUserRepository;
import cwu.cs.cloudprint.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    SystemUserRepository systemUserRepository;

    /**
     * 验证手机号是否注册
     * @param phoneNumber
     * @return
     */
    @Override
    public boolean verifyPhone(String phoneNumber){

        SystemUser user = this.findByPhone(phoneNumber);
        if(user == null){
            return false;
        }
        return true;
    }

    /**
     * 查找用户
     * @param param
     * @return
     */
    @Override
    public SystemUser findSystemUser(String param){

        // 首先通过手机号查找，若手机号查找失败，则通过用户名查找
        SystemUser systemUser = this.findByPhone(param);
        if(systemUser == null){
            return this.findByUserName(param);
        }
        return systemUser;
    }

    /**
     * 通过手机号查找
     * @param userPhone
     * @return
     */
    @Override
    public SystemUser findByPhone(String userPhone){

        return systemUserRepository.findByUserPhone(userPhone);
    }

    /**
     * 通过用户名查找
     * @param userName
     * @return
     */
    @Override
    public SystemUser findByUserName(String userName){

        return systemUserRepository.findByUserName(userName);
    }
}
