package com.hand.hcf.app.common.co;

import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hand.hcf.app.core.web.dto.DomainObjectDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @Author: bin.xie
 * @Description:
 * @Date: Created in 11:26 2018/1/24
 * @Modified by
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequisitionHeaderCO extends DomainObjectDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;  //主键
    @JsonSerialize(using = ToStringSerializer.class)
    private Long companyId; //机构ID

    private String companyName;//机构名称
    @JsonSerialize(using = ToStringSerializer.class)
    private Long unitId; //部门ID

    private String unitName; //部门名称

    @JsonSerialize(using = ToStringSerializer.class)
    private Long acpReqTypeId; //付款申请单类型ID

    private String acpReqTypeName;//付款申请单类型名称
    @JsonSerialize(using = ToStringSerializer.class)
    private Long employeeId;//员工ID
    private String employeeName;//员工名称

    private String requisitionNumber; //单据编号

    private ZonedDateTime requisitionDate;//申请日期


    private BigDecimal functionAmount;//总金额

    private  String description;//头描述

    private Integer status;//状态

    private String documentOid; //单据oid
    @JsonSerialize(using = ToStringSerializer.class)
    private Long documentType;  //单据类型

    private String formOid; //表单oid


    private String unitOid;//部门oid


    private String applicantOid;//申请人oid

    private Integer versionNumber; //版本号

    private List<PaymentRequisitionLineCO> paymentRequisitionLineDTO; //行DTO

    private String createdName;//创建人名称

    private String submitDate;

    private List<PaymentRequisitionNumberCO> paymentRequisitionNumberDTO;//行币种分组

    private Page page;
    /**
     *  根据单据驳回重新提交,金额或成本中心等是否变更 确认审批时候需要过滤
     */
    private Boolean filterFlag;// true表示跳过,false表示不跳
    /**
     * 历史驳回类型 RejectTypeEnum 驳回类型: 1000-正常, 1001-撤回, 1002-审批驳回 1003-审核驳回 1004-开票驳回
     */
    private String lastRejectType;
    private String rejectType;
    private String rejectReason;

}
