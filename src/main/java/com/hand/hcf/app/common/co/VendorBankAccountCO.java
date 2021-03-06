package com.hand.hcf.app.common.co;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hand.hcf.app.core.web.dto.DomainObjectDTO;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @Author: 魏胜
 * @Description:
 * @Date: 2018/4/4 16:25
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorBankAccountCO extends DomainObjectDTO {


    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String venInfoId;

    private String companyOid;

    private String venNickOid;

    private String bankAccount;

    private String bankName;

    private String venBankNumberName;

    private Integer venType;

    private String bankOpeningBank;

    private String bankOpeningCity;

    private String bankOperatorNumber;

    private String bankCode;

    private String bankAddress;

    private String country;

    private String swiftCode;

    private Boolean primaryFlag;

    private String importCode;

    private String notes;

    /**
     * 导入使用-状态 1001 启用 1002禁用
     */
    private Integer status;
    /**
     * 导入使用-是否启用
     */
    private String enabledString;
    /**
     * 导入使用-是否主账户
     */
    private String primaryFlagString;

    /**
     * 王凯 artemis 临时需要加急定义
     */
    private String venInfoName;

    /**
     * 能否修改主账户约束标志
     */
    private String constraintFlag;

    /**
     * 返回提示信息-银行名称
     */
    private String mesBankName;

    /**
     * 返回提示信息-供应商银行账户户名
     */
    private String mesVenBankNumberName;

    /**
     * 前端展示更新日期
     */
    private ZonedDateTime webUpdateDate;

    private ZonedDateTime createTime;

    private ZonedDateTime updateTime;

    /**
     * 当前操作人工号和姓名，用来维护供应商银行账号信息时，同步供应商的最后修改人信息
     */

    private String venOperatorNumber;

    private String venOperatorName;
}
