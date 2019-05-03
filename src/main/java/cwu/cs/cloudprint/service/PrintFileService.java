package cwu.cs.cloudprint.service;

import cwu.cs.cloudprint.entity.PrintFile;
import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.model.FileUploadReturn;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PrintFileService {

    /**
     * 保存文件上传记录
     * @param user
     * @param file
     * @param res
     */
    void saveFile(SystemUser user, MultipartFile file, FileUploadReturn res);

    /**
     * 获取当前用户的打印文件
     * @param userId
     * @return
     */
    List<PrintFile> findByUserId(Integer userId);
}
