package cwu.cs.cloudprint.result;

import lombok.Data;

@Data
public class CodeMsg {

    // 状态码
    private int code;
    // 提示信息
    private String msg;

    private CodeMsg(){

    }

    private CodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg(String msg, Integer code){
        this.msg = msg;
        this.code = code;
    }

    public static CodeMsg BLANK = new CodeMsg(0, "");

    public static CodeMsg SUCCESS = new CodeMsg(10, "success");

    public static CodeMsg SERVER_ERROR = new CodeMsg(11, "success");

    public static CodeMsg USER_NULL = new CodeMsg(100, "用户信息不存在。");

    public static CodeMsg ACCOUNT_ERROR = new CodeMsg(101, "账号或手机号错误。");

    public static CodeMsg PWD_ERROR = new CodeMsg(102, "密码错误。");

    public static CodeMsg PHONE_EXIST = new CodeMsg(103, "该手机号已注册。");

    public static CodeMsg CODE_ERROR = new CodeMsg(104, "验证码输入错误。");

    public static CodeMsg PWD_NOT_SAME = new CodeMsg(105, "两次输入的密码一致。");

    public static CodeMsg PWD_LENGTH = new CodeMsg(106, "密码至少为6位。");
}
