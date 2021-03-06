package com.hand.hcf.app.ant.expenseCategory.service;

import com.hand.hcf.app.ant.expenseCategory.dto.CompanyOrDeptAuthority;
import com.hand.hcf.app.ant.expenseCategory.persistence.CompanyOrDeptAuthorityMapper;
import com.hand.hcf.app.core.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyOrDeptAuthorityService extends BaseService<CompanyOrDeptAuthorityMapper,CompanyOrDeptAuthority> {

    public List<CompanyOrDeptAuthority> batchInsert(List<CompanyOrDeptAuthority> list){
        this.insertBatch(list);
        return list;
    }
}
