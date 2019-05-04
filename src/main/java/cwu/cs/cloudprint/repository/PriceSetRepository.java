package cwu.cs.cloudprint.repository;

import cwu.cs.cloudprint.entity.PriceSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PriceSetRepository extends JpaRepository<PriceSet, Integer>, JpaSpecificationExecutor<PriceSet> {

    PriceSet findByUrgentStatusAndPrintType(Integer urgentStatus, Integer printType);
}
