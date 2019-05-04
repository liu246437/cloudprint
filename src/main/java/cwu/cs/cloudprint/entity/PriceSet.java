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

@Entity
@Table(name = "define_pay_tbl")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class PriceSet implements Serializable {

    /**
     * 流水号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 是否紧急
     */
    @Column(name = "urgent_status")
    private Integer urgentStatus;

    /**
     * 打印类型
     */
    @Column(name = "print_type")
    private Integer printType;

    /**
     * 单价
     */
    @Column(name = "pay_amount")
    private BigDecimal payAmount;
}
