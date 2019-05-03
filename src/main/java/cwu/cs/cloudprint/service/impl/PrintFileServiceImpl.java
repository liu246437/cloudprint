package cwu.cs.cloudprint.service.impl;

import cwu.cs.cloudprint.entity.PrintFile;
import cwu.cs.cloudprint.repository.PrintFileRepository;
import cwu.cs.cloudprint.service.PrintFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class PrintFileServiceImpl implements PrintFileService {

    @Autowired
    PrintFileRepository printFileRepository;

    /**
     * 获取当前用户的打印文件
     * @param userId
     * @return
     */
    @Override
    public List<PrintFile> findByUserId(Integer userId){

        return printFileRepository.findByUserId(userId);
    }
}
