package com.hand.hcf.app.ant.expenseCategory.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hand.hcf.app.ant.expenseCategory.dto.CompanyOrDeptAuthority;
import com.hand.hcf.app.ant.expenseCategory.dto.ExpenseCategory;
import com.hand.hcf.app.ant.expenseCategory.persistence.CompanyOrDeptAuthorityMapper;
import com.hand.hcf.app.ant.expenseCategory.persistence.ExpenseCategoryMapper;
import com.hand.hcf.app.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hcf.app.mdata.base.util.OrgInformationUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpenseCategoryService extends BaseService<ExpenseCategoryMapper, ExpenseCategory> {

    @Autowired
    private ExpenseCategoryMapper expenseCategoryMapper;

    @Autowired
    private CompanyOrDeptAuthorityService companyOrDeptAuthorityService;

    @Autowired
    private CompanyOrDeptAuthorityMapper companyOrDeptAuthorityMapper;


    public List<ExpenseCategory> queryPages(String categoryType,Page page){
        return  expenseCategoryMapper.selectPage(page,new EntityWrapper<ExpenseCategory>()
                .eq("category_type",categoryType)
                .eq("set_of_book_id",OrgInformationUtil.getCurrentSetOfBookId())
                .orderBy("last_updated_date")
        );
    }


    public ExpenseCategory insertOrUpdateExpenseCategory (ExpenseCategory expenseCategory){

        expenseCategory.setSetOfBooksId(OrgInformationUtil.getCurrentSetOfBookId());
        expenseCategory.setLastUpdatedBy(OrgInformationUtil.getCurrentUserId());
        expenseCategory.setCreatedBy(OrgInformationUtil.getCurrentUserId());

        if(expenseCategory.getId()!= null){
            // 更新的时候，设置最后更新时间
            expenseCategory.setLastUpdatedDate(ZonedDateTime.now());
        }

        this.insertOrUpdate(expenseCategory);

        //设置权限范围
        CompanyOrDeptAuthority authority = expenseCategory.getCompanyOrDeptAuthority();

        List<CompanyOrDeptAuthority> authorityList = authority.getValues().stream().map(item->{
            CompanyOrDeptAuthority companyOrDeptAuthority = new CompanyOrDeptAuthority();
            companyOrDeptAuthority.setType(authority.getType());
            companyOrDeptAuthority.setCategoryId(expenseCategory.getId());
            companyOrDeptAuthority.setComOrDeptId(item);
            return  companyOrDeptAuthority;
        }).collect(Collectors.toList());

        if(!authorityList.isEmpty()){
            if(expenseCategory.getId()!= null){
                // 更新的时候，先删除关联
                companyOrDeptAuthorityMapper.delete(new EntityWrapper<CompanyOrDeptAuthority>()
                        .eq("category_id",expenseCategory.getId()));
            }
            companyOrDeptAuthorityService.insertBatch(authorityList);
        }

        return expenseCategory;
    }
}