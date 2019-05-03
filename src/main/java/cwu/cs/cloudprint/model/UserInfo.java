package cwu.cs.cloudprint.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    // 用户名
    private String username;

    // 密码
    private String password;

    // 手机号
    private String phoneNumber;
}
