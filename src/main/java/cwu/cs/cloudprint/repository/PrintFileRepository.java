package cwu.cs.cloudprint.repository;

import cwu.cs.cloudprint.entity.PrintFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PrintFileRepository extends JpaRepository<PrintFile, Integer>, JpaSpecificationExecutor<PrintFile> {

    List<PrintFile> findByUserId(Integer userId);

    Optional<PrintFile> findById(Integer id);
}