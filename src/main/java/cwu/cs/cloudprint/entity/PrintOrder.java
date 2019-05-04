package cwu.cs.cloudprint.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "order_tbl")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class PrintOrder implements Serializable {

    /**
     * 流水号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 订单状态
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 是否紧急
     */
    @Column(name = "urgent_status")
    private Integer urgentStatus;

    /**
     * 打印方式
     *  1-黑白单面
     *  2-黑白双面
     *  3-彩色单面
     *  4-彩色双面
     */
    @Column(name = "print_type")
    private Integer printType;

    /**
     * 打印份数
     */
    @Column(name = "print_copies")
    private Integer printCopies;

    /**
     * 打印备注
     */
    @Column(name = "print_remark")
    private String printRemark;

    /**
     * 统计小时区间
     */
    @Column(name = "time_interval")
    private int timeInterval;

    /**
     * 统计小时区间
     */
    @Column(name = "file_id")
    private Integer fileId;

    /**
     * 统计小时区间
     */
    @Column(name = "pay_amount")
    private BigDecimal payAmount;
}
