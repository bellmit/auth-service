package com.hand.hcf.app.expense.report.service;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hand.hcf.app.common.co.*;
import com.hand.hcf.app.common.enums.FormTypeEnum;
import com.hand.hcf.app.core.exception.BizException;
import com.hand.hcf.app.core.service.BaseI18nService;
import com.hand.hcf.app.core.service.BaseService;
import com.hand.hcf.app.expense.common.domain.enums.ExpenseDocumentTypeEnum;
import com.hand.hcf.app.expense.common.externalApi.OrganizationService;
import com.hand.hcf.app.expense.common.externalApi.PaymentService;
import com.hand.hcf.app.expense.common.utils.RespCode;
import com.hand.hcf.app.expense.common.utils.StringUtil;
import com.hand.hcf.app.expense.init.dto.SourceTypeTargetTypeDTO;
import com.hand.hcf.app.expense.report.domain.*;
import com.hand.hcf.app.expense.report.dto.DepartmentOrUserGroupDTO;
import com.hand.hcf.app.expense.report.dto.ExpenseReportTypeDTO;
import com.hand.hcf.app.expense.report.dto.ExpenseReportTypeRequestDTO;
import com.hand.hcf.app.expense.report.persistence.ExpenseReportTypeMapper;
import com.hand.hcf.app.expense.type.bo.ExpenseBO;
import com.hand.hcf.app.expense.type.domain.ExpenseDimension;
import com.hand.hcf.app.expense.type.domain.ExpenseType;
import com.hand.hcf.app.expense.type.service.ExpenseDimensionService;
import com.hand.hcf.app.expense.type.service.ExpenseTypeService;
import com.hand.hcf.app.expense.type.web.dto.ExpenseTypeWebDTO;
import com.hand.hcf.app.mdata.base.util.OrgInformationUtil;
import com.hand.hcf.app.mdata.implement.web.AuthorizeControllerImpl;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.*;

/**
 * @description:
 * @version: 1.0
 * @author: xue.han@hand-china.com
 * @date: 2019/2/22
 */
@Service
@Transactional
public class ExpenseReportTypeService extends BaseService<ExpenseReportTypeMapper,ExpenseReportType>{

    @Autowired
    private ExpenseReportTypeMapper expenseReportTypeMapper;

    @Autowired
    private ExpenseReportTypeExpenseTypeService expenseReportTypeExpenseTypeService;

    @Autowired
    private ExpenseReportTypeTransactionClassService expenseReportTypeTransactionClassService;

    @Autowired
    private ExpenseReportTypeDepartmentService expenseReportTypeDepartmentService;

    @Autowired
    private ExpenseReportTypeUserGroupService expenseReportTypeUserGroupService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private BaseI18nService baseI18nService;

    @Autowired
    private ExpenseReportTypeDimensionService dimensionService;

    @Autowired
    private ExpenseDimensionService expenseDimensionService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @Autowired
    private ExpenseReportTypeDistSettingService expenseReportTypeDistSettingService;

    @Autowired
    private ExpenseReportTypeCompanyService expenseReportTypeCompanyService;

    @Autowired
    private AuthorizeControllerImpl authorizeClient;

    /**
     * 新增 报账单类型
     *
     * @param expenseReportTypeRequestDTO
     * @return
     */
    @Transactional
    public ExpenseReportType createExpenseReportType(ExpenseReportTypeRequestDTO expenseReportTypeRequestDTO){
        //插入报账单类型表
        ExpenseReportType expenseReportType = expenseReportTypeRequestDTO.getExpenseReportType();
//        CashPayRequisitionType cashPayRequisitionType = new CashPayRequisitionType();
//        BeanUtils.copyProperties(dtoCashPayRequisitionType,cashPayRequisitionType);
        if (expenseReportType.getId() != null){
            throw new BizException(RespCode.EXPENSE_REPORT_TYPE_ALREADY_EXISTS);
        }
        if (expenseReportTypeMapper.selectList(
                new EntityWrapper<ExpenseReportType>()
                        .eq("set_of_books_id",expenseReportType.getSetOfBooksId())
                        .eq("report_type_code",expenseReportType.getReportTypeCode())
        ).size() > 0){
            throw new BizException(RespCode.EXPENSE_REPORT_TYPE_CODE_NOT_ALLOWED_TO_REPEAT);
        }

        this.insert(expenseReportType);

        //插入预付款单类型关联费用类型表
        if ( false == expenseReportType.getAllExpenseFlag() ){
            List<Long> expenseTypeIdList = expenseReportTypeRequestDTO.getExpenseTypeIdList();
            if (expenseTypeIdList != null) {
                List<ExpenseReportTypeExpenseType> expenseTypeList = new ArrayList<>();
                expenseTypeIdList.stream().forEach(expenseTypeId -> {
                    ExpenseReportTypeExpenseType expenseType = ExpenseReportTypeExpenseType
                            .builder().reportTypeId(expenseReportType.getId()).expenseTypeId(expenseTypeId).build();
                    expenseTypeList.add(expenseType);
                });
                expenseReportTypeExpenseTypeService.createExpenseReportTypeExpenseTypeBatch(expenseTypeList);
            }
        }

        //插入报账单类型关联付款用途表
        if ( false == expenseReportType.getAllCashTransactionClass() ){
            List<Long> cashTransactionClassIdList =expenseReportTypeRequestDTO.getCashTransactionClassIdList();
            if (cashTransactionClassIdList != null){
                List<ExpenseReportTypeTransactionClass> transactionClassList = new ArrayList<>();
                cashTransactionClassIdList.stream().forEach(cashTransactionClassId -> {
                    ExpenseReportTypeTransactionClass transactionClass = ExpenseReportTypeTransactionClass
                            .builder().reportTypeId(expenseReportType.getId()).transactionClassId(cashTransactionClassId).build();
                    transactionClassList.add(transactionClass);
                });
                expenseReportTypeTransactionClassService.createExpenseReportTypeTransactionClassBatch(transactionClassList);
            }
        }

        //插入部门或人员组
        if ("1002".equals(expenseReportType.getApplyEmployee())){
            List<Long> departmentIdList = expenseReportTypeRequestDTO.getDepartmentOrUserGroupIdList();
            if (departmentIdList != null){
                List<ExpenseReportTypeDepartment> departmentList = new ArrayList<>();
                departmentIdList.stream().forEach(departmentId ->{
                    ExpenseReportTypeDepartment department = ExpenseReportTypeDepartment
                            .builder().reportTypeId(expenseReportType.getId()).departmentId(departmentId).build();
                    departmentList.add(department);
                });
                expenseReportTypeDepartmentService.createExpenseReportTypeDepartmentBatch(departmentList);
            }
        }else if ("1003".equals(expenseReportType.getApplyEmployee())){
            List<Long> userGroupIdList = expenseReportTypeRequestDTO.getDepartmentOrUserGroupIdList();
            if (userGroupIdList != null){
                List<ExpenseReportTypeUserGroup> userGroupList = new ArrayList<>();
                userGroupIdList.stream().forEach(userGroupId -> {
                    ExpenseReportTypeUserGroup userGroup = ExpenseReportTypeUserGroup
                            .builder().reportTypeId(expenseReportType.getId()).userGroupId(userGroupId).build();
                    userGroupList.add(userGroup);
                });
                expenseReportTypeUserGroupService.createExpenseReportTypeUserGroupBatch(userGroupList);
            }
        }

        //在分摊设置表中插入默认值
        ExpenseReportTypeDistSetting expenseReportTypeDistSetting = ExpenseReportTypeDistSetting
                .builder()
                .reportTypeId(expenseReportType.getId())
                .companyDistFlag(false)
                .companyVisible("EDITABLE")
                .departmentDistFlag(false)
                .departmentVisible("EDITABLE")
                .resCenterDistFlag(false)
                .resVisible("EDITABLE")
                .build();
        expenseReportTypeDistSettingService.insert(expenseReportTypeDistSetting);

        //返回给前台新增的报账单类型
        return expenseReportType;
    }

    /**
     * 修改 报账单类型
     *
     * @param expenseReportTypeRequestDTO
     * @return
     */
    @Transactional
    public ExpenseReportType updateExpenseReportType(ExpenseReportTypeRequestDTO expenseReportTypeRequestDTO){
        //修改报账单类型表
        ExpenseReportType expenseReportType = expenseReportTypeRequestDTO.getExpenseReportType();

        ExpenseReportType reportType = expenseReportTypeMapper.selectById(expenseReportType.getId());
        if (reportType == null){
            throw new BizException(RespCode.EXPENSE_REPORT_TYPE_NOT_EXIST);
        }
        expenseReportType.setReportTypeCode(reportType.getReportTypeCode());
        this.updateAllColumnById(expenseReportType);

        //修改报账单类型关联费用类型表
        expenseReportTypeExpenseTypeService.deleteExpenseReportTypeExpenseTypeByReportTypeIdBatch(expenseReportType.getId());
        if (!expenseReportType.getAllExpenseFlag()){
            List<ExpenseReportTypeExpenseType> expenseTypeList = new ArrayList<>();
            List<Long> expenseTypeIdList = expenseReportTypeRequestDTO.getExpenseTypeIdList();
            if (CollectionUtils.isEmpty(expenseTypeIdList)){
                throw new BizException(RespCode.EXPENSE_REPORT_TYPE_EXPENSE_TYPE_SHOULD_NOT_EMPTY);
            }
            expenseTypeIdList.stream().forEach(expenseTypeId -> {
                ExpenseReportTypeExpenseType expenseType = ExpenseReportTypeExpenseType
                        .builder().reportTypeId(expenseReportType.getId()).expenseTypeId(expenseTypeId).build();
                expenseTypeList.add(expenseType);
            });
            expenseReportTypeExpenseTypeService.insertBatch(expenseTypeList);
        }

        //修改报账单类型关联付款用途表
        expenseReportTypeTransactionClassService.deleteExpenseReportTypeTransactionClassByReportTypeIdBatch(expenseReportType.getId());
        if (!expenseReportType.getAllCashTransactionClass()){
            List<ExpenseReportTypeTransactionClass> transactionClassList = new ArrayList<>();
            List<Long> transactionClassIdList = expenseReportTypeRequestDTO.getCashTransactionClassIdList();
            if (CollectionUtils.isEmpty(transactionClassIdList)){
                throw new BizException(RespCode.EXPENSE_REPORT_TYPE_TRANSACTION_CLASS_SHOULD_NOT_EMPTY);
            }
            transactionClassIdList.stream().forEach(transactionClassId -> {
                ExpenseReportTypeTransactionClass transactionClass = ExpenseReportTypeTransactionClass
                        .builder().reportTypeId(expenseReportType.getId()).transactionClassId(transactionClassId).build();
                transactionClassList.add(transactionClass);
            });
            expenseReportTypeTransactionClassService.insertBatch(transactionClassList);
        }

        //修改报账单类型关联部门或人员组
        //删除原有数据
        if ("1002".equals(reportType.getApplyEmployee())){
            List<Long> departmentIdList = expenseReportTypeDepartmentService.selectList(
                    new EntityWrapper<ExpenseReportTypeDepartment>()
                            .eq("report_type_id",expenseReportType.getId())
            ).stream().map(ExpenseReportTypeDepartment::getId).collect(toList());
            expenseReportTypeDepartmentService.deleteBatchIds(departmentIdList);
        }else if ("1003".equals(reportType.getApplyEmployee())){
            List<Long> userGroupIdList = expenseReportTypeUserGroupService.selectList(
                    new EntityWrapper<ExpenseReportTypeUserGroup>()
                            .eq("report_type_id",expenseReportType.getId())
            ).stream().map(ExpenseReportTypeUserGroup::getId).collect(toList());
            expenseReportTypeUserGroupService.deleteBatchIds(userGroupIdList);
        }
        //插入更新的数据
        if ("1002".equals(expenseReportType.getApplyEmployee())){
            List<Long> departmentIdList = expenseReportTypeRequestDTO.getDepartmentOrUserGroupIdList();
            if (departmentIdList != null){
                List<ExpenseReportTypeDepartment> departmentList = new ArrayList<>();
                departmentIdList.stream().forEach(departmentId ->{
                    ExpenseReportTypeDepartment department = ExpenseReportTypeDepartment
                            .builder().reportTypeId(expenseReportType.getId()).departmentId(departmentId).build();
                    departmentList.add(department);
                });
                expenseReportTypeDepartmentService.createExpenseReportTypeDepartmentBatch(departmentList);
            }
        }else if ("1003".equals(expenseReportType.getApplyEmployee())){
            List<Long> userGroupIdList = expenseReportTypeRequestDTO.getDepartmentOrUserGroupIdList();
            if (userGroupIdList != null){
                List<ExpenseReportTypeUserGroup> userGroupList = new ArrayList<>();
                userGroupIdList.stream().forEach(userGroupId -> {
                    ExpenseReportTypeUserGroup userGroup = ExpenseReportTypeUserGroup
                            .builder().reportTypeId(expenseReportType.getId()).userGroupId(userGroupId).build();
                    userGroupList.add(userGroup);
                });
                expenseReportTypeUserGroupService.createExpenseReportTypeUserGroupBatch(userGroupList);
            }
        }


        //返回修改以后的报账单类型
        return expenseReportType;
    }

    /**
     * 根据ID查询 报账单类型
     *
     * @param id
     * @return
     */
    public ExpenseReportTypeRequestDTO getExpenseReportType(Long id){
        ExpenseReportTypeRequestDTO expenseReportTypeRequestDTO = new ExpenseReportTypeRequestDTO();

        //返回报账单类型数据
        ExpenseReportType expenseReportType = baseI18nService.selectOneTranslatedTableInfoWithI18nByEntity(this.selectById(id),ExpenseReportType.class);
        //返回账套code、账套name
        SetOfBooksInfoCO setOfBooksInfoCOById = organizationService.getSetOfBooksInfoCOById(expenseReportType.getSetOfBooksId(), false);
        if (setOfBooksInfoCOById != null) {
            expenseReportType.setSetOfBooksCode(setOfBooksInfoCOById.getSetOfBooksCode());
            expenseReportType.setSetOfBooksName(setOfBooksInfoCOById.getSetOfBooksName());
        }
        //返回付款方式类型name
        /*SysCodeValueCO sysCodeValueByCodeAndValue = organizationService.getSysCodeValueByCodeAndValue(SystemCustomEnumerationType.CSH_PAYMENT_TYPE, expenseReportType.getPaymentMethod());
        if (sysCodeValueByCodeAndValue != null) {
            expenseReportType.setPaymentMethodName(sysCodeValueByCodeAndValue.getName());
        }*/
        //返回付款方式name
        SysCodeValueCO sysCodeValue = organizationService.getSysCodeValueByCodeAndValue("ZJ_PAYMENT_TYPE", expenseReportType.getPaymentType());
        if (sysCodeValue != null) {
            expenseReportType.setPaymentTypeName(sysCodeValue.getName());
        }
        //返回关联表单名称formName
        ApprovalFormCO approvalFormById = organizationService.getApprovalFormById(expenseReportType.getFormId());
        if (approvalFormById != null){
            expenseReportType.setFormName(approvalFormById.getFormName());
        }

        expenseReportTypeRequestDTO.setExpenseReportType(expenseReportType);

        //返回报账单类型关联费用类型表数据
        if ( false == expenseReportType.getAllExpenseFlag() ) {
            List<Long> expenseTypeIdList = expenseReportTypeExpenseTypeService.selectList(
                    new EntityWrapper<ExpenseReportTypeExpenseType>()
                            .eq("report_type_id",expenseReportType.getId())
            ).stream().map(ExpenseReportTypeExpenseType::getExpenseTypeId).collect(toList());
            //返回Long 类型的费用类型id集合
            expenseReportTypeRequestDTO.setExpenseTypeIdList(expenseTypeIdList);
        }

        //返回报账单类型关联付款用途表
        if ( false == expenseReportType.getAllCashTransactionClass() ){
            List<Long> transactionClassIdList = expenseReportTypeTransactionClassService.selectList(
                    new EntityWrapper<ExpenseReportTypeTransactionClass>()
                            .eq("report_type_id",id)
            ).stream().map(ExpenseReportTypeTransactionClass::getTransactionClassId).collect(toList());
//            List<CashTransactionClassCO> classDTOS = paymentModuleInterface.listCashTransactionClassByIdList(transactionClassIdList);
//            List<Long> ids = classDTOS.stream().map(CashTransactionClassCO::getId).collect(Collectors.toList());
            //返回Long 类型的现金事务分类id集合
            expenseReportTypeRequestDTO.setCashTransactionClassIdList(transactionClassIdList);
        }
        //返回报账单类型关联的部门或人员组
        if ( !"1001".equals(expenseReportType.getApplyEmployee()) ){
            List<Long> departmentOrUserGroupIdList = new ArrayList<>();
            if ( "1002".equals(expenseReportType.getApplyEmployee()) ){
                departmentOrUserGroupIdList = expenseReportTypeDepartmentService.selectList(
                        new EntityWrapper<ExpenseReportTypeDepartment>()
                                .eq("report_type_id",id)
                ).stream().map(ExpenseReportTypeDepartment::getDepartmentId).collect(toList());
                if (departmentOrUserGroupIdList != null){
                    List<DepartmentOrUserGroupDTO> departmentList = new ArrayList<>();
                    Map<Long, DepartmentCO> departmentMap = organizationService.getDepartmentMapByDepartmentIds(departmentOrUserGroupIdList);
                    departmentOrUserGroupIdList.stream().forEach(e ->{
                        if (departmentMap.containsKey(e)){
                            DepartmentCO departmentCO = departmentMap.get(e);
                            DepartmentOrUserGroupDTO department = DepartmentOrUserGroupDTO.builder().id(departmentCO.getId()).name(departmentCO.getName()).build();
                            departmentList.add(department);
                        }
                    });
                    expenseReportTypeRequestDTO.setDepartmentOrUserGroupList(departmentList);
                }
            }else if ( "1003".equals(expenseReportType.getApplyEmployee()) ){
                departmentOrUserGroupIdList = expenseReportTypeUserGroupService.selectList(
                        new EntityWrapper<ExpenseReportTypeUserGroup>()
                                .eq("report_type_id",id)
                ).stream().map(ExpenseReportTypeUserGroup::getUserGroupId).collect(toList());
                if (departmentOrUserGroupIdList != null){
                    Map<Long, UserGroupCO> userGroupCOMap = organizationService.getUserGroupMapByGroupIds(departmentOrUserGroupIdList);
                    List<DepartmentOrUserGroupDTO> userGroupList = new ArrayList<>();
                    departmentOrUserGroupIdList.stream().forEach(e -> {
                        if (userGroupCOMap.containsKey(e)) {
                            UserGroupCO userGroupCO = userGroupCOMap.get(e);
                            DepartmentOrUserGroupDTO userGroup = DepartmentOrUserGroupDTO.builder().id(userGroupCO.getId()).name(userGroupCO.getName()).build();
                            userGroupList.add(userGroup);
                        }
                    });
                    expenseReportTypeRequestDTO.setDepartmentOrUserGroupList(userGroupList);
                }
            }
            expenseReportTypeRequestDTO.setDepartmentOrUserGroupIdList(departmentOrUserGroupIdList);
        }


        //返回expenseReportTypeRequestDTO
        return expenseReportTypeRequestDTO;
    }

    /**
     * 自定义条件查询 报账单类型(分页)
     *
     * @param setOfBooksId
     * @param reportTypeCode
     * @param reportTypeName
     * @param enabled
     * @param page
     * @return
     */
    public List<ExpenseReportType> getExpenseReportTypeByCond(Long setOfBooksId, String reportTypeCode, String reportTypeName,Boolean enabled, Page page){
        List<ExpenseReportType> list = new ArrayList<>();

        if (setOfBooksId == null){
            return list;
        }

        list = expenseReportTypeMapper.selectPage(page,
                new EntityWrapper<ExpenseReportType>()
                        .where("deleted = false")
                        .eq("set_of_books_id",setOfBooksId)
                        .like(reportTypeCode != null, "report_type_code",reportTypeCode, SqlLike.DEFAULT)
                        .like(reportTypeName != null, "report_type_name",reportTypeName, SqlLike.DEFAULT)
                        .eq(enabled != null, "enabled",enabled)
                        .orderBy("enabled",false)
                        .orderBy("report_type_code")
        );
        list = baseI18nService.selectListTranslatedTableInfoWithI18nByEntity(list, ExpenseReportType.class);
        SetOfBooksInfoCO setOfBooks = organizationService.getSetOfBooksInfoCOById(setOfBooksId, false);
        Map<String, String> sysCodeValueMap = organizationService.mapSysCodeValueByCode(
                "ZJ_PAYMENT_TYPE");
        Map<Long, String> formNameMap = new HashMap<>(16);
        for (ExpenseReportType expenseReportType : list){
            //返回账套code、账套name
            if (setOfBooks != null) {
                expenseReportType.setSetOfBooksCode(setOfBooks.getSetOfBooksCode());
                expenseReportType.setSetOfBooksName(setOfBooks.getSetOfBooksName());
            }

            //返回付款方式类型name
//            expenseReportType.setPaymentMethodName(sysCodeValueMap.get(expenseReportType.getPaymentMethod()));
            //返回付款方式name
            expenseReportType.setPaymentTypeName(sysCodeValueMap.get(expenseReportType.getPaymentType()));

            //返回关联表单名称formName
            if (!formNameMap.containsKey(expenseReportType.getFormId())) {
                ApprovalFormCO formCO = organizationService.getApprovalFormById(expenseReportType.getFormId());
                if (formCO != null){
                    formNameMap.put(expenseReportType.getFormId(), formCO.getFormName());
                }
            }
            expenseReportType.setFormName(formNameMap.get(expenseReportType.getFormId()));
        }
        return list;
    }

    /**
     * 获取某个报账单类型下，当前账套下、启用的、PAYMENT类型的 已分配的、未分配的、全部的 付款用途(现金事物分类)
     * @param setOfBooksId 账套ID
     * @param range 查询范围(全部：all；已选：selected；未选：notChoose)
     * @param reportTypeId 报账单类型ID
     * @param code 付款用途代码
     * @param name 付款用途名称
     * @param page 分页信息
     * @return
     * @throws URISyntaxException
     */
    public Page<CashTransactionClassCO> getTransactionClassForExpenseReportType(Long setOfBooksId,String range,Long reportTypeId,String code,String name,Page page)throws URISyntaxException {
        List<CashTransactionClassCO> list = new ArrayList<>();

        CashTransactionClassForOtherCO forOtherCO = new CashTransactionClassForOtherCO();
        forOtherCO.setSetOfBookId(setOfBooksId);
        forOtherCO.setRange(range);
        forOtherCO.setClassCode(code);
        forOtherCO.setDescription(name);

        //reportTypeId不为null，说明是更新
        if (null != reportTypeId){
            //如果 之前该 报账单关联的 付款用途(现金事务分类)是部分的
            if(!expenseReportTypeMapper.selectById(reportTypeId).getAllCashTransactionClass() ){
                List<Long> transactionClassIdList = expenseReportTypeTransactionClassService.selectList(
                        new EntityWrapper<ExpenseReportTypeTransactionClass>()
                                .eq("report_type_id",reportTypeId)
                ).stream().map(ExpenseReportTypeTransactionClass::getTransactionClassId).collect(toList());
                forOtherCO.setTransactionClassIdList(transactionClassIdList);
            }
        }

        //jiu.zhao 支付
        //全部：all、已选：selected、未选：notChoose
        /*if (forOtherCO.getRange().equals("selected")){
            list = paymentService.listCashTransactionClassByRange(forOtherCO,page);
            if (list.size() > 0){
                list.stream().forEach(cashTransactionClassDTO -> cashTransactionClassDTO.setAssigned(true));
            }
        }else if (forOtherCO.getRange().equals("notChoose")){
            list = paymentService.listCashTransactionClassByRange(forOtherCO,page);
            if (list.size() > 0) {
                list.stream().forEach(cashTransactionClassDTO -> cashTransactionClassDTO.setAssigned(false));
            }
        }else if (forOtherCO.getRange().equals("all")){

            List<CashTransactionClassCO> transactionClassDTOList = paymentService.listCashTransactionClassByRange(forOtherCO, page);
            if (transactionClassDTOList.size() > 0) {
                if (transactionClassDTOList.size() < page.getSize() * page.getCurrent()) {
                    for (int i = (page.getCurrent() - 1) * page.getSize(); i < transactionClassDTOList.size(); i++) {
                        list.add(transactionClassDTOList.get(i));
                    }
                } else {
                    for (int i = (page.getCurrent() - 1) * page.getSize(); i < page.getCurrent() * page.getSize(); i++) {
                        list.add(transactionClassDTOList.get(i));
                    }
                }
            }
        }*/


        page.setRecords(list);
        return page;
    }


    /**
     * 获取当前用户有权限的单据类型
     * @return
     */
    public List<ExpenseReportType> getCurrentUserExpenseReportType(Boolean isAuth){
        Long currentSetOfBookId = OrgInformationUtil.getCurrentSetOfBookId();
        Long currentCompanyId = OrgInformationUtil.getCurrentCompanyId();
        Long currentDepartmentId = OrgInformationUtil.getCurrentDepartmentId();
        Long currentUserId = OrgInformationUtil.getCurrentUserId();
        List<ExpenseReportType> currentUserExpenseReportType = baseMapper.getCurrentUserExpenseReportType(currentDepartmentId, currentCompanyId, currentSetOfBookId);
        List<ExpenseReportType> useUserGroupTypes = currentUserExpenseReportType.stream().filter(e -> "1003".equals(e.getApplyEmployee())).collect(toList());
        if(com.baomidou.mybatisplus.toolkit.CollectionUtils.isNotEmpty(useUserGroupTypes)){
            // 筛选出不符合员工组条件的类型
            List<ExpenseReportType> notMatchUserGroupTypes = useUserGroupTypes.stream().filter(useUserGroupType -> {
                List<ExpenseReportTypeUserGroup> userGroups = expenseReportTypeUserGroupService.selectList(new EntityWrapper<ExpenseReportTypeUserGroup>()
                        .eq("report_type_id", useUserGroupType.getId()));
                JudgeUserCO judgeUserCO = new JudgeUserCO();
                judgeUserCO.setIdList(userGroups.stream().map(ExpenseReportTypeUserGroup::getUserGroupId).collect(toList()));
                judgeUserCO.setUserId(currentUserId);
                Boolean isExists = organizationService.judgeUserInUserGroups(judgeUserCO);
                return !isExists;
            }).collect(toList());
            currentUserExpenseReportType.removeAll(notMatchUserGroupTypes);
        }
        if (isAuth != null && isAuth) {
            currentUserExpenseReportType.addAll(listAuthorizedExpenseReportType());
            //根据ID去重
            currentUserExpenseReportType = currentUserExpenseReportType.stream().collect(
                    collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(ExpenseReportType::getId))), ArrayList::new)
            );
        }
        return currentUserExpenseReportType;
    }

    /**
     * 获取当前用户被授权的单据类型
     * @return
     */
    public List<ExpenseReportType> listAuthorizedExpenseReportType(){
        List<ExpenseReportType> expenseReportTypeList = new ArrayList<>();

        List<FormAuthorizeCO> formAuthorizeCOList = authorizeClient.listFormAuthorizeByDocumentCategoryAndUserId(FormTypeEnum.BULLETIN_BILL.getCode(), OrgInformationUtil.getCurrentUserId());

        for(FormAuthorizeCO item : formAuthorizeCOList) {
            OrganizationUserCO contactCO = new OrganizationUserCO();
            if (item.getMandatorId() != null) {
                contactCO = organizationService.getOrganizationCOByUserId(item.getMandatorId());
            }
            List<Long> typeIdList = expenseReportTypeCompanyService.selectList(
                    new EntityWrapper<ExpenseReportTypeCompany>()
                            .eq(item.getCompanyId() != null, "company_id", item.getCompanyId())
                            .eq(contactCO.getCompanyId() != null, "company_id", contactCO.getCompanyId())
                            .eq("enabled",true)
            ).stream().map(ExpenseReportTypeCompany::getReportTypeId).collect(Collectors.toList());
            if (typeIdList.size() == 0) {
                continue;
            }
            List<ExpenseReportType> expenseReportTypes = this.selectList(
                    new EntityWrapper<ExpenseReportType>()
                            .in(typeIdList.size() != 0, "id", typeIdList)
                            .eq(item.getFormId() != null, "id", item.getFormId())
                            .eq("enabled", true));

            expenseReportTypes = expenseReportTypes.stream().filter(expenseReportType -> {
                // 全部人员
                if ("1001".equals(expenseReportType.getApplyEmployee())){
                    return true;
                // 部门
                }else if("1002".equals(expenseReportType.getApplyEmployee())){
                    List<Long> deparmentIds = expenseReportTypeDepartmentService.selectList(new EntityWrapper<ExpenseReportTypeDepartment>()
                            .eq("report_type_id", expenseReportType.getId())).stream().map(ExpenseReportTypeDepartment::getDepartmentId).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(deparmentIds)) {
                        if (item.getMandatorId() != null) {
                            OrganizationUserCO userCO = organizationService.getOrganizationCOByUserId(item.getMandatorId());
                            if (!deparmentIds.contains(userCO.getDepartmentId())){
                                return false;
                            }
                        }
                        if (item.getUnitId() != null && !deparmentIds.contains(item.getUnitId())) {
                            return false;
                        }
                    }
                // 人员组
                }else if("1003".equals(expenseReportType.getApplyEmployee())){
                    List<Long> userGroupIds = expenseReportTypeUserGroupService.selectList(new EntityWrapper<ExpenseReportTypeUserGroup>()
                            .eq("report_type_id", expenseReportType.getId())).stream().map(ExpenseReportTypeUserGroup::getUserGroupId).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(userGroupIds)) {
                        if (item.getMandatorId() != null) {
                            JudgeUserCO judgeUserCO = JudgeUserCO.builder().idList(userGroupIds).userId(item.getMandatorId()).build();
                            if (!organizationService.judgeUserInUserGroups(judgeUserCO)) {
                                return false;
                            }
                        }
                        if (item.getUnitId() != null){
                            List<Long> userIds = organizationService.listUsersByDepartmentId(item.getUnitId()).stream().map(ContactCO::getId).collect(Collectors.toList());
                            for(Long e : userIds){
                                JudgeUserCO judgeUserCO = JudgeUserCO.builder().idList(userGroupIds).userId(e).build();
                                if (!organizationService.judgeUserInUserGroups(judgeUserCO)) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    }
                }
                return true;
            }).collect(Collectors.toList());
            expenseReportTypeList.addAll(expenseReportTypes);
        }
        return expenseReportTypeList;
    }

    /**
     * 根据报账类型获取明细配置
     * @param typeId
     * @param headerId
     * @return
     */
    public ExpenseReportTypeDTO getExpenseReportType(Long typeId, Long headerId){
        ExpenseReportType expenseReportType = selectById(typeId);
        if (expenseReportType == null){
            throw new BizException(RespCode.EXPENSE_REPORT_TYPE_IS_NUTT);
        }
        ExpenseReportTypeDTO expenseReportTypeDTO = new ExpenseReportTypeDTO();
        BeanUtils.copyProperties(expenseReportType,expenseReportTypeDTO);
        expenseReportTypeDTO.setExpenseDimensions(getExpenseTypeDimension(typeId,headerId));
        ExpenseReportTypeDistSetting reportTypeDistSetting =
                expenseReportTypeDistSettingService.selectOne(new EntityWrapper<ExpenseReportTypeDistSetting>().eq("report_type_id", typeId));
        if(reportTypeDistSetting.getCompanyDefaultId() != null){
            CompanyCO companyById = organizationService.getCompanyById(reportTypeDistSetting.getCompanyDefaultId());
            reportTypeDistSetting.setCompanyCode(companyById.getCompanyCode());
            reportTypeDistSetting.setCompanyName(companyById.getName());
        }
        if(reportTypeDistSetting.getDepartmentDefaultId() != null){
            DepartmentCO departmentById = organizationService.getDepartmentById(reportTypeDistSetting.getDepartmentDefaultId());
            reportTypeDistSetting.setDepartmentCode(departmentById.getDepartmentCode());
            reportTypeDistSetting.setDepartmentName(departmentById.getName());
        }
        if(reportTypeDistSetting.getResDefaultId() != null){
            ResponsibilityCenterCO responsibilityCenterById = organizationService.getResponsibilityCenterById(reportTypeDistSetting.getResDefaultId());
            reportTypeDistSetting.setResCode(responsibilityCenterById.getResponsibilityCenterCode());
            reportTypeDistSetting.setResName(responsibilityCenterById.getResponsibilityCenterName());
        }
        //返回付款方式类型name
        /*SysCodeValueCO sysCodeValueByCodeAndValue = organizationService.getSysCodeValueByCodeAndValue(SystemCustomEnumerationType.CSH_PAYMENT_TYPE, expenseReportType.getPaymentMethod());
        if (sysCodeValueByCodeAndValue != null) {
            expenseReportTypeDTO.setPaymentMethodName(sysCodeValueByCodeAndValue.getName());
        }*/
        //返回付款方式name
        SysCodeValueCO sysCodeValue = organizationService.getSysCodeValueByCodeAndValue("ZJ_PAYMENT_TYPE", expenseReportType.getPaymentType());
        if (sysCodeValue != null) {
            expenseReportType.setPaymentTypeName(sysCodeValue.getName());
        }
        expenseReportTypeDTO.setExpenseReportTypeDistSetting(reportTypeDistSetting);
        return expenseReportTypeDTO;
    }

    /**
     * 获取部分付款用途范围
     * @param typeId
     */
    public List<CashTransactionClassCO> getExpenseReportTypeCashTransactionClasses(Long typeId,
                                                                                   String code,
                                                                                   String name,
                                                                                   Page page) throws URISyntaxException {
        ExpenseReportType expenseReportType = selectById(typeId);
        CashTransactionClassForOtherCO cashTransactionClassForOtherCO = new CashTransactionClassForOtherCO();
        cashTransactionClassForOtherCO.setClassCode(code);
        cashTransactionClassForOtherCO.setDescription(name);
        cashTransactionClassForOtherCO.setSetOfBookId(expenseReportType.getSetOfBooksId());

        if(BooleanUtils.isNotTrue(expenseReportType.getAllCashTransactionClass())){
            cashTransactionClassForOtherCO.setRange("selected");
            List<ExpenseReportTypeTransactionClass> transactionClasses = expenseReportTypeTransactionClassService.
                    selectList(new EntityWrapper<ExpenseReportTypeTransactionClass>()
                    .eq("report_type_id", expenseReportType.getId()));
            List<Long> collect = transactionClasses.stream().map(e -> e.getTransactionClassId()).collect(toList());
            cashTransactionClassForOtherCO.setTransactionClassIdList(collect);
        }else{
            cashTransactionClassForOtherCO.setRange("all");
        }
        return paymentService.listCashTransactionClassByRange(cashTransactionClassForOtherCO,page);
    }

    /**
     * 获取部分费用类型
     * @param typeId
     * @param page
     */
    public List<ExpenseTypeWebDTO> getExpenseReportTypeExpenseType(Long typeId,
                                                                   Long employeeId,
                                                                   Long companyId,
                                                                   Long departmentId,
                                                                   Long categoryId,
                                                                   String expenseTypeName,
                                                                   Page page){
        ExpenseReportType expenseReportType = selectById(typeId);
        ExpenseBO expenseBO = new ExpenseBO();
        expenseBO.setTypeFlag(1);
        expenseBO.setSetOfBooksId(expenseReportType.getSetOfBooksId());
        expenseBO.setCompanyId(companyId);
        expenseBO.setDepartmentId(departmentId);
        expenseBO.setEmployeeId(employeeId);
        expenseBO.setCategoryId(categoryId);
        expenseBO.setExpenseTypeName(expenseTypeName);
        if(BooleanUtils.isNotTrue(expenseReportType.getAllExpenseFlag())){
            List<ExpenseReportTypeExpenseType> expenseReportTypeExpenseTypes = expenseReportTypeExpenseTypeService.selectList(new EntityWrapper<ExpenseReportTypeExpenseType>()
                    .eq("report_type_id", typeId));
            List<Long> collect = expenseReportTypeExpenseTypes.stream().map(e -> e.getExpenseTypeId()).collect(toList());
            expenseBO.setAllFlag(false);
            expenseBO.setAssignTypeIds(collect);
        }else{
            expenseBO.setAllFlag(true);
        }
        return expenseTypeService.lovExpenseType(expenseBO,page);
    }

    /**
     * 获取部分费用类型
     * @param typeId
     */
    public List<ExpenseType> getExpenseReportTypeExpenseType(Long typeId,
                                                             String code,
                                                             String name){
        ExpenseReportType expenseReportType = selectById(typeId);
        if(BooleanUtils.isNotTrue(expenseReportType.getAllExpenseFlag())){
            List<ExpenseReportTypeExpenseType> expenseTypes = expenseReportTypeExpenseTypeService.selectList(new EntityWrapper<ExpenseReportTypeExpenseType>()
                    .eq("report_type_id", typeId));
            List<Long> collect = expenseTypes.stream().map(e -> e.getExpenseTypeId()).collect(toList());
            return expenseTypeService.selectList(new EntityWrapper<ExpenseType>()
                    .in("id",collect)
                    .like(StringUtils.isNotEmpty(code),"code",code)
                    .like(StringUtils.isNotEmpty(name),"name",name)
                    .eq("enabled",true)
                    .orderBy("sequence"));
        }else{
            return expenseTypeService.queryLovByDocumentTypeAssign(expenseReportType.getSetOfBooksId(),
                    "all",
                    ExpenseDocumentTypeEnum.PUBLIC_REPORT.getKey(),
                    expenseReportType.getId(),
                    code,
                    name,
                    null,
                    1,
                    null);
        }
    }

    /**
     * 获取单据维度布局
     * @param typeId
     * @param headerId
     * @return
     */
    public List<ExpenseDimension> getExpenseTypeDimension(Long typeId, Long headerId){
        List<ExpenseDimension> expenseDimensions = Arrays.asList();
        // 当单据ID为空时，表示为创建单据
        if(headerId == null){
            expenseDimensions = queryTypeDimensionByIdWhenCreateDocument(typeId);
        }else{
            expenseDimensions = queryTypeDimensionByIdWhenCreated(headerId);
        }
        return expenseDimensions;
    }

    /**
     * 已建单据获取维度配置
     * @param headerId             报账单ID
     * @return
     */
    private List<ExpenseDimension> queryTypeDimensionByIdWhenCreated(Long headerId) {
        //获取对公报账维度布局
        List<ExpenseDimension> expenseDimensions =
                expenseDimensionService.listDimensionByHeaderIdAndType(headerId, ExpenseDocumentTypeEnum.PUBLIC_REPORT.getKey(), null);
        // 根据维度ID查询相关维度信息
        if (!CollectionUtils.isEmpty(expenseDimensions)){
            expenseDimensions.stream().sorted(Comparator.comparing(ExpenseDimension::getSequence)).forEach(expenseDimension -> {
                DimensionCO dimensionById = organizationService.getDimensionById(expenseDimension.getDimensionId());
                expenseDimension.setName(dimensionById.getDimensionName());
                if(expenseDimension.getValue() != null){
                    DimensionItemCO dimensionItemById = organizationService.getDimensionItemById(expenseDimension.getValue());
                    expenseDimension.setValue(dimensionItemById.getId());
                    expenseDimension.setValueName(dimensionItemById.getDimensionItemName());
                }
            });
        }
        return expenseDimensions;
    }

    /**
     * 新建报账单时，获取单据维度配置
     * @param typeId             报账单类型ID
     * @return
     */
    private List<ExpenseDimension> queryTypeDimensionByIdWhenCreateDocument(Long typeId) {
        List<ExpenseReportTypeDimension> typeDimensions = dimensionService.selectList(
                new EntityWrapper<ExpenseReportTypeDimension>()
                        .eq("report_type_id", typeId)
                        .orderBy("sequence_number", true));

        List<ExpenseDimension> dimensions = new ArrayList<>();
        // 根据维度ID查询相关维度信息
        if (!CollectionUtils.isEmpty(typeDimensions)){
            dimensions = typeDimensions.stream().map(typeDimension -> {
                DimensionCO dimensionById = organizationService.getDimensionById(typeDimension.getDimensionId());
                if(!dimensionById.getEnabled()){
                    return null;
                }
                ExpenseDimension expenseDimension = new ExpenseDimension();
                expenseDimension.setDimensionField("dimension" + dimensionById.getDimensionSequence() + "Id");
                expenseDimension.setDimensionId(typeDimension.getDimensionId());
                expenseDimension.setHeaderFlag("HEADER".equals(typeDimension.getPosition()));
                expenseDimension.setSequence(typeDimension.getSequenceNumber());
                expenseDimension.setRequiredFlag(typeDimension.getMustEnter());
                expenseDimension.setName(dimensionById.getDimensionName());
                if(typeDimension.getDefaultValueId() != null){
                    DimensionItemCO dimensionItemById = organizationService.getDimensionItemById(typeDimension.getDefaultValueId());
                    expenseDimension.setValue(dimensionItemById.getId());
                    expenseDimension.setValueName(dimensionItemById.getDimensionItemName());
                }
                return expenseDimension;
            }).filter(e -> e != null).collect(toList());
        }
        return dimensions;
    }

    /**
     * 根据公司id查询报账单类型
     * @param companyId
     * @return
     */
    public List<ExpenseReportType> getExpenseReprotTypeByCompanyId(Long companyId) {
        List<ExpenseReportTypeCompany> typeCompanys = expenseReportTypeCompanyService.getReportTypeByCompanyId(companyId);
        List<Long> typeIds = typeCompanys.stream().map(ExpenseReportTypeCompany::getReportTypeId).collect(toList());
        return baseMapper.selectList(new EntityWrapper<ExpenseReportType>()
                .in(!CollectionUtils.isEmpty(typeIds),"id",typeIds)
                .eq("enabled",true));
    }

    public List<ExpenseReportType> getExpenseReportTypeByFormTypes(Long setOfBooksId, List<Long> formTypes) {

       List<ExpenseReportType> list=  baseMapper.getExpenseReportTypeByFormTypes(
                new EntityWrapper<ExpenseReportType>()
                .where("et.deleted = false")
                .eq("et.set_of_books_id",setOfBooksId)
                .in(formTypes!=null,"et.id",formTypes)
                .orderBy("et.enabled",false)
                .orderBy("et.report_type_code"));
        list = baseI18nService.selectListTranslatedTableInfoWithI18nByEntity(list,ExpenseReportType.class);
        for (ExpenseReportType expenseReportType : list){
            //返回账套code、账套name
            SetOfBooksInfoCO setOfBooksInfoCOById = organizationService.getSetOfBooksInfoCOById(expenseReportType.getSetOfBooksId(), false);
            if (setOfBooksInfoCOById != null) {
                expenseReportType.setSetOfBooksCode(setOfBooksInfoCOById.getSetOfBooksCode());
                expenseReportType.setSetOfBooksName(setOfBooksInfoCOById.getSetOfBooksName());
            }

            //返回付款方式类型name
            /*SysCodeValueCO sysCodeValueByCodeAndValue = organizationService.getSysCodeValueByCodeAndValue(SystemCustomEnumerationType.CSH_PAYMENT_TYPE, expenseReportType.getPaymentMethod());
            if (sysCodeValueByCodeAndValue != null) {
                expenseReportType.setPaymentMethodName(sysCodeValueByCodeAndValue.getName());
            }*/

            //返回付款方式name
            SysCodeValueCO sysCodeValue = organizationService.getSysCodeValueByCodeAndValue("ZJ_PAYMENT_TYPE", expenseReportType.getPaymentType());
            if (sysCodeValue != null) {
                expenseReportType.setPaymentTypeName(sysCodeValue.getName());
            }

            //返回关联表单名称formName
            ApprovalFormCO approvalFormById = organizationService.getApprovalFormById(expenseReportType.getFormId());
            if (approvalFormById != null){
                expenseReportType.setFormName(approvalFormById.getFormName());
            }
        }
        return list;
    }

    /**
     * 根据报账单类型ID获取所有有权限创建该单据的人员
     * @param id
     * @return
     */
    public List<ContactCO> listUsersByExpenseReportType(Long id, String userCode, String userName, Page queryPage) {
        List<ContactCO> userCOList = new ArrayList<>();

        ExpenseReportType expenseReportType = this.selectById(id);
        if (expenseReportType == null){
            return userCOList;
        }

        List<Long> companyIdList = expenseReportTypeCompanyService.selectList(
                new EntityWrapper<ExpenseReportTypeCompany>()
                        .eq("enabled", true)
                        .eq("report_type_id", id)
        ).stream().map(ExpenseReportTypeCompany::getCompanyId).collect(toList());
        if (companyIdList.size() == 0){
            return userCOList;
        }

        List<Long> departmentIdList = null;
        List<Long> userGroupIdList = null;

        // 部门
        if ("1002".equals(expenseReportType.getApplyEmployee())){
            departmentIdList = expenseReportTypeDepartmentService.selectList(new EntityWrapper<ExpenseReportTypeDepartment>()
                    .eq("report_type_id", expenseReportType.getId())).stream().map(ExpenseReportTypeDepartment::getDepartmentId).collect(Collectors.toList());
        }
        // 人员组
        if ("1003".equals(expenseReportType.getApplyEmployee())){
            userGroupIdList = expenseReportTypeUserGroupService.selectList(new EntityWrapper<ExpenseReportTypeUserGroup>()
                    .eq("report_type_id", expenseReportType.getId())).stream().map(ExpenseReportTypeUserGroup::getUserGroupId).collect(Collectors.toList());
        }

        AuthorizeQueryCO queryCO = AuthorizeQueryCO
                .builder()
                .documentCategory(FormTypeEnum.BULLETIN_BILL.getCode())
                .formTypeId(id)
                .companyIdList(companyIdList)
                .departmentIdList(departmentIdList)
                .userGroupIdList(userGroupIdList)
                .currentUserId(OrgInformationUtil.getCurrentUserId())
                .build();
        //jiu.zhao 修改三方接口
        //Page<ContactCO> contactCOPage = authorizeClient.pageUsersByAuthorizeAndCondition(queryCO, userCode, userName, queryPage);
        Page<ContactCO> contactCOPage = authorizeClient.pageUsersByAuthorizeAndCondition(queryCO, userCode, userName, queryPage.getCurrent() - 1, queryPage.getSize());
        queryPage.setTotal(contactCOPage.getTotal());
        userCOList = contactCOPage.getRecords();

        return userCOList;
    }

    /**
     * 条件查询账套下报账单类型
     * @param code
     * @param name
     * @param setOfBooksId
     * @param page
     * @return
     */
    public List<ExpenseReportType> getExpenseReportType(String code,String name,Long setOfBooksId,Page page){
        return baseMapper.selectPage(page,new EntityWrapper<ExpenseReportType>()
                .eq("set_of_books_id",setOfBooksId)
                .like(StringUtils.isNotEmpty(code),"report_type_code",code)
                .like(StringUtils.isNotEmpty(name),"report_type_name",name));
    }

    /**
     * 批量导入费用报账单类型定义-关联费用类型
     * @param sourceTypeTargetTypeDTOS
     * @return
     */
    @Transactional
    public Map<String,String> expExpenseReportTypeExpenseType(List<SourceTypeTargetTypeDTO> sourceTypeTargetTypeDTOS) {
        //报账单数据 用于修改费用类型标志
        List<ExpenseReportType> expenseReportTypeList = new ArrayList<>();
        //要插入的数据
        List<ExpenseReportTypeExpenseType> expenseReportTypeExpenseTypeList = new ArrayList<>();
        //错误信息
        Map<String,String> resultMap = new HashMap<>();
        int line = 1;
        //校验成功标志
        Boolean successFlag = true;
        for (SourceTypeTargetTypeDTO sourceTypeTargetTypeDTO : sourceTypeTargetTypeDTOS) {
            String errorMessage = "";
            //校验重复信息
            String repeatMessage = checkRepeat(sourceTypeTargetTypeDTOS, sourceTypeTargetTypeDTO);
            if (!repeatMessage.equals("")){
                errorMessage += "与第 " + repeatMessage + " 行数据重复,";
            }
            //账套code
            String setOfBooksCode = sourceTypeTargetTypeDTO.getSetOfBooksCode();
            //费用类型代码
            String expenseTypeCode = sourceTypeTargetTypeDTO.getTargetTypeCode();
            //报账单类型代码
            String expenseReportTypeCode = sourceTypeTargetTypeDTO.getSourceTypeCode();
            if (StringUtil.isNullOrEmpty(setOfBooksCode) || StringUtil.isNullOrEmpty(expenseTypeCode)
                    || StringUtil.isNullOrEmpty(expenseReportTypeCode)) {
                errorMessage += "必输字段为空";
            }else {
                Long setOfBooksId = null;
                List<SetOfBooksInfoCO> setOfBooksList = organizationService.getSetOfBooksBySetOfBooksCode(setOfBooksCode);
                if (CollectionUtils.isEmpty(setOfBooksList)) {
                    errorMessage += "未找到账套";
                }else if (setOfBooksList.size() > 1) {
                    errorMessage += "找到多个账套";
                }else {
                    //账套id
                    setOfBooksId = setOfBooksList.get(0).getId();
                    ExpenseReportType expenseReportType = new ExpenseReportType();
                    expenseReportType.setSetOfBooksId(setOfBooksId);
                    expenseReportType.setReportTypeCode(expenseReportTypeCode);
                    ExpenseReportType ExpenseReportTypeResult = baseMapper.selectOne(expenseReportType);
                    if (ExpenseReportTypeResult == null) {
                        errorMessage += "在当前帐套下,没有找到该报账单类型代码";
                    }else {
                        //报账单类型id
                        Long reportTypeId = ExpenseReportTypeResult.getId();

                        //费用类型id
                        Long expenseTypeId = expenseTypeService.queryExpenseTypeIdByExpenseTypeCode(setOfBooksId, expenseTypeCode);
                        if (expenseTypeId == null) {
                            errorMessage += "在当前帐套下,没有找到该费用类型代码";
                        }else {
                            //构造插入数据
                            ExpenseReportTypeExpenseType expenseReportTypeExpenseType = new ExpenseReportTypeExpenseType();
                            expenseReportTypeExpenseType.setReportTypeId(reportTypeId);
                            expenseReportTypeExpenseType.setExpenseTypeId(expenseTypeId);
                            //判断是否已存在
                            if ( expenseReportTypeExpenseTypeService.selectList(
                                    new EntityWrapper<ExpenseReportTypeExpenseType>()
                                            .eq("report_type_id",expenseReportTypeExpenseType.getReportTypeId())
                                            .eq("expense_type_id",expenseReportTypeExpenseType.getExpenseTypeId())
                            ).size() > 0 ){
                                errorMessage += "费用类型已在该报账单类型下";
                            }else {
                                //修改报账单关联类型为部分类型
                                ExpenseReportTypeResult.setAllExpenseFlag(false);
                                expenseReportTypeList.add(ExpenseReportTypeResult);
                                expenseReportTypeExpenseTypeList.add(expenseReportTypeExpenseType);
                            }
                        }
                    }
                }
            }
            if (!errorMessage.equals("")){
                resultMap.put("第" + line + "行",errorMessage);
                successFlag = false;
            }
            line++;
        }
        System.out.println(successFlag);
        //如果数据没有错误信息
        if (successFlag) {
            System.out.println(expenseReportTypeList);
            System.out.println(expenseReportTypeExpenseTypeList);
            //更新为部分类型
            for ( ExpenseReportType expenseReportType : expenseReportTypeList) {
                baseMapper.updateById(expenseReportType);
            }
            //插入数据
            for (ExpenseReportTypeExpenseType expenseReportTypeExpenseType : expenseReportTypeExpenseTypeList) {
                expenseReportTypeExpenseTypeService.insert(expenseReportTypeExpenseType);
            }
            return null;
        }
        return resultMap;
    }

    private String checkRepeat(List<SourceTypeTargetTypeDTO> sourceTypeTargetTypeDTOS,
                               SourceTypeTargetTypeDTO sourceTypeTargetTypeDTO){
        String errorMessage = "";
        int line = 1;
        for (SourceTypeTargetTypeDTO typeTargetTypeDTO : sourceTypeTargetTypeDTOS) {
            boolean isRepeat = sourceTypeTargetTypeDTO.equals(typeTargetTypeDTO);
            if (isRepeat){
                if (errorMessage.equals("")){
                    errorMessage += line;
                }else {
                    errorMessage += "," + line;
                }
            }
            line++;
        }
        return errorMessage;
    }

    /**
     * 根据费用类型获取当前用户有权限创建，且有相应费用类型的报账单类型
     * @param expenseTypeIds
     * @return
     */
    public List<ExpenseReportType> getExpenseReportTypeByExpenseTypeIds(Collection<Long> expenseTypeIds){
        //当前用户有权限创建的单据类型
        List<ExpenseReportType> currentUserExpenseReportTypes = getCurrentUserExpenseReportType(false);
        if(com.baomidou.mybatisplus.toolkit.CollectionUtils.isNotEmpty(currentUserExpenseReportTypes)){
            Wrapper<ExpenseReportTypeExpenseType> wrapper = new EntityWrapper<ExpenseReportTypeExpenseType>();
            currentUserExpenseReportTypes.stream().forEach(expenseReportType -> {
                wrapper.orNew();
                expenseTypeIds.stream().forEach(expenseTypeId -> {
                    String sql = "select 1 from dual where report_type_id = " + expenseReportType.getId() + " and expense_type_id = " + expenseTypeId;
                    wrapper.exists(sql);
                });

            });
            List<ExpenseReportTypeExpenseType> list = expenseReportTypeExpenseTypeService.selectList(wrapper);
            return currentUserExpenseReportTypes.stream().filter(e -> {
                return list.stream().anyMatch(m -> e.getId().equals(m.getReportTypeId()));
            }).collect(toList());
        }
        return Arrays.asList();
    }

}
