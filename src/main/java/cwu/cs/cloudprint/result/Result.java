package cwu.cs.cloudprint.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result extends HashMap<String, Object> implements Serializable {

    /**
     * 含参构造器
     * @param codeMsg
     */
    public Result(CodeMsg codeMsg){
        put("code", codeMsg.getCode());
        put("msg", codeMsg.getMsg());
    }

    /**
     * 无参构造器
     */
    public Result(){
        this(CodeMsg.SUCCESS);
    }

    /**
     * 默认正常状态返回值
     * @return
     */
    public static Result ok(){
        return new Result();
    }

    /**
     * 有data信息的返回值
     * @param value
     * @return
     */
    public static Result ok(Object value){

        Result r = new Result();
        r.put("data", value);
        return r;
    }

    /**
     * map封装返回结果
     * @param value
     * @return
     */
    public static Result ok(Map<String, Object> value){

        Result r = new Result();
        r.putAll(value);
        return r;
    }

    /**
     * 未知异常
     * @return
     */
    public static Result error(){
        return new Result(CodeMsg.SERVER_ERROR);
    }

    /**
     * 已知异常
     * @param codeMsg
     * @return
     */
    public static Result error(CodeMsg codeMsg){
        return new Result(codeMsg);
    }

    /**
     * 获取信息
     * @return
     */
    public String getMsg(){
        return get("msg").toString();
    }

    /**
     * 重写put方法
     * @param key
     * @param value
     * @return
     */
    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
