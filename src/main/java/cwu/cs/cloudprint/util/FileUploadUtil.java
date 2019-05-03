package cwu.cs.cloudprint.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileUploadUtil {

    @Value("${cloudPrint.qiniuCloud.accessKey}")
    private String accessKey;

    @Value("${cloudPrint.qiniuCloud.secretKey}")
    private String secretKey;

    @Value("${cloudPrint.qiniuCloud.bucket}")
    private String bucket;

    @Value("${cloudPrint.qiniuCloud.fileFolder}")
    private String fileFolder;

    /**
     * 上传文件到七牛云
     * @param localFilePath
     */
    public void uploadSimpleFile(String localFilePath){

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, null, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
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
    }
}
