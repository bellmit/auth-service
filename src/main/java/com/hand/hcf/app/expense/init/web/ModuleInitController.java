package com.hand.hcf.app.expense.init.web;

import com.hand.hcf.app.expense.application.service.ApplicationTypeService;
import com.hand.hcf.app.expense.init.dto.*;
import com.hand.hcf.app.expense.policy.service.ExpensePolicyRelatedCompanyService;
import com.hand.hcf.app.expense.policy.service.ExpensePolicyService;
import com.hand.hcf.app.expense.report.service.ExpenseReportTypeService;
import com.hand.hcf.app.expense.travel.service.TravelApplicationTypeService;
import com.hand.hcf.app.expense.type.service.ExpenseTypeAssignCompanyService;
import com.hand.hcf.app.expense.type.service.ExpenseTypeAssignUserService;
import com.hand.hcf.app.expense.type.service.ExpenseTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p> </p>
 *
 * @Author: zhu.zhao
 * @Date: 2019/04/17
 */
@RestController
@RequestMapping("/api/expense/init")
@Api(tags = "初始化数据控制器")
public class ModuleInitController {

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @Autowired
    private ExpenseReportTypeService expenseReportTypeService;

    @Autowired
    private ApplicationTypeService applicationTypeService;

    @Autowired
    private TravelApplicationTypeService travelApplicationTypeService;

    @Autowired
    private ExpenseTypeAssignCompanyService expenseTypeAssignCompanyService;
    @Autowired
    private ExpenseTypeAssignUserService expenseTypeAssignUserService;

    @Autowired
    private ExpensePolicyService expensePolicyService;

    @Autowired
    private ExpensePolicyRelatedCompanyService expensePolicyRelatedCompanyService;
    /**
     * 申请类型/费用类型导入
     * @param expenseTypeInitDTOS
     * @return
     */
    @PostMapping(value = "/expenseType", produces = "application/json")
    @ApiOperation(value = "申请类型/费用类型导入", notes = "申请类型/费用类型导入 开发:赵柱")
    public ResponseEntity initExpenseType(@ApiParam(value = "申请类型/费用类型初始化DTO") @RequestBody List<ExpenseTypeInitDTO> expenseTypeInitDTOS) {
        return ResponseEntity.ok(expenseTypeService.initExpenseType(expenseTypeInitDTOS));
    }

    /**
     * 申请类型/费用类型分配公司导入
     * @param expenseTypeAssignCompanyInitDTOS
     * @return
     */
    @PostMapping(value = "/expenseTypeAssignCompany", produces = "application/json")
    @ApiOperation(value = "申请类型/费用类型分配公司导入", notes = "申请类型/费用类型分配公司导入 开发:赵柱")
    public ResponseEntity initExpenseTypeAssignCompany(@ApiParam(value = "申请类型/费用类型分配公司初始化DTO") @RequestBody List<ExpenseTypeAssignCompanyInitDTO> expenseTypeAssignCompanyInitDTOS) {
        return ResponseEntity.ok(expenseTypeAssignCompanyService.initExpenseTypeAssignCompany(expenseTypeAssignCompanyInitDTOS));
    }

    /**
     * 费用政策导入
     * @param expensePolicyInitDTOS
     * @return
     */
    @PostMapping(value = "/expensePolicy", produces = "application/json")
    @ApiOperation(value = "费用政策导入导入", notes = "费用政策导入导入 开发:赵柱")
    public ResponseEntity initExpensePolicy(@ApiParam(value = "费用政策初始化DTO") @RequestBody List<ExpensePolicyInitDTO> expensePolicyInitDTOS) {
        return ResponseEntity.ok(expensePolicyService.initExpensePolicy(expensePolicyInitDTOS));
    }

    /**
     * 费用政策导入分配公司
     * @param policyRelatedCompanyInitDTOS
     * @return
     */
    @PostMapping(value = "/expensePolicyRelatedCompany", produces = "application/json")
    @ApiOperation(value = "费用政策导入分配公司", notes = "费用政策导入分配公司 开发:赵柱")
    public ResponseEntity initExpensePolicyRelatedCompany(@ApiParam(value = "申请政策分配公司初始化DTO") @RequestBody List<ExpensePolicyRelatedCompanyInitDTO> policyRelatedCompanyInitDTOS) {
        return ResponseEntity.ok(expensePolicyRelatedCompanyService.initExpensePolicyRelatedCompany(policyRelatedCompanyInitDTOS));
    }

    /**
     * 申请类型/费用类型适用人员导入
     * @param expenseTypeAssignUserInitDTOS
     * @return
     */
    @PostMapping(value = "/expenseTypeAssignUser", produces = "application/json")
    @ApiOperation(value = "申请类型/费用类型适用人员导入", notes = "申请类型/费用类型适用人员导入 开发:赵柱")
    public ResponseEntity initExpenseTypeAssignUser(@ApiParam(value = "申请类型/费用类型适用初始化DTO") @RequestBody List<ExpenseTypeAssignUserInitDTO> expenseTypeAssignUserInitDTOS) {
        return ResponseEntity.ok(expenseTypeAssignUserService.initExpenseTypeAssignUser(expenseTypeAssignUserInitDTOS));
    }

    @PutMapping("/expenseReportTypeExpenseType")
    @ApiOperation(value = "报账单类型关联费用类型", notes = "报账单类型关联费用类型 开发:赵立国")
    public ResponseEntity expExpenseReportTypeExpenseType(@ApiParam(value = "报账单关联费用类型") @RequestBody List<SourceTypeTargetTypeDTO> SourceTypeTargetTypeDTOS) {
        return ResponseEntity.ok(expenseReportTypeService.expExpenseReportTypeExpenseType(SourceTypeTargetTypeDTOS));
    }

    @ApiOperation(value = "费用申请单关联申请类型", notes = "费用申请单关联申请类型 开发:20855")
    @PutMapping("/expenseApplicationTypeApplicationType")
    public ResponseEntity expApplicationTypeApplicationType(@ApiParam(value = "费用申请单关联申请类型") @RequestBody List<SourceTypeTargetTypeDTO> SourceTypeTargetTypeDTOS) {
        return ResponseEntity.ok(applicationTypeService.expApplicationTypeApplicationType(SourceTypeTargetTypeDTOS));
    }

    @ApiOperation(value = "差旅申请单关联申请类型", notes = "差旅申请单关联申请类型 开发:20855")
    @PutMapping("/travelApplicationTypeApplicationType")
    public ResponseEntity expTravelApplicationTypeApplicationType(@ApiParam(value = "差旅申请单关联申请类型") @RequestBody List<SourceTypeTargetTypeDTO> SourceTypeTargetTypeDTOS) {
        return ResponseEntity.ok(travelApplicationTypeService.expTravelApplicationTypeApplicationType(SourceTypeTargetTypeDTOS));
    }


}
