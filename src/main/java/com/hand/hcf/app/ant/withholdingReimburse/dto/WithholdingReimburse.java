/**
 * <p>
 *     费用小类基类
 * </p>
 *
 * @Author: jsq
 * @Date: 2019/06/19
 */

package com.hand.hcf.app.ant.withholdingReimburse.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hand.hcf.app.common.co.AttachmentCO;
import com.hand.hcf.app.core.domain.Domain;
import com.hand.hcf.app.expense.common.utils.RespCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


@ApiModel(description = "费用小类")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ant_exp_withholding_expense_reimburse")
@AllArgsConstructor
@NoArgsConstructor
public class WithholdingReimburse extends Domain {
    @TableId
    @JsonSerialize(
            using = ToStringSerializer.class   //用字符串类型序列化id
    )
    private Long id;

    /**
     * 单据编号
     */
    @ApiModelProperty(value = "单据编号")
    @NotNull(message = RespCode.SYS_FIELD_IS_NULL)
    @TableField(value = "document_number")
    private String documentNumber;

    /**
     * 单据类型id
     */
    @ApiModelProperty(value = "单据类型id")
    @NotNull(message = RespCode.SYS_FIELD_IS_NULL)
    @TableField(value = "document_type_id")
    private String documentTypeId;

    /**
     * 单据类型名称
     */
    @ApiModelProperty(value = "单据类型名称")
    @TableField(exist = false)
    private String documentTypeName;

    /**
     * 账套
     */
    @ApiModelProperty(value = "帐套id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "set_of_book_id")
    private Long setOfBooksId;

    /**
     * 账套名称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "账套名称")
    private String setOfBooksName;

    /**
     * 创建人名称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "创建人名称")
    private String createdByName;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "tenant_id")
    private Long tenantId;

    /**
     * 费用小类id
     */
    @ApiModelProperty(value = "费用小类id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "category_type_id")
    private Long categoryTypeId;

    /**
     * 预提金额
     */
    @ApiModelProperty(value = "预提金额",dataType = "BigDecimal")
    @TableField(value = "amount" )
    private BigDecimal amount;


    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要")
    @TableField(value = "comment")
    private String comment;


    /**
     * 责任人id
     */
    @ApiModelProperty(value = "责任人id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "duty_person_id")
    private Long dutyPersonId;

    /**
     * 责任人名称
     */
    @ApiModelProperty(value = "责任人名称")
    @TableField(exist = false)
    private String dutyPersonName;


    /*
     * 状态
     * */
    @ApiModelProperty(value = "预提状态")
    @NotNull(message = RespCode.SYS_FIELD_IS_NULL)
    @TableField(value = "status")
    private String status;


    /*
     * 次月是否冲销
     * */
    @ApiModelProperty(value = "次月是否冲销")
    @TableField(value = "auto_sterilisation")
    private boolean autoSterilisation;

    /*
     * 附件oid
     * */
    @ApiModelProperty(value = "附件oid")
    @TableField(value = "attachmentOid")
    private String attachmentOid;

    /*
     * 附件
     * */
    @ApiModelProperty(value = "附件")
    @TableField(exist = false)
    private List<AttachmentCO> attachments;


}
