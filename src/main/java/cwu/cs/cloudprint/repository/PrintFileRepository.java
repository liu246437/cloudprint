package cwu.cs.cloudprint.repository;

import cwu.cs.cloudprint.entity.PrintFile;
import cwu.cs.cloudprint.entity.PrintOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PrintFileRepository extends JpaRepository<PrintOrder, Integer>, JpaSpecificationExecutor<PrintOrder> {

    List<PrintFile> findByUserId(Integer userId);
}