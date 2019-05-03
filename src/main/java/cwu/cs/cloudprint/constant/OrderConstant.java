package cwu.cs.cloudprint.constant;

public class OrderConstant {

    /**
     * 最新订单
     */
    // 最新订单：所有
    public final static Integer LAST_ALL = 1;
    // 最新订单：紧急
    public final static Integer IS_URGENT = 2;
    // 最新订单：不紧急
    public final static Integer NOT_URGENT = 3;

    /**
     * 订单管理
     */
    // 订单管理：所有
    public final static Integer MANAGEMENT_ALL = 1;
    // 订单管理：打印中
    public final static Integer MANAGEMENT_PRINTING = 2;
    // 订单管理：已完成
    public final static Integer MANAGEMENT_OVER = 3;

    /**
     * 订单状态
     */
    // 已提交
    public final static Integer ORDER_STATUS_SUBMIT = 1;
    // 打印中
    public final static Integer ORDER_STATUS_PRINTING = 2;
    // 已完成
    public final static Integer ORDER_STATUS_OVER = 3;

    /**
     * 紧急状态
     */
    // 紧急订单
    public final static Integer URGENT_STATUS_IS = 1;
    // 不紧急不紧急
    public final static Integer URGENT_STATUS_NOT = 0;

    /**
     * 打印类型
     */
    // 1-黑白单面
    public final static Integer PRINT_TYPE_BW_SINGLE = 1;
    // 2-黑白双面
    public final static Integer PRINT_TYPE_BW_DOUBLE = 2;
    // 3-彩色单面
    public final static Integer PRINT_TYPE_CO_SINGLE = 3;
    // 4-彩色双面
    public final static Integer PRINT_TYPE_CO_DOUBLE = 4;
    // 黑白单面
    public final static String PRINT_NAME_BW_SINGLE = "黑白单面";
    // 黑白双面
    public final static String PRINT_NAME_BW_DOUBLE = "黑白双面";
    // 彩色单面
    public final static String PRINT_NAME_CO_SINGLE = "彩色单面";
    // 彩色双面
    public final static String PRINT_NAME_CO_DOUBLE = "彩色双面";
}
