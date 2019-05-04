package cwu.cs.cloudprint.repository;

import cwu.cs.cloudprint.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer>, JpaSpecificationExecutor<SystemUser> {

    Optional<SystemUser> findById(Integer id);

    SystemUser findByUserPhone(String userPhone);

    SystemUser findByUserName(String userName);
}
