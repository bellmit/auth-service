package com.hand.hcf.app.expense.adjust.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hand.hcf.app.core.domain.Domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @description 费用调整单关联维度(EXP_ADJUST_TYPE_DIMENSION)表实体类
 * @author zhanhua.cheng
 * @date 2019-04-09 17:37:44
 */
@ApiModel(description = "费用调整单关联维度(EXP_ADJUST_TYPE_DIMENSION)表实体类")
@TableName(value = "exp_adjust_type_dimension")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpAdjustTypeDimension extends Domain {
    /**
      费用调整单类型ID 
    */
    @ApiModelProperty(value = "费用调整单类型ID")
    @TableField(value = "exp_adjust_type_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long expAdjustTypeId;
    
    /**
      维度ID 
    */
    @ApiModelProperty(value = "维度ID")
    @TableField(value = "dimension_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dimensionId;

    @TableField(exist = false)
    @ApiModelProperty(value = "维度名称")
    private String dimensionName;
    /**
      排序 
    */
    @ApiModelProperty(value = "排序")
    @TableField(value = "sequence")
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer sequence;
    
    /**
      默认值 
    */
    @ApiModelProperty(value = "默认值")
    @TableField(value = "default_value")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long defaultValue;

    @TableField(exist = false)
    @ApiModelProperty(value = "值名称")
    private String valueName;
    /**
      布局位置 
    */
    @ApiModelProperty(value = "布局位置")
    @TableField(value = "header_flag")
    private Boolean headerFlag;
    
    /**
      是否必输 
    */
    @ApiModelProperty(value = "是否必输")
    @TableField(value = "required_flag")
    private Boolean requiredFlag;
    
}