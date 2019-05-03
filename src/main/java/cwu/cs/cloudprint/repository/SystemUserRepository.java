package cwu.cs.cloudprint.repository;

import cwu.cs.cloudprint.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer>, JpaSpecificationExecutor<SystemUser> {

    SystemUser findByUserPhone(String userPhone);

    SystemUser findByUserName(String userName);
}
