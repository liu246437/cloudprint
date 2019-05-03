package cwu.cs.cloudprint.service.impl;

import cwu.cs.cloudprint.constant.RegisterConstant;
import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.model.RegisterInfo;
import cwu.cs.cloudprint.repository.SystemUserRepository;
import cwu.cs.cloudprint.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    SystemUserRepository systemUserRepository;

    @Override
    public void createUser(RegisterInfo registerInfo){

        SystemUser user = SystemUser.builder()
                .userName(registerInfo.getPhoneNumber())
                .userPwd(registerInfo.getPassword())
                .userPhone(registerInfo.getPhoneNumber())
                .userRole(RegisterConstant.MEMBER_ROLE)
                .remainSum(new BigDecimal("0"))
                .createTime(new Date())
                .build();

        systemUserRepository.save(user);
    }
}
