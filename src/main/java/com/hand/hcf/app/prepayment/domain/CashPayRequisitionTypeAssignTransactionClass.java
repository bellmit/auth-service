package com.hand.hcf.app.prepayment.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by 韩雪 on 2017/10/25.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("csh_sob_pay_req_t_ass_tra_cla")
public class CashPayRequisitionTypeAssignTransactionClass{
    //主键ID
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "sob_pay_req_type_id")
    private Long sobPayReqTypeId;//预付款单类型ID

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "transaction_class_id")
    private Long transactionClassId;//现金事务分类ID

    //预付款类型代码(现金事务分类代码)
    @TableField(exist = false)
    private String transactionClassCode;

    //预付款类型名称(现金事务分类名称)
    @TableField(exist = false)
    private String transactionClassName;
}