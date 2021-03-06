package com.hand.hcf.app.workflow.brms.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hand.hcf.app.core.domain.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * 条件
 */
@TableName("sys_rule_condition")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleCondition extends Domain implements Serializable {
    @NotNull
    private UUID ruleConditionOid;
    private Integer status;
    private String code;
    private String name;
    private String remark;
    private Integer typeNumber;
    private String ruleField;
    private Integer symbol;
    @JsonProperty("valueDetail")
    private String valueDetail;
    private String ruleValue;
    private Long batchCode;
    private Integer fieldTypeId;
    private String fieldContent;
}
