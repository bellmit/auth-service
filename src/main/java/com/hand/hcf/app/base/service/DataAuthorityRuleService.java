package com.hand.hcf.app.base.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.hand.hcf.app.base.domain.DataAuthorityRuleDetailValue;
import com.hand.hcf.app.base.dto.DataAuthRuleDetailValueDTO;
import com.hand.hcf.app.client.com.CompanyService;
import com.hand.hcf.app.client.com.CompanySumDTO;
import com.hand.hcf.app.client.department.DepartmentInfoDTO;
import com.hand.hcf.app.client.department.DepartmentService;
import com.hand.hcf.app.client.org.ObjectIdsDTO;
import com.hand.hcf.app.client.sob.SetOfBooksInfoDTO;
import com.hand.hcf.app.client.sob.SobService;
import com.hand.hcf.app.client.user.UserInfoDTO;
import com.hand.hcf.app.client.user.UserService;
import com.hand.hcf.core.exception.BizException;
import com.hand.hcf.core.service.BaseI18nService;
import com.hand.hcf.core.service.BaseService;
import com.hand.hcf.app.base.util.RespCode;
import com.hand.hcf.app.base.domain.DataAuthorityRule;
import com.hand.hcf.app.base.domain.DataAuthorityRuleDetail;
import com.hand.hcf.app.base.persistence.DataAuthorityRuleMapper;
import com.hand.hcf.core.util.DataAuthorityUtil;
import com.hand.hcf.core.util.LoginInformationUtil;
import com.hand.hcf.core.util.PageUtil;
import com.hand.hcf.core.util.TypeConversionUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kai.zhang05@hand-china.com
 * @create 2018/10/12 16:49
 * @remark
 */
@Service
@AllArgsConstructor
public class DataAuthorityRuleService extends BaseService<DataAuthorityRuleMapper,DataAuthorityRule>{

    private final DataAuthorityRuleMapper dataAuthorityRuleMapper;
    private final DataAuthorityRuleDetailService dataAuthorityRuleDetailService;
    private final DataAuthorityRuleDetailValueService dataAuthorityRuleDetailValueService;
    private final BaseI18nService baseI18nService;
    private final DepartmentService departmentService;
    private final CompanyService companyService;
    private final SobService sobService;
    private final UserService userService;

    /**
     * 添加数据权限规则
     * @param entity
     * @return
     */
    @Transactional
    public DataAuthorityRule saveDataAuthorityRule(DataAuthorityRule entity){
        Integer count = dataAuthorityRuleMapper.selectCount(new EntityWrapper<DataAuthorityRule>()
                .eq("data_authority_id", entity.getDataAuthorityId())
                .eq("data_authority_rule_name", entity.getDataAuthorityRuleName())
                .eq("deleted",false)
                .ne(entity.getId() != null,"id",entity.getId()));
        if(count > 0){
            throw new BizException(RespCode.DATA_AUTHORITY_RULE_EXISTS);
        }
        if(entity.getId() != null){
            dataAuthorityRuleMapper.updateById(entity);
        }else {
            dataAuthorityRuleMapper.insert(entity);
        }
        if(CollectionUtils.isNotEmpty(entity.getDataAuthorityRuleDetails())){
            dataAuthorityRuleDetailService.saveDataAuthorityRuleDetailBatch(entity.getDataAuthorityRuleDetails(),entity.getId(),entity.getDataAuthorityId());
        }
        return entity;
    }

    @Transactional
    public DataAuthorityRule saveDataAuthorityRule(DataAuthorityRule entity,Long dataAuthorityId){
        if(entity.getDataAuthorityId() == null){
            entity.setDataAuthorityId(dataAuthorityId);
        }
        return saveDataAuthorityRule(entity);
    }

    /**
     * 批量添加数据权限规则
     * @param entities
     * @return
     */
    @Transactional
    public List<DataAuthorityRule> saveDataAuthorityRuleBatch(List<DataAuthorityRule> entities){
        entities.forEach(entity -> saveDataAuthorityRule(entity));
        return entities;
    }

    @Transactional
    public List<DataAuthorityRule> saveDataAuthorityRuleBatch(List<DataAuthorityRule> entities,Long dataAuthorityId) {
        entities.forEach(entity -> saveDataAuthorityRule(entity, dataAuthorityId));
        return entities;
    }

    /**
     * 根据权限ID获取权限
     * @param dataAuthorityId
     * @return
     */
    public List<DataAuthorityRule> queryDataAuthorityRules(Long dataAuthorityId){
        List<DataAuthorityRule> dataAuthRules = dataAuthorityRuleMapper.selectList(new EntityWrapper<DataAuthorityRule>()
                .eq("data_authority_id", dataAuthorityId));
        dataAuthRules.forEach(dataAuthorityRule -> {
            Map<String, List<Map<String, String>>> i18nMap = baseI18nService.getI18nMap(DataAuthorityRule.class, dataAuthorityRule.getId());
            dataAuthorityRule.setI18n(i18nMap);
            List<DataAuthorityRuleDetail> dataAuthorityRuleDetails = dataAuthorityRuleDetailService.queryDataAuthorityRuleDetailsByRuleId(dataAuthorityRule.getId());
            dataAuthorityRule.setDataAuthorityRuleDetails(dataAuthorityRuleDetails);
        });
        return dataAuthRules;
    }

    private void deleteDataAuthRule(DataAuthorityRule dataAuthorityRule){
        dataAuthorityRule.setDeleted(true);
        dataAuthorityRule.setDataAuthorityRuleName(dataAuthorityRule.getDataAuthorityRuleName() + "_DELETED_" + RandomStringUtils.randomNumeric(6));
        dataAuthorityRuleMapper.updateById(dataAuthorityRule);
    }

    /**
     * 根据数据权限ID删除规则
     * @param authId
     */
    @Transactional
    public void deleteDataAuthRuleByAuthId(Long authId){
        List<DataAuthorityRule> dataAuths = dataAuthorityRuleMapper.selectList(new EntityWrapper<DataAuthorityRule>().eq("data_authority_id", authId));
        dataAuths.stream().forEach(dataAuthorityRule -> {
            deleteDataAuthRule(dataAuthorityRule);
        });
    }

    @Transactional
    public void deleteDataAuthRuleAndDetail(Long id){
        DataAuthorityRule dataAuthorityRule = dataAuthorityRuleMapper.selectById(id);
        deleteDataAuthRule(dataAuthorityRule);
        List<DataAuthorityRuleDetail> ruleDetails = dataAuthorityRuleDetailService.selectList(new EntityWrapper<DataAuthorityRuleDetail>().eq("data_authority_rule_id", id));
        if(CollectionUtils.isNotEmpty(ruleDetails)){
            dataAuthorityRuleDetailValueService.delete(new EntityWrapper<DataAuthorityRuleDetailValue>().in("data_auth_rule_detail_id",ruleDetails.stream().map(ruleDetail -> ruleDetail.getId()).collect(Collectors.toList())));
            dataAuthorityRuleDetailService.delete(new EntityWrapper<DataAuthorityRuleDetail>().eq("data_authority_rule_id", id));
        }
    }

    public List<DataAuthRuleDetailValueDTO> getDataAuthRuleDetailValuesByDataType(Long ruleId, String dataType,Page page){
        DataAuthorityRuleDetail dataAuthorityRuleDetail = dataAuthorityRuleDetailService.selectOne(new EntityWrapper<DataAuthorityRuleDetail>()
                .eq("data_authority_rule_id", ruleId).eq("data_type", dataType));
        switch (dataAuthorityRuleDetail.getDataScope()){
            // 全部
            case "1001" :{
                if(DataAuthorityUtil.SOB_COLUMN.equals(dataType)){
                    Page<SetOfBooksInfoDTO> setOfBooksListByTenantId = sobService.getSetOfBooksListByTenantId(LoginInformationUtil.getCurrentTenantID(), page);
                    page.setTotal(setOfBooksListByTenantId.getTotal());
                    return setOfBooksToDetailValueDTO(setOfBooksListByTenantId.getRecords());
                }else if(DataAuthorityUtil.COMPANY_COLUMN.equals(dataType)){
                    Page<CompanySumDTO> companiesByTenantId = companyService.getCompaniesByTenantId(LoginInformationUtil.getCurrentTenantID(), page);
                    page.setTotal(companiesByTenantId.getTotal());
                    return companyToDetailValueDTO(companiesByTenantId.getRecords());
                }else if(DataAuthorityUtil.UNIT_COLUMN.equals(dataType)){
                    Page<DepartmentInfoDTO> departmentInfoByTenantId = departmentService.getDepartmentInfoByTenantId(LoginInformationUtil.getCurrentTenantID(), page);
                    page.setTotal(departmentInfoByTenantId.getTotal());
                    return departmentToDetailValueDTO(departmentInfoByTenantId.getRecords());
                }else if(DataAuthorityUtil.EMPLOYEE_COLUMN.equals(dataType)){
                    Page<UserInfoDTO> usersByTenantId = userService.getUsersByTenantId(LoginInformationUtil.getCurrentTenantID(), page);
                    page.setTotal(usersByTenantId.getTotal());
                    return userToDetailValueDTO(usersByTenantId.getRecords());
                }
                break;
            }
            // 当前
            case "1002":{
                page.setTotal(1);
                if(DataAuthorityUtil.SOB_COLUMN.equals(dataType)){
                    SetOfBooksInfoDTO setOfBookById = sobService.getSetOfBookById(LoginInformationUtil.getCurrentSetOfBookID());
                    return setOfBooksToDetailValueDTO(Arrays.asList(setOfBookById));
                }else if(DataAuthorityUtil.COMPANY_COLUMN.equals(dataType)){
                    CompanySumDTO companyById = companyService.findCompanyById(LoginInformationUtil.getCurrentCompanyID());
                    return companyToDetailValueDTO(Arrays.asList(companyById));
                }else if(DataAuthorityUtil.UNIT_COLUMN.equals(dataType)){
                    Long unitIdByUserId = departmentService.findDepartmentByUserOid(LoginInformationUtil.getCurrentUserOID().toString()).getDepartmentId();
                    return departmentToDetailValueDTO(departmentService.getDepartmentByDepartmentIds(Arrays.asList(unitIdByUserId)));
                }else if(DataAuthorityUtil.EMPLOYEE_COLUMN.equals(dataType)){
                    UserInfoDTO userInfoDTO = userService.selectUsersByUserId(LoginInformationUtil.getCurrentUserID());
                    return userToDetailValueDTO(Arrays.asList(userInfoDTO));
                }
                break;
            }
            // 当前及下属
            case "1003":{
                if(DataAuthorityUtil.COMPANY_COLUMN.equals(dataType)){
                    Page<CompanySumDTO> sonAndOwnCompanyByCond = companyService.getSonAndOwnCompanyByCond(LoginInformationUtil.getCurrentCompanyID(),
                            null, null, null, null, page);
                    page.setTotal(sonAndOwnCompanyByCond.getTotal());
                    return companyToDetailValueDTO(sonAndOwnCompanyByCond.getRecords());
                }else if(DataAuthorityUtil.UNIT_COLUMN.equals(dataType)){
                    Long unitIdByUserId = departmentService.findDepartmentByUserOid(LoginInformationUtil.getCurrentUserOID().toString()).getDepartmentId();
                    Page<DepartmentInfoDTO> unitChildrenAndOwnByUnitId = departmentService.getUnitChildrenAndOwnByUnitId(unitIdByUserId,page);
                    page.setTotal(unitChildrenAndOwnByUnitId.getTotal());
                    return departmentToDetailValueDTO(unitChildrenAndOwnByUnitId.getRecords());
                }else if(DataAuthorityUtil.EMPLOYEE_COLUMN.equals(dataType)){
                    return new ArrayList<>();
                }
                break;
            }
            // 手工选择
            case "1004":{
                List<DataAuthorityRuleDetailValue> dataAuthorityRuleDetailValues = dataAuthorityRuleDetailValueService.
                        queryAllDataAuthorityRuleDetailValues(dataAuthorityRuleDetail.getId());
                List<Long> keyIds = dataAuthorityRuleDetailValues.stream()
                        .map(e -> TypeConversionUtils.parseLong(e.getValueKey())).collect(Collectors.toList());
                if(DataAuthorityUtil.SOB_COLUMN.equals(dataType)){
                    List<SetOfBooksInfoDTO> setOfBooksListByIds = sobService.getSetOfBooksListByIds(keyIds);
                    return setOfBooksToDetailValueDTO(PageUtil.pageHandler(
                            page, setOfBooksListByIds.stream().sorted(Comparator.comparing(e -> e.getSetOfBooksCode())).collect(Collectors.toList())));
                }else if(DataAuthorityUtil.COMPANY_COLUMN.equals(dataType)){
                    List<ObjectIdsDTO> companiesByIds = companyService.getCompanysByIds(keyIds);
                    List<DataAuthRuleDetailValueDTO> collect = companiesByIds.stream().sorted(Comparator.comparing(e -> e.getObjectCode())).map(e -> {
                        return DataAuthRuleDetailValueDTO.builder()
                                .valueKey(e.getObjectId().toString())
                                .valueKeyCode(e.getObjectCode())
                                .valueKeyDesc(e.getObjectName()).build();
                    }).collect(Collectors.toList());
                    return PageUtil.pageHandler(page,collect);
                }else if(DataAuthorityUtil.UNIT_COLUMN.equals(dataType)){
                    List<DepartmentInfoDTO> departmentByDepartmentIds = departmentService.getDepartmentByDepartmentIds(keyIds);
                    return departmentToDetailValueDTO(PageUtil.pageHandler(page,
                            departmentByDepartmentIds.stream().sorted(Comparator.comparing(e->e.getDepartmentCode())).collect(Collectors.toList())));
                }else if(DataAuthorityUtil.EMPLOYEE_COLUMN.equals(dataType)){
                    List<UserInfoDTO> userInfoDTOS = userService.selectUsersByUserIds(keyIds);
                    return userToDetailValueDTO(PageUtil.pageHandler(page,
                            userInfoDTOS.stream().sorted(Comparator.comparing(e->e.getEmployeeID())).collect(Collectors.toList())));
                }
                break;
            }
            default: return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    private List<DataAuthRuleDetailValueDTO> setOfBooksToDetailValueDTO(List<SetOfBooksInfoDTO> setOfBooksInfoDTOS){
        if(CollectionUtils.isEmpty(setOfBooksInfoDTOS)){
            return new ArrayList<>();
        }
        return setOfBooksInfoDTOS.stream().map(e -> {
            return DataAuthRuleDetailValueDTO.builder()
                    .valueKey(e.getId().toString())
                    .valueKeyCode(e.getSetOfBooksCode())
                    .valueKeyDesc(e.getSetOfBooksName()).build();
        }).collect(Collectors.toList());
    }

    private List<DataAuthRuleDetailValueDTO> companyToDetailValueDTO(List<CompanySumDTO> companySumDTOS){
        if(CollectionUtils.isEmpty(companySumDTOS)){
            return new ArrayList<>();
        }
        return companySumDTOS.stream().map(e -> {
            return DataAuthRuleDetailValueDTO.builder()
                    .valueKey(e.getId().toString())
                    .valueKeyCode(e.getCompanyCode())
                    .valueKeyDesc(e.getName()).build();
        }).collect(Collectors.toList());
    }

    private List<DataAuthRuleDetailValueDTO> departmentToDetailValueDTO(List<DepartmentInfoDTO> departmentInfoDTOS){
        if(CollectionUtils.isEmpty(departmentInfoDTOS)){
            return new ArrayList<>();
        }
        return departmentInfoDTOS.stream().map(e -> {
            return DataAuthRuleDetailValueDTO.builder()
                    .valueKey(e.getId().toString())
                    .valueKeyCode(e.getDepartmentCode())
                    .valueKeyDesc(e.getName()).build();
        }).collect(Collectors.toList());
    }

    private List<DataAuthRuleDetailValueDTO> userToDetailValueDTO(List<UserInfoDTO> userInfoDTOS){
        if(CollectionUtils.isEmpty(userInfoDTOS)){
            return new ArrayList<>();
        }
        return userInfoDTOS.stream().map(e -> {
            return DataAuthRuleDetailValueDTO.builder()
                    .valueKey(e.getId().toString())
                    .valueKeyCode(e.getEmployeeID())
                    .valueKeyDesc(e.getFullName()).build();
        }).collect(Collectors.toList());
    }
}
