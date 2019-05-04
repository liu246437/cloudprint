package cwu.cs.cloudprint.service.impl;

import cwu.cs.cloudprint.entity.PrintFile;
import cwu.cs.cloudprint.entity.SystemUser;
import cwu.cs.cloudprint.model.FileUploadReturn;
import cwu.cs.cloudprint.repository.PrintFileRepository;
import cwu.cs.cloudprint.service.PrintFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PrintFileServiceImpl implements PrintFileService {

    @Autowired
    PrintFileRepository printFileRepository;

    /**
     * 保存文件上传记录
     * @param user
     * @param file
     * @param res
     */
    @Override
    public void saveFile(SystemUser user, MultipartFile file, FileUploadReturn res){

        PrintFile printFile = PrintFile.builder()
                .userId(user.getId())
                .fileName(file.getOriginalFilename())
                .rfileName(res.getCloudKey())
                .uploadTime(new Date())
                .filePath(res.getTempPath()).build();

        printFileRepository.save(printFile);
    }

    /**
     * 获取当前用户的打印文件
     * @param userId
     * @return
     */
    @Override
    public List<PrintFile> findByUserId(Integer userId){

        return printFileRepository.findByUserId(userId);
    }

    /**
     * 删除指定文件
     * @param id
     */
    @Override
    public void deleteFile(Integer id){
        Optional<PrintFile> printFileOptional = printFileRepository.findById(id);
        if(printFileOptional.isPresent()){
            printFileRepository.delete(printFileOptional.get());
        }
    }

    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    @Override
    public PrintFile findById(Integer id){
        Optional<PrintFile> printFileOptional = printFileRepository.findById(id);
        if(printFileOptional.isPresent()){
            return printFileOptional.get();
        }
        return null;
    }
}
