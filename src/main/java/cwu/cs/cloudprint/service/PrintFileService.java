package cwu.cs.cloudprint.service;

import cwu.cs.cloudprint.entity.PrintFile;

import java.util.List;

public interface PrintFileService {

    /**
     * 获取当前用户的打印文件
     * @param userId
     * @return
     */
    List<PrintFile> findByUserId(Integer userId);
}
