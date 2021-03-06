package com.hand.hcf.app.ant.taxreimburse.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.ant.taxreimburse.domain.ExpBankFlow;
import com.hand.hcf.app.ant.taxreimburse.service.ExpBankFlowService;
import com.hand.hcf.app.ant.taxreimburse.utils.TaxReimburseConstans;
import com.hand.hcf.app.base.util.FileUtil;
import com.hand.hcf.app.core.domain.ExportConfig;
import com.hand.hcf.app.core.handler.ExcelExportHandler;
import com.hand.hcf.app.core.service.ExcelExportService;
import com.hand.hcf.app.core.util.LoginInformationUtil;
import com.hand.hcf.app.core.util.PageUtil;
import com.hand.hcf.app.core.util.TypeConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author xu.chen02@hand-china.com
 * @version 1.0
 * @description: 银行流水controller·
 * @date 2019/5/29 14:56
 */
@RestController
@RequestMapping("/api/exp/bank/flow")
public class ExpBankFlowController {

    @Autowired
    private ExpBankFlowService expBankFlowService;

    @Autowired
    private ExcelExportService excelExportService;


    /**
     * 获取银行流水List的数据-分页查询
     * url-/api/exp/bank/flow/list/query
     *
     * @param companyId
     * @param fundFlowNumber
     * @param bankAccountName
     * @param currencyCode
     * @param payDateFrom
     * @param payDateTo
     * @param flowAmountFrom
     * @param flowAmountTo
     * @param blendStatus
     * @param status
     * @param pageable
     * @return
     */

    @GetMapping("/list/query")
    public ResponseEntity<List<ExpBankFlow>> getExpTaxReportByCon(
            @RequestParam(required = false) String companyId,/*公司*/
            @RequestParam(required = false) String fundFlowNumber,/*资金流水号*/
            @RequestParam(required = false) String bankAccountName,/*对方名称*/
            @RequestParam(required = false) String currencyCode,/*币种*/
            @RequestParam(required = false) String payDateFrom,/*支付日期从*/
            @RequestParam(required = false) String payDateTo,/*支付日期至*/
            @RequestParam(required = false) BigDecimal flowAmountFrom,/*流水金额从*/
            @RequestParam(required = false) BigDecimal flowAmountTo,/*流水金额至*/
            @RequestParam(required = false) Boolean blendStatus,/*勾兑状态*/
            @RequestParam(required = false) Boolean status,/*报账状态*/
            @ApiIgnore Pageable pageable) {

        Page page = PageUtil.getPage(pageable);
        ZonedDateTime payDateFroms = TypeConversionUtils.getStartTimeForDayYYMMDD(payDateFrom);
        ZonedDateTime payDateTos = TypeConversionUtils.getEndTimeForDayYYMMDD(payDateTo);
        List<ExpBankFlow> expBankFlowList = expBankFlowService.getExpBankFlowByCon(
                companyId,
                fundFlowNumber,
                bankAccountName,
                currencyCode,
                payDateFroms,
                payDateTos,
                flowAmountFrom,
                flowAmountTo,
                blendStatus,
                status,
                page);
        HttpHeaders httpHeaders = PageUtil.getTotalHeader(page);
        return new ResponseEntity<>(expBankFlowList, httpHeaders, HttpStatus.OK);
    }

    /**
     * 导出银行流水数据 url:/api/exp/bank/flow/export
     *
     * @param request
     * @param response
     * @param exportConfig
     * @param companyId
     * @param fundFlowNumber
     * @param bankAccountName
     * @param currencyCode
     * @param payDateFrom
     * @param payDateTo
     * @param flowAmountFrom
     * @param flowAmountTo
     * @param blendStatus
     * @param status
     * @throws IOException
     */
    @RequestMapping("/export")
    public void export(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody ExportConfig exportConfig,
            @RequestParam(required = false) String companyId,/*公司*/
            @RequestParam(required = false) String fundFlowNumber,/*资金流水号*/
            @RequestParam(required = false) String bankAccountName,/*对方名称*/
            @RequestParam(required = false) String currencyCode,/*币种*/
            @RequestParam(required = false) String payDateFrom,/*支付日期从*/
            @RequestParam(required = false) String payDateTo,/*支付日期至*/
            @RequestParam(required = false) BigDecimal flowAmountFrom,/*流水金额从*/
            @RequestParam(required = false) BigDecimal flowAmountTo,/*流水金额至*/
            @RequestParam(required = false) Boolean blendStatus,/*勾兑状态*/
            @RequestParam(required = false) Boolean status/*报账状态*/
    ) throws IOException {
        ZonedDateTime payDateFroms = TypeConversionUtils.getStartTimeForDayYYMMDD(payDateFrom);
        ZonedDateTime payDateTos = TypeConversionUtils.getEndTimeForDayYYMMDD(payDateTo);
        Page<ExpBankFlow> expTaxReportPage = expBankFlowService.getBankFlowByPage(
                companyId,
                fundFlowNumber,
                bankAccountName,
                currencyCode,
                payDateFroms,
                payDateTos,
                flowAmountFrom,
                flowAmountTo,
                blendStatus,
                status,
                new Page<ExpBankFlow>(1, 0)
        );
        String name = exportConfig.getFileName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = "_" + sdf.format(new Date());
        String fileName = name + date;
        exportConfig.setFileName(fileName);
        int total = TypeConversionUtils.parseInt(expTaxReportPage.getTotal());
        int threadNumber = total > 100000 ? 8 : 2;
        excelExportService.exportAndDownloadExcel(exportConfig, new ExcelExportHandler<ExpBankFlow, ExpBankFlow>() {
            @Override
            public int getTotal() {
                return total;
            }

            @Override
            public List<ExpBankFlow> queryDataByPage(Page page) {
                List<ExpBankFlow> list = expBankFlowService.exportExpBankFlow(
                        companyId,
                        fundFlowNumber,
                        bankAccountName,
                        currencyCode,
                        payDateFroms,
                        payDateTos,
                        flowAmountFrom,
                        flowAmountTo,
                        blendStatus,
                        status);
                return list;
            }

            @Override
            public ExpBankFlow toDTO(ExpBankFlow t) {
                return t;
            }

            @Override
            public Class<ExpBankFlow> getEntityClass() {
                return ExpBankFlow.class;
            }
        }, threadNumber, request, response);
    }

    /**
     * 根据Id 批量删除银行流水数据 url:/api/exp/bank/flow/delete
     *
     * @param rowIds
     */
    @DeleteMapping("/delete")
    public void delete(@RequestParam String rowIds) {
        expBankFlowService.deleteBankFlow(rowIds);
    }


    /**
     * 报账单详情页面支付明细信息显示
     *
     * @param reimburseHeaderId
     * @param pageable
     * @return
     */
    @GetMapping("/list/by/headId")
    public ResponseEntity<List<ExpBankFlow>> getTaxReportDetail(@RequestParam String reimburseHeaderId, Pageable pageable) {
        Page page = PageUtil.getPage(pageable);
        List<ExpBankFlow> expBankFlowList = expBankFlowService.getBankFlowDetailList(reimburseHeaderId, page);
        HttpHeaders httpHeaders = PageUtil.getTotalHeader(page);
        return new ResponseEntity<>(expBankFlowList, httpHeaders, HttpStatus.OK);
    }

    /**
     * 下载税金申报数据导入模板-url:/api/exp/bank/flow/download/template
     *
     * @return
     */
    @GetMapping("/download/template")
    public ResponseEntity<byte[]> exportBankFlowTemplate() {
        byte[] bytes = FileUtil.getFileBinaryForDownload(FileUtil.getTemplatePath(TaxReimburseConstans.BANK_FLOW_IMPORT_TEMPLATE_PATH, LoginInformationUtil.getCurrentLanguage()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    /**
     * 批量更新银行流水支付明细信息 url:api/exp/bank/flow/update/bank/flow/data (保存功能）
     * @param expBankFlowList
     * @return
     */
    @PostMapping("/update/bank/flow/data")
    public ResponseEntity<List<ExpBankFlow>>  saveTaxReport(@RequestBody List<ExpBankFlow> expBankFlowList){
        return ResponseEntity.ok(expBankFlowService.saveBankFlow(expBankFlowList));
    }
}
