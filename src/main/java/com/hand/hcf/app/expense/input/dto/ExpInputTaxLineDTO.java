package com.hand.hcf.app.expense.input.dto;

import com.hand.hcf.app.expense.input.domain.ExpInputTaxHeader;
import com.hand.hcf.app.expense.input.domain.ExpInputTaxLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description
 * @Version: 1.0
 * @author: ShilinMao
 * @date: 2019/3/1 14:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpInputTaxLineDTO extends ExpInputTaxLine {


    private String documentNumber;

    private String useTypeName;
    /**
     * 费用类型id
     */
    private Long expenseTypeId;

    private String expenseTypeName;

}
