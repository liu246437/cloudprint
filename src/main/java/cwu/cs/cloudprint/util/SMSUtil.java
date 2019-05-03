package cwu.cs.cloudprint.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import cwu.cs.cloudprint.constant.UtilConstant;
import cwu.cs.cloudprint.result.CodeMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Service
public class SMSUtil {

    /**
     * 地区代码
     */
    @Value("${cloudPrint.tencentCloud.nationCode}")
    private String nationCode;

    /**
     * appId
     */
    @Value("${cloudPrint.tencentCloud.appId}")
    private Integer appId;

    /**
     * appKey
     */
    @Value("${cloudPrint.tencentCloud.appKey}")
    private String appKey;

    /**
     * 短信签名
     */
    @Value("${cloudPrint.tencentCloud.smsSign}")
    private String smsSign;

    /**
     * 验证码长度
     */
    @Value("${cloudPrint.tencentCloud.digit}")
    private Integer digit;

    /**
     * 验证码有效时间
     */
    @Value("${cloudPrint.tencentCloud.effective}")
    private Integer effective;

    /**
     * 发送手机注册验证码
     * @param session
     * @param phoneNumber
     */
    public CodeMsg sentSimpleMessage(HttpSession session, String phoneNumber){

        CodeMsg codeMsg = new CodeMsg("", 0);

        try {

            // 数组具体的元素个数和模板中变量个数必须一致，例如示例中 templateId:5678 对应一个变量，参数数组中元素个数也必须是一个
            String[] params = this.getParams();
            SmsSingleSender smsSingleSender = new SmsSingleSender(appId, appKey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = smsSingleSender.sendWithParam(nationCode, phoneNumber,
                    UtilConstant.TEMPLATE_ID_VERIFY_CODE, params, smsSign, "", "");

            if(result.result == 0){

                // 设置五分钟有效时长
                session.setAttribute(phoneNumber, params[0]);
                Thread.sleep(effective * 60 * 1000);
                session.removeAttribute(phoneNumber);
            }else {

                // 发送失败
                codeMsg.setCode(UtilConstant.SEND_VERIFY_CODE_ERROR);
                codeMsg.setMsg(result.errMsg);
            }
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
            log.error(e.getMessage());
        }catch (Exception e){
            // 异步异常
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return codeMsg;
    }

    /**
     * 获取短信验证码参数
     * @return
     */
    public String[] getParams(){

        return new String[]{RandomStringUtils.randomNumeric(digit), effective.toString()};
    }
}
