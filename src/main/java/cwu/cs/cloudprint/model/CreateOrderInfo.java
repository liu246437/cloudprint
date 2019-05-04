package cwu.cs.cloudprint.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderInfo {

    // 文件ID
    private Integer fileId;
    // 文件名
    private String fileName;
    // 文件页数
    private Integer filePages;
    // 打印份数
    private Integer printCopies;
    // 是否紧急
    private Integer urgentStatus;
    // 打印类型
    private Integer printType;
    // 备注信息
    private String printRemark;
    // 支付金额
    private BigDecimal payAccount;
    // 信息提示
    private String errmsg;
}