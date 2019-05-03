package cwu.cs.cloudprint.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import cwu.cs.cloudprint.model.FileUploadReturn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileOperateUtil {

    @Value("${cloudPrint.qiniuCloud.accessKey}")
    private String accessKey;

    @Value("${cloudPrint.qiniuCloud.secretKey}")
    private String secretKey;

    @Value("${cloudPrint.qiniuCloud.bucket}")
    private String bucket;

    @Value("${cloudPrint.qiniuCloud.fileFolder}")
    private String fileFolder;

    @Value("${cloudPrint.filePath}")
    private String filePath;

    /**
     * 上传文件到七牛云
     * @param file
     * @return
     */
    public FileUploadReturn uploadSimpleFile(MultipartFile file){

        // 将文件保存在临时文件夹
        this.saveTemp(file);

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        FileUploadReturn res = new FileUploadReturn();

        try {
            Response response = uploadManager.put(filePath.concat(file.getOriginalFilename()), null, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            res = FileUploadReturn.builder().cloudKey(putRet.key)
                    .tempPath(filePath.concat(file.getOriginalFilename())).build();
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            log.error(r.toString());
            try {
                System.err.println(r.bodyString());
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                log.error(ex2.error());
            }
        }

        return res;
    }

    /**
     * 上传文件到本地临时文件夹
     * @param file
     */
    public void saveTemp(MultipartFile file){

        // 保证路径存在
        this.createFolder();

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成临时文件路径
     */
    public void createFolder(){
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdir();
        }
    }
}
