package cwu.cs.cloudprint.repository;

import cwu.cs.cloudprint.entity.PrintOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PrintOrderRepository extends JpaRepository<PrintOrder, Integer>, JpaSpecificationExecutor<PrintOrder> {

    Optional<PrintOrder> findById(Integer id);

    List<PrintOrder> findByUserId(Integer userId);

    List<PrintOrder> findByUserIdAndAndOrderStatus(Integer userId, Integer orderStatus);

    List<PrintOrder> findByUserIdAndAndOrderStatusNot(Integer userId, Integer orderStatus);

    List<PrintOrder> findByOrderStatus(Integer orderStatus);

    List<PrintOrder> findByOrderStatusNot(Integer orderStatus);

    List<PrintOrder> findByOrderStatusAndUrgentStatus(Integer orderStatus, Integer urgentStatus);
}