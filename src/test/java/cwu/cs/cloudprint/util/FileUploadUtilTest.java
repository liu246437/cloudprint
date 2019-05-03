package cwu.cs.cloudprint.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileUploadUtilTest {

    @Autowired
    FileUploadUtil fileUploadUtil;

    @Test
    public void uploadSimpleFile(){

        fileUploadUtil.uploadSimpleFile("/Users/czliuguoyu163.com/Downloads/python/sub_max_array.py");
    }
}