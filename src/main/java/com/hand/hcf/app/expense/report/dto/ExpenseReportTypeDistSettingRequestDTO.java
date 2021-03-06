package com.hand.hcf.app.expense.report.dto;

import com.hand.hcf.app.common.co.CompanyCO;
import com.hand.hcf.app.common.co.DepartmentCO;
import com.hand.hcf.app.common.co.ResponsibilityCenterCO;
import com.hand.hcf.app.expense.report.domain.ExpenseReportTypeDistSetting;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @version: 1.0
 * @author: xue.han@hand-china.com
 * @date: 2019/3/4
 */
@ApiModel(description = "业务规则描述")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseReportTypeDistSettingRequestDTO {
    //分摊设置对象
    @ApiModelProperty(value = "分摊设置对象")
    private ExpenseReportTypeDistSetting expenseReportTypeDistSetting;

    //自定义公司id集合
    @ApiModelProperty(value = "自定义公司id集合")
    private List<Long> companyIdList;

    //自定义部门id集合
    @ApiModelProperty(value = "自定义部门id集合")
    private List<Long> departmentIdList;

    //自定义责任中心id集合(res即responsibility)
    @ApiModelProperty(value = "自定义责任中心id集合")
    private List<Long> resIdList;



    //自定义公司集合
    @ApiModelProperty(value = "自定义公司集合")
    private List<CompanyCO> companyCOList;
    //自定义部门集合
    @ApiModelProperty(value = "自定义部门集合")
    private List<DepartmentCO> departmentCOList;
    //自定义责任中心集合
    @ApiModelProperty(value = "自定义责任中心集合")
    private List<ResponsibilityCenterCO> responsibilityCenterCOList;
}