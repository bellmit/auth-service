package com.hand.hcf.app.payment.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hand.hcf.app.core.domain.DomainLogicEnable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: bin.xie
 * @Description: 付款申请单类型实体类
 * @Date: Created in 10:50 2018/1/22
 * @Modified by
 */
@ApiModel(description = "付款申请单类型实体类")
@Data
@TableName("csh_req_types")
public class PaymentRequisitionTypes extends DomainLogicEnable {

    @ApiModelProperty(value = "主键ID")
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id; //主键ID

    @ApiModelProperty(value = "付款申请单代码")
    @TableField(value = "acp_req_type_code")
    @NotNull
    private String acpReqTypeCode; //付款申请单代码

    @ApiModelProperty(value = "付款申请单名称")
    @TableField(value = "description")
    @NotNull
    private String description; //付款申请单名称

    @ApiModelProperty(value = "租户ID")
    @TableField(value = "tenant_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tenantId;//租户ID

    @ApiModelProperty(value = "账套ID")
    @TableField(value = "set_of_books_id")
    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private Long setOfBooksId; //账套ID

    @ApiModelProperty(value = "是否关联报账单")
    @TableField(value = "is_related")
    private Boolean related; //是否关联报账单

    @ApiModelProperty(value = "关联报账单的依据")
    @TableField(value = "according_as_related")
    private String accordingAsRelated;//关联报账单的依据  BASIS_01 -- 申请人=报账单申请人

    @ApiModelProperty(value = "关联类型")
    @TableField(value = "related_type")
    private String relatedType; // BASIS_01 -- 全部类型 BASIS_02 --部分类型

    @ApiModelProperty(value = "关联表单OID")
    @TableField(value = "form_oid")
    private String formOid;//关联表单OID

    @ApiModelProperty(value = "关联表单名称")
    @TableField(value = "form_name")
    private String formName;//关联表单名称

    @ApiModelProperty(value = "付款申请单代码")
    @TableField(value = "form_type")
    private String formType;//关联表单类型

    @ApiModelProperty(value = "付款申请单代码")
    @TableField(value = "apply_employee")
    private String applyEmployee; //适用人员  BASIS_01 -- 全部 BASIS_02 --部门 BASIS_03 --人员组

}
