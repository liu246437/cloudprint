package cwu.cs.cloudprint.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SMSUtilTest {

    @Autowired
    SMSUtil smsUtil;

    @Test
    public void sentSimpleMessage(){

    }
}
