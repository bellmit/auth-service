package com.hand.hcf.app.expense.input.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description 从报账单取其行数据的DTO
 * @Version: 1.0
 * @author: ShilinMao
 * @date: 2019/3/4 11:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpInputForReportDistDTO {

  private Long id;

  private Long inputTaxLineId;

  private Long expReportDistId;

  private String selectFlag;


  private Long tenantId;

  protected Long setOfBooksId;

  private Long companyId;

  private String companyName;

  private Long departmentId;

  private String departmentName;

  private Long responsibilityCenterId;


  private String  currencyCode;

  private Long rate;

  private BigDecimal baseAmount;

  private BigDecimal baseFunctionAmount;

  /**
   * 报账单分摊行分摊金额
   */
  private BigDecimal distAmount;
  /**
   * 报账单分摊行分摊Function金额
   */
  private BigDecimal distFunctionAmount;
  /**
   * 报账单分摊行分摊税额
   */
  private BigDecimal distTaxAmount;

  /**
   * 报账单分摊行分摊Function税额
   */
  private BigDecimal distTaxFunctionAmount;



}
