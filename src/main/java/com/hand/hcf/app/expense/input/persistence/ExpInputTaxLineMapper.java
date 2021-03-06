package com.hand.hcf.app.expense.input.persistence;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.expense.input.domain.ExpInputTaxLine;
import com.hand.hcf.app.expense.input.dto.ExpInputForReportLineDTO;
import com.hand.hcf.app.expense.input.dto.ExpInputTaxLineDTO;
import com.hand.hcf.app.expense.input.dto.ExpInputTaxSumAmountDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @description
 * @Version: 1.0
 * @author: ShilinMao
 * @date: 2019/2/28 14:43
 */
public interface ExpInputTaxLineMapper extends BaseMapper<ExpInputTaxLine>{

    /**
     * 获取 报账单的行数据 包含两部分-已经存在进项行的，未存在进项行的
     * (当进项税业务单，业务大类为“PART_TRANSFER”按比例转出 和 “ALL_TRANSFER”全额转出时，用这个方法)
     * */
    List<ExpInputForReportLineDTO> listExpInputTaxLine(@Param("ew") Wrapper<ExpInputTaxLine> wrapper,@Param("setOfBooksId") Long setOfBooksId, @Param("headerId") Long headerId, Page page);

    /**
     * 获取 报账单的行数据 包含两部分-已经存在进项行的，未存在进项行的
     * (当进项税业务单，业务大类为“FOR_SALE”视同销售时，用这个方法)
     * */
    List<ExpInputForReportLineDTO> listExpInputTaxLineV2(@Param("ew") Wrapper<ExpInputTaxLine> wrapper,@Param("setOfBooksId") Long setOfBooksId, @Param("headerId") Long headerId, Page page);



    /**
     * 获取 报账单的行金额合计
     * */
    ExpInputTaxSumAmountDTO getSumAmount(@Param("inputTaxHeaderId") Long inputTaxHeaderId);
    /**
     * 获取 报账单的行数据，部分数据需要联表查询
     * */
    List<ExpInputTaxLineDTO> listLineById(@Param("inputTaxHeaderId") Long inputTaxHeaderId,Page page);

    /**
     * 获取 报账单的行数据，部分数据需要联表查询
     * */
    List<ExpInputTaxLineDTO> listLineById(@Param("inputTaxHeaderId") Long inputTaxHeaderId);
    /**
     * 获取 报账单的行数据对应报账单行的可转出金额
     * */
    List<ExpInputForReportLineDTO> checklistExpInputTaxLine(@Param("headerId") Long headerId);

    /**
     * 获取 报账单的行数据对应报账单行的可视同销售金额
     * */
    List<ExpInputForReportLineDTO> checklistExpInputTaxLineV2(@Param("headerId") Long headerId);
}
