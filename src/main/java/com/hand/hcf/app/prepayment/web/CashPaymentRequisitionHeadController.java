package com.hand.hcf.app.prepayment.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.common.co.*;
import com.hand.hcf.app.core.util.DateUtil;
import com.hand.hcf.app.core.util.PageUtil;
import com.hand.hcf.app.core.util.PaginationUtil;
import com.hand.hcf.app.core.util.TypeConversionUtils;
import com.hand.hcf.app.prepayment.domain.CashPaymentRequisitionHead;
import com.hand.hcf.app.prepayment.domain.enumeration.Constants;
import com.hand.hcf.app.prepayment.externalApi.PrepaymentHcfOrganizationInterface;
import com.hand.hcf.app.prepayment.service.CashPaymentRequisitionHeadService;
import com.hand.hcf.app.prepayment.utils.StringUtil;
import com.hand.hcf.app.prepayment.web.adapter.CashPaymentRequisitionHeaderAdapter;
import com.hand.hcf.app.prepayment.web.dto.*;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Created by cbc on 2017/10/26.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/cash/prepayment/requisitionHead")
@Api(tags = "预付款单头")
public class CashPaymentRequisitionHeadController {

    private final CashPaymentRequisitionHeadService cashPaymentRequisitionHeadService;
    @Autowired
    private PrepaymentHcfOrganizationInterface hcfOrganizationInterface;
    @Autowired
    private CashPaymentRequisitionHeaderAdapter cashPaymentRequisitionHeaderAdapter;

    /**@apiDefine PrepaymentService 预付款模块
     */

    /**
     * @apiDescription 预付款单提交工作流
     * @api {POST} /api/cash/prepayment/requisitionHead/submit 【预付款】预付款单提交
     * @apiGroup PrepaymentService
     *
     * @apiParam {UUID} applicantOid 申请人OID
     * @apiParam {UUID} userOid 用户OID
     * @apiParam {UUID} formOid 表单OID
     * @apiParam {UUID} documentOid 单据OID
     * @apiParam {Integer} documentCategory 单据大类 （如801003)
     * @apiParam {List} countersignApproverOIDs 加签审批人OID
     * @apiParam {String} documentNumber 单据编号
     * @apiParam {String} remark 描述说明
     * @apiParam {Long} companyId 公司ID
     * @apiParam {UUID} unitOid 部门OID
     * @apiParam {String} remark 描述说明
     * @apiParam {Bigdecimal} amount 金额
     * @apiParam {String} currencyCode 币种
     * @apiParam {Long} documentTypeId 单据类型ID
     * @apiSuccessExample {json} 成功返回值:
     * [true]
     *
     */
    @ApiOperation(value = "预付款单提交工作流", notes = "预付款单提交工作流 开发:cbc")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseEntity<Boolean> submit(@ApiParam(value = "工作流文档参考") @RequestBody WorkFlowDocumentRefCO workFlowDocumentRef) {
        return ResponseEntity.ok(cashPaymentRequisitionHeadService.submit(workFlowDocumentRef));
    }
    /**
     * @apiDescription 该接口用于条件查看预付款单头信息
     * @api {GET} /api/cash/prepayment/requisitionHead/query 【预付款】条件查头
     * @apiGroup PrepaymentService
     * @apiParam {Long} [requisitionNumber] 预付款单编号
     * @apiParam {Long} [paymentReqTypeId] 预付款类型id
     * @apiParam {long} [employeeId] 员工ID
     * @apiParam {Date} [requisitionDateFrom] 预付款单日期从
     * @apiParam {Date} [requisitionDateTo] 预付款单日期到
     * @apiParam {Date} [submitDateFrom] 预付款单提交日期从
     * @apiParam {Date} [submitDateTo] 预付款单提交日期到
     * @apiParam {Double} [advancePaymentAmountFrom] 预付款单金额从
     * @apiParam {Double} [advancePaymentAmountTo] 预付款单金额到
     * @apiParam {int} [status] 预付款单状态
     * @apiParam {Long} [checkBy] 复核人id
     * @apiParam {boolean} [ifWorkflow] 是否走工作流
     * @apiParam {int} [page]  page
     * @apiParam {int} [size]  size
     * @apiSuccessExample {json} 成功返回值:
    [
    {
    "id": "974159835799089154",
    "isEnabled": true,
    "isDeleted": false,
    "createdDate": "2018-03-15T13:46:19+08:00",
    "createdBy": 177601,
    "lastUpdatedDate": "2018-03-15T13:46:19+08:00",
    "lastUpdatedBy": 177601,
    "versionNumber": 1,
    "tenantId": "937506219191881730",
    "companyId": "928",
    "unitId": "625575",
    "employeeId": "177601",
    "employeeName": "清浅",
    "requisitionNumber": "SS18031500003",
    "requisitionDate": "2018-03-15T13:48:42+08:00",
    "paymentReqTypeId": "969399818411589634",
    "description": "1",
    "attachmentNum": null,
    "status": 1004,
    "approvalDate": "2018-03-16T14:31:24+08:00",
    "approvedBy": "177691",
    "auditFlag": null,
    "auditDate": null,
    "advancePaymentAmount": 222,
    "attachmentOid": null,
    "applicationOid": "6bdfc6b0-ed2c-442f-8743-22cd522c28c5",
    "formOid": "",
    "unitOid": "41b7be80-04b8-4527-9073-e2cf95c9b914",
    "empOid": "6bdfc6b0-ed2c-442f-8743-22cd522c28c5",
    "documentOid": "6e86b4e8-726c-4d27-ac35-591ec71325f0",
    "documentType": 801003,
    "approvalRemark": null,
    "ifWorkflow": false,
    "submitDate": "2018-03-16T11:26:37+08:00",
    "checkBy": "177691",
    "createByName": "清浅",
    "typeName": "预付款单不走工作流"
    }
    ]
     */
    /**
     * 预付款单页面查询
     *
     * @param requisitionNumber
     * @param paymentReqTypeId
     * @param employeeId
     * @param status
     * @param pageable
     * @param editor 默认为false，true时可以查询编辑中的数据
     * @return
     * @throws URISyntaxException
     */
    @ApiOperation(value = "获取预付款申请单", notes = "获取预付款申请单 开发:cbc")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    @GetMapping("/query")
    public ResponseEntity getCashPaymentRequisition(
            @ApiParam(value = "预付款单编号") @RequestParam(required = false) String requisitionNumber,
            @ApiParam(value = "账套id")  @RequestParam(required = false) Long setOfBooksId,
            @ApiParam(value = "公司id")  @RequestParam(required = false) Long companyId,
            @ApiParam(value = "部门id")  @RequestParam(required = false) Long unitId,
            @ApiParam(value = "预付款类型id") @RequestParam(required = false) Long paymentReqTypeId,
            @ApiParam(value = "员工ID") @RequestParam(required = false) Long employeeId,
            @ApiParam(value = "预付款单日期从") @RequestParam(required = false) String requisitionDateFrom,
            @ApiParam(value = "预付款单日期到") @RequestParam(required = false) String requisitionDateTo,
            @ApiParam(value = "预付款单提交日期从") @RequestParam(required = false) String submitDateFrom,
            @ApiParam(value = "预付款单提交日期到") @RequestParam(required = false) String submitDateTo,
            @ApiParam(value = "预付款单金额从") @RequestParam(required = false) Double advancePaymentAmountFrom,
            @ApiParam(value = "预付款单金额到") @RequestParam(required = false) Double advancePaymentAmountTo,
            @ApiParam(value = "预付款单状态") @RequestParam(required = false) String status,
            @ApiParam(value = "是否走工作流") @RequestParam(required = false) Boolean ifWorkflow,
            @ApiParam(value = "复核人id") @RequestParam(required = false) Long checkBy,
            @ApiParam(value = "描述") @RequestParam(required = false) String description,
            @ApiParam(value = "非编制金额从") @RequestParam(required = false) Double noWritedAmountFrom,
            @ApiParam(value = "非编制金额到") @RequestParam(required = false) Double noWritedAmountTo,
            @ApiParam(value = "编辑者") @RequestParam(required = false,defaultValue = "false") Boolean editor,
            @ApiIgnore Pageable pageable
    ) throws URISyntaxException {
        Page page = PageUtil.getPage(pageable);
        List<CashPaymentRequisitionHead> result = cashPaymentRequisitionHeadService.getCashPaymentRequisition(
                requisitionNumber,
                null,
                null,
                null,
                paymentReqTypeId,
                employeeId,
                requisitionDateFrom == null ? null : TypeConversionUtils.getStartTimeForDayYYMMDD(requisitionDateFrom),
                requisitionDateTo == null ? null : TypeConversionUtils.getEndTimeForDayYYMMDD(requisitionDateTo),
                submitDateFrom == null ? null : TypeConversionUtils.getStartTimeForDayYYMMDD(submitDateFrom),
                submitDateTo == null ? null : TypeConversionUtils.getEndTimeForDayYYMMDD(submitDateTo),
                advancePaymentAmountFrom,
                advancePaymentAmountTo,
                status,
                ifWorkflow,
                checkBy,
                description,
                noWritedAmountFrom,
                noWritedAmountTo,
                editor,
                false,
                page
        );
        HttpHeaders headers = PageUtil.generateHttpHeaders(page, "/api/cash/prepayment/requisitionHead/query");
        return Optional
                .ofNullable(result)
                .map(u -> new ResponseEntity<>(
                        result,
                        headers,
                        HttpStatus.OK
                )).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * @apiDescription 该接口用于条件查看预付款单头信息
     * @api {GET} /api/cash/prepayment/requisitionHead/query 【预付款】条件查头
     * @apiGroup PrepaymentService
     * @apiParam {Long} [requisitionNumber] 预付款单编号
     * @apiParam {Long} [paymentReqTypeId] 预付款类型id
     * @apiParam {long} [employeeId] 员工ID
     * @apiParam {Date} [requisitionDateFrom] 预付款单日期从
     * @apiParam {Date} [requisitionDateTo] 预付款单日期到
     * @apiParam {Date} [submitDateFrom] 预付款单提交日期从
     * @apiParam {Date} [submitDateTo] 预付款单提交日期到
     * @apiParam {Double} [advancePaymentAmountFrom] 预付款单金额从
     * @apiParam {Double} [advancePaymentAmountTo] 预付款单金额到
     * @apiParam {int} [status] 预付款单状态
     * @apiParam {Long} [checkBy] 复核人id
     * @apiParam {boolean} [ifWorkflow] 是否走工作流
     * @apiParam {int} [page]  page
     * @apiParam {int} [size]  size
     * @apiSuccessExample {json} 成功返回值:
    [
    {
    "id": "974159835799089154",
    "isEnabled": true,
    "isDeleted": false,
    "createdDate": "2018-03-15T13:46:19+08:00",
    "createdBy": 177601,
    "lastUpdatedDate": "2018-03-15T13:46:19+08:00",
    "lastUpdatedBy": 177601,
    "versionNumber": 1,
    "tenantId": "937506219191881730",
    "companyId": "928",
    "unitId": "625575",
    "employeeId": "177601",
    "employeeName": "清浅",
    "requisitionNumber": "SS18031500003",
    "requisitionDate": "2018-03-15T13:48:42+08:00",
    "paymentReqTypeId": "969399818411589634",
    "description": "1",
    "attachmentNum": null,
    "status": 1004,
    "approvalDate": "2018-03-16T14:31:24+08:00",
    "approvedBy": "177691",
    "auditFlag": null,
    "auditDate": null,
    "advancePaymentAmount": 222,
    "attachmentOid": null,
    "applicationOid": "6bdfc6b0-ed2c-442f-8743-22cd522c28c5",
    "formOid": "",
    "unitOid": "41b7be80-04b8-4527-9073-e2cf95c9b914",
    "empOid": "6bdfc6b0-ed2c-442f-8743-22cd522c28c5",
    "documentOid": "6e86b4e8-726c-4d27-ac35-591ec71325f0",
    "documentType": 801003,
    "approvalRemark": null,
    "ifWorkflow": false,
    "submitDate": "2018-03-16T11:26:37+08:00",
    "checkBy": "177691",
    "createByName": "清浅",
    "typeName": "预付款单不走工作流"
    }
    ]
     */
    /**
     * 预付款单页面查询
     *
     * @param requisitionNumber
     * @param paymentReqTypeId
     * @param setOfBooksId
     * @param companyId
     * @param unitId
     * @param employeeId
     * @param status
     * @param pageable
     * @param editor 默认为false，true时可以查询编辑中的数据
     * @return
     * @throws URISyntaxException
     */
    @ApiOperation(value = "查询预付款单数据权限启动状态", notes = "查询预付款单数据权限启动状态 开发:cbc")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    @GetMapping("/query/enable/dataAuth")
    public ResponseEntity getCashPaymentRequisitionEnableDataAuth(
          @ApiParam(value = "预付款单编号")  @RequestParam(required = false) String requisitionNumber,
          @ApiParam(value = "账套id")  @RequestParam(required = false) Long setOfBooksId,
          @ApiParam(value = "公司id")  @RequestParam(required = false) Long companyId,
          @ApiParam(value = "部门id")  @RequestParam(required = false) Long unitId,
          @ApiParam(value = "预付款类型id") @RequestParam(required = false) Long paymentReqTypeId,
          @ApiParam(value = "员工ID") @RequestParam(required = false) Long employeeId,
          @ApiParam(value = "预付款单日期从")  @RequestParam(required = false) String requisitionDateFrom,
          @ApiParam(value = "预付款单日期到")  @RequestParam(required = false) String requisitionDateTo,
          @ApiParam(value = "预付款单提交日期从")  @RequestParam(required = false) String submitDateFrom,
          @ApiParam(value = "预付款单提交日期到")  @RequestParam(required = false) String submitDateTo,
          @ApiParam(value = "预付款单金额从")  @RequestParam(required = false) Double advancePaymentAmountFrom,
          @ApiParam(value = "预付款单金额到") @RequestParam(required = false) Double advancePaymentAmountTo,
          @ApiParam(value = "预付款单状态")  @RequestParam(required = false) String status,
          @ApiParam(value = "是否走工作流")  @RequestParam(required = false) Boolean ifWorkflow,
          @ApiParam(value = "复核人id")  @RequestParam(required = false) Long checkBy,
          @ApiParam(value = "描述")  @RequestParam(required = false) String description,
          @ApiParam(value = "非编制金额从")  @RequestParam(required = false) Double noWritedAmountFrom,
          @ApiParam(value = "非编制金额到")  @RequestParam(required = false) Double noWritedAmountTo,
          @ApiParam(value = "编辑者")  @RequestParam(required = false,defaultValue = "false") Boolean editor,
          @ApiIgnore Pageable pageable
    ) throws URISyntaxException {
        Page page = PageUtil.getPage(pageable);
        List<CashPaymentRequisitionHead> result = cashPaymentRequisitionHeadService.getCashPaymentRequisition(
                requisitionNumber,
                setOfBooksId,
                companyId,
                unitId,
                paymentReqTypeId,
                employeeId,
                requisitionDateFrom == null ? null : TypeConversionUtils.getStartTimeForDayYYMMDD(requisitionDateFrom),
                requisitionDateTo == null ? null : TypeConversionUtils.getEndTimeForDayYYMMDD(requisitionDateTo),
                submitDateFrom == null ? null : TypeConversionUtils.getStartTimeForDayYYMMDD(submitDateFrom),
                submitDateTo == null ? null : TypeConversionUtils.getEndTimeForDayYYMMDD(submitDateTo),
                advancePaymentAmountFrom,
                advancePaymentAmountTo,
                status,
                ifWorkflow,
                checkBy,
                description,
                noWritedAmountFrom,
                noWritedAmountTo,
                editor,
                true,
                page
        );
        HttpHeaders headers = PageUtil.generateHttpHeaders(page, "/api/cash/prepayment/requisitionHead/query/enable/dataAuth");
        return Optional
                .ofNullable(result)
                .map(u -> new ResponseEntity<>(
                        result,
                        headers,
                        HttpStatus.OK
                )).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @ApiOperation(value = "使用输入获取预付款单申请", notes = "使用输入获取预付款单申请 开发:cbc")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    @GetMapping("/query/by/input")
    public ResponseEntity getCashPaymentRequisitionByInput(
            @ApiParam(value = "预付款单编号") @RequestParam(required = false) String requisitionNumber,
            @ApiParam(value = "预付款类型id") @RequestParam(required = false) Long paymentReqTypeId,
            @ApiParam(value = "员工ID") @RequestParam(required = false) String empInfo,
            @ApiParam(value = "预付款单日期从")  @RequestParam(required = false) Date requisitionDateFrom,
            @ApiParam(value = "预付款单日期到")  @RequestParam(required = false) Date requisitionDateTo,
            @ApiParam(value = "预付款单金额从")  @RequestParam(required = false) Double advancePaymentAmountFrom,
            @ApiParam(value = "预付款单金额到")  @RequestParam(required = false) Double advancePaymentAmountTo,
            @ApiParam(value = "预付款单状态") @RequestParam(required = false) String status,
            @ApiIgnore Pageable pageable
    ) throws URISyntaxException {
        Page page = PageUtil.getPage(pageable);
        List<CashPaymentRequisitionHeaderCO> result = cashPaymentRequisitionHeadService.getCashPaymentRequisitionByInput(
                requisitionNumber,
                paymentReqTypeId,
                empInfo,
                requisitionDateFrom == null ? null : ZonedDateTime.ofInstant(requisitionDateFrom.toInstant(), ZoneId.systemDefault()),
                requisitionDateTo == null ? null : ZonedDateTime.ofInstant(requisitionDateTo.toInstant(), ZoneId.systemDefault()),
                advancePaymentAmountTo,
                advancePaymentAmountFrom,
                status,
                page
        );
        HttpHeaders headers = PageUtil.generateHttpHeaders(page, "/api/cash/prepayment/requisitionHead/query/by/input");
        return Optional
                .ofNullable(result)
                .map(u -> new ResponseEntity<>(
                        result,
                        headers,
                        HttpStatus.OK
                )).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * @apiDescription 该接口用于新增或修改预付款单头信息
     * @api {POST} /api/cash/prepayment/requisitionHead 【预付款】新增/修改头
     * @apiGroup PrepaymentService
     * @apiParam {Long} [tenantId] 租户id
     * @apiParam {Long} [companyId] 公司id
     * @apiParam {Long} [unitId] 部门id
     * @apiParam {Long} [employeeId] 员工id
     * @apiParam {Double} [writedAmount] 核销金额
     * @apiParam {Srting} [description] 说明
     * @apiParam {int} [status] 状态
     * @apiParam {String} [formOid] 表单oid
     * @apiParam {Long} id 单据id（新增是传""，修改时传具体id）
     * @apiParam {boolean} ifWorkflow 是否走工作流
     * @apiParam {double} advancePaymentAmount 金额
     * @apiParam {Long} paymentReqTypeId 预付款单类型id
     * @apiParamExample {json} 请求参数:
     * {
     * "id":"",
     * "paymentReqTypeId":"969404967351762945",
     * "description":"codetest",
     * "advancePaymentAmount":"0",
     * "formOid":"99d71810-60eb-4161-bb39-b5e933ae3763",
     * "ifWorkflow":true
     * }
     * @apiErrorExample {json} 错误返回值:
     * {
     * "message": "版本号不一致，该数据已在其他客户端更新，请刷新！",
     * "errorCode": "00013"
     * }
     * @apiSuccessExample {json} 成功返回值:
     * {
     * "id": "999554274873806849",
     * "tenantId": "937506219191881730",
     * "companyId": "928",
     * "companyName": "清浅集团",
     * "unitId": "625575",
     * "employeeId": "177601",
     * "employeeName": "清浅",
     * "requisitionNumber": "PREPAYMENT201805240063",
     * "requisitionDate": null,
     * "paymentReqTypeId": "969404967351762945",
     * "typeName": "默认账套预付款不走工作流",
     * "writedAmount": null,
     * "description": "codetest",
     * "attachmentNum": null,
     * "status": 1001,
     * "approvalDate": null,
     * "approvedBy": null,
     * "auditFlag": null,
     * "auditDate": null,
     * "advancePaymentAmount": 0,
     * "versionNumber": 1,
     * "isEnabled": true,
     * "isDeleted": false,
     * "createdDate": "2018-05-24T15:34:45+08:00",
     * "createdBy": 177601,
     * "createByName": "清浅",
     * "lastUpdatedDate": "2018-05-24T15:34:45+08:00",
     * "lastUpdatedBy": 177601,
     * "attachmentOid": null,
     * "attachmentOids": null,
     * "attachments": null,
     * "applicationOid": "6bdfc6b0-ed2c-442f-8743-22cd522c28c5",
     * "formOid": "99d71810-60eb-4161-bb39-b5e933ae3763",
     * "unitOid": "41b7be80-04b8-4527-9073-e2cf95c9b914",
     * "empOid": "6bdfc6b0-ed2c-442f-8743-22cd522c28c5",
     * "documentOid": "fb23b210-c6be-4fd4-87c9-d08acdc0ce92",
     * "documentType": 801003,
     * "ifWorkflow": true,
     * "paymentMethod": "线下",
     * "paymentMethodCode": "OFFLINE_PAYMENT",
     * "ifApplication": false,
     * "submitDate": null,
     * "checkBy": null,
     * "unitName": "预算部",
     * "stringSubmitDate": null
     * }
     */
    @PostMapping
    @ApiOperation(value = "保存预付款单请求信息", notes = "保存预付款单请求信息 开发:cbc")
    public ResponseEntity<CashPaymentRequisitionHeaderCO> saveCashPaymentRequisition(@ApiParam(value = "预付款单请求信息") @RequestBody @Valid CashPaymentRequisitionHeaderCO params) {
        return ResponseEntity.ok(cashPaymentRequisitionHeadService.saveCashPaymentRequisition(params));
    }


    /**
     *  保存预付款单头信息
     * @param cashPaymentRequisitionHead
     * @return
     @PostMapping public ResponseEntity saveCashPaymentRequisitionHead(@RequestBody CashPaymentRequisitionHead cashPaymentRequisitionHead){
     CashPaymentRequisitionHead rsult = cashPaymentRequisitionHeadService.saveCashPaymentRequisitionHead(cashPaymentRequisitionHead);
     return ResponseEntity.ok(rsult);
     }*/

    /**
     * 根据头 id 查询头行信息
     *
     * @param headId
     * @param pageable
     * @return
     * @throws URISyntaxException
     */
    @GetMapping("/{headId}")
    @ApiOperation(value = "根据头id查询头行信息", notes = "根据头id查询头行信息 开发:cbc")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    public ResponseEntity getCashPaymentRequisitionByHeadId(@PathVariable Long headId,@ApiIgnore Pageable pageable) throws URISyntaxException {
        Page page = PageUtil.getPage(pageable);
        CashPaymentParamCO result = cashPaymentRequisitionHeadService.getCashPaymentRequisitionByHeadId(headId, page);
        HttpHeaders headers = PageUtil.generateHttpHeaders(page, "/api/cash/prepayment/requisitionHead/" + headId);
        return new ResponseEntity(result, headers, HttpStatus.OK);
    }


    /**
     * 修改 预付款单头
     *
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @ApiOperation(value = "修改预付款单头", notes = "修改预付款单头 开发:cbc")
    public ResponseEntity updateCashPaymentRequisitionHead(@ApiParam(value = "预付款单头") @RequestBody CashPaymentRequisitionHead cashPaymentRequisitionHead) throws URISyntaxException {
        CashPaymentRequisitionHead result = cashPaymentRequisitionHeadService.updateCashPaymentRequisitionHead(cashPaymentRequisitionHead);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/toPayment")
    @ApiOperation(value = "预付款单交付工作流", notes = "预付款单交付工作流 开发:cbc")
    public ResponseEntity commit(@ApiParam(value = "预付款单参数") @RequestBody @Valid CashPaymentParamCO param) {
        cashPaymentRequisitionHeadService.commit(param);
        return ResponseEntity.ok().build();
    }

  /*  @DeleteMapping("/delete/head/{id}")
    public ResponseEntity deleteHead(@RequestParam Long id){
        cashPaymentRequisitionHeadService.deleteHead(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/line/{id}")
    public ResponseEntity deleteLine(@RequestParam Long id){
        cashPaymentRequisitionHeadService.deleteline(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/insert/line")
    public ResponseEntity insertLine(@RequestBody CashPaymentRequisitionLine line){
        cashPaymentRequisitionHeadService.insertLine(line);
        return ResponseEntity.ok().build();
    }*/

    /**
     * @apiDescription 该接口用于新增或修改预付款单行信息
     * @api {POST} /api/cash/prepayment/requisitionHead/insertOrUpdateLine 【预付款】新增/修改行
     * @apiGroup PrepaymentService
     * @apiParam {long} paymentRequisitionHeaderId 预付款头id
     * @apiParam {string} [refDocumentCode] 关联申请单code
     * @apiParam {long} companyId 公司id
     * @apiParam {long} unitId 部门id
     * @apiParam {long} employeeId 员工id
     * @apiParam {String} partnerCategory 收款方大类
     * @apiParam {long} partnerId 收款方id
     * @apiParam {String} partnerCode 收款方code
     * @apiParam {String} accountName 收款方银行账号名称
     * @apiParam {String} accountNumber 收款方银行账户账号
     * @apiParam {String} bankBranchCode 收款方银行分行代码
     * @apiParam {Stirng}  bankBranchName 收款方银行分行名称
     * @apiParam {String} paymentMethodCategory 付款方式大类
     * @apiParam {long} cshTransactionClassId 现金事务分类id
     * @apiParam {Double} amount 行金额
     * @apiParam {String} currency 币种
     * @apiParam {String} [description] 备注
     * @apiParam {long} [contractId] 合同id
     * @apiParam {String} [contractNumber] 合同编号
     * @apiParam {String} [contractLineNumber] 合同行号
     * @apiParam {long} [contractLineId] 合同行id
     * @apiParamExample {json} 请求参数:
     * [{
     * "id":"",
     * "paymentRequisitionHeaderId":"989045710791380994",
     * "refDocumentCode":"TA00055006",
     * "companyId":"928",
     * "unitId":"625575",
     * "employeeId":"177601",
     * "partnerCategory":"EMPLOYEE",
     * "partnerId":"122158",
     * "partnerCode":"23333333333",
     * "accountName":"333333",
     * "accountNumber":"33333",
     * "bankBranchCode":"bankCode",
     * "bankBranchName":"bankName",
     * "requisitionPaymentDate":"",
     * "paymentMethodCategory":"ONLINE_PAYMENT",
     * "cshTransactionClassId":"987170657266958337",
     * "amount":"101",
     * "currency":"CNY",
     * "description":"liu_test0000011111_行描述",
     * "contractId":"",
     * "contractNumber":"",
     * "contractLineNumber":"",
     * "contractLineId":""
     * }]
     * @apiSuccessExample {json} 成功返回值:
     * [
     * {
     * "id": "999569553611788289",
     * "paymentRequisitionHeaderId": "989045710791380994",
     * "refDocumentOid": null,
     * "refDocumentId": null,
     * "refDocumentCode": "TA00055006",
     * "refDocumentRemark": null,
     * "tenantId": "937506219191881730",
     * "companyId": "928",
     * "companyName": "清浅集团",
     * "partnerCategory": "EMPLOYEE",
     * "partnerId": "122158",
     * "partnerCode": "23333333333",
     * "accountName": "333333",
     * "accountNumber": "33333",
     * "bankBranchCode": "bankCode",
     * "bankBranchName": "bankName",
     * "requisitionPaymentDate": null,
     * "paymentMethodCategory": "ONLINE_PAYMENT",
     * "paymentMethodName": "线上",
     * "cshTransactionClassId": "987170657266958337",
     * "cashFlowId": null,
     * "cashFlowCode": null,
     * "amount": 101,
     * "currency": "CNY",
     * "exchangeRate": 1,
     * "functionAmount": 101,
     * "description": "liu_test0000011111_行描述",
     * "contractId": null,
     * "contractNumber": "",
     * "contractLineNumber": "",
     * "versionNumber": 1,
     * "partnerName": null,
     * "contractLineId": null,
     * "dueDate": null,
     * "paymentReqTypeId": "969399702304866306",
     * "typeName": "预付款单走工作流",
     * "cshTransactionClassName": "14984测试预付款现金事务类型",
     * "isEnabled": true,
     * "isDeleted": false,
     * "createdBy": "177601",
     * "createdDate": "2018-05-24T16:35:27.34+08:00"
     * }
     * ]
     */
    @PostMapping("/insertOrUpdateLine")
    @ApiOperation(value = "插入或更新行", notes = "插入或更新行 开发:cbc")
    public ResponseEntity<List<CashPaymentRequisitionLineCO>> insertOrUpdateLine(@ApiParam(value = "预付款单行") @RequestBody @Valid List<CashPaymentRequisitionLineCO> lineDTOS) {
        return ResponseEntity.ok(cashPaymentRequisitionHeadService.insertOrUpdateLine(lineDTOS));
    }
    @ApiOperation(value = "根据行id复制一行数据", notes = "根据行id复制一行数据 开发:毛仕林")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lineId", value = "复制的行id", dataType = "Long")
    })
    @GetMapping("/copyLineByLineId")
    public ResponseEntity copyLineByLineId(@ApiParam(value = "预付款单行") @RequestParam("lineId") Long lineId) {
        return  ResponseEntity.ok(cashPaymentRequisitionHeadService.copyLine(lineId));
    }

    /**
     * @apiDescription 该接口用于根据头id查询行各个币种金额信息
     * @api {GET} /api/cash/prepayment/requisitionHead/getAmountByHeadId 【预付款】头id查行币种金额
     * @apiGroup PrepaymentService
     * @apiParam {Long} headId 预付款单头id
     * @apiSuccessExample {json} 成功返回值:
     * {
     * CNY:3
     * totalFunctionAmount:3
     * }
     */
    @ApiOperation(value = "根据头id查询行各个币种金额信息", notes = "根据头id查询行各个币种金额信息 开发:cbc")
    @GetMapping("/getAmountByHeadId")
    public ResponseEntity<Map<String, BigDecimal>> getAmountByHeadId(@ApiParam(value = "头ID") @RequestParam Long headId) {
        return ResponseEntity.ok(cashPaymentRequisitionHeadService.getAmountGroupByCodeByHeadId(headId));
    }


    /**
     * @apiDescription 该接口用于根据头id删除预付款单头行信息
     * @api {DELETE} /api/cash/prepayment/requisitionHead/deleteHeadAndLineByHeadId 【预付款】头id删除头行
     * @apiGroup PrepaymentService
     * @apiParam {Long} headId 预付款单头id
     * @apiSuccessExample {json} 成功返回值:
     * {true}
     */
    @ApiOperation(value = "根据头id删除单头行信息", notes = "根据头id删除单头行信息 开发:cbc")
    @DeleteMapping("/deleteHeadAndLineByHeadId")
    public ResponseEntity<Boolean> deleteByHeadId(@ApiParam(value = "头ID") @RequestParam Long headId) {
        return ResponseEntity.ok(cashPaymentRequisitionHeadService.deleteHeadAndLineByHeadId(headId));
    }

    /**
     * @apiDescription 该接口用于根据头id删除预付款单头行信息 app无法使用delete方法
     * @api {DELETE} /api/cash/prepayment/requisitionHead/deleteHeadAndLineByHeadId 【预付款】头id删除头行
     * @apiGroup PrepaymentService
     * @apiParam {Long} headId 预付款单头id
     * @apiSuccessExample {json} 成功返回值:
     * {true}
     */
    @ApiOperation(value = "根据头id删除预付款单头行信息 app无法使用delete方法", notes = "根据头id删除预付款单头行信息 app无法使用delete方法 开发:cbc")
    @GetMapping("/deleteHeadAndLineByHeadId")
    public ResponseEntity<Boolean> deleteByHeadIdForApp(@ApiParam(value = "头ID") @RequestParam Long headId) {
        return ResponseEntity.ok(cashPaymentRequisitionHeadService.deleteHeadAndLineByHeadId(headId));
    }

    /**
     * @apiDescription 该接口用于单独删除行
     * @api {DELETE} /api/cash/prepayment/requisitionHead/deleteLineById 【预付款】删除行
     * @apiGroup PrepaymentService
     * @apiParam {Long} lineId 预付款单行id
     * @apiSuccessExample {json} 成功返回值:
     * {true}
     */
    @ApiOperation(value = "独删除行", notes = "独删除行 开发:cbc")
    @DeleteMapping("/deleteLineById")
    public ResponseEntity<Boolean> deleteLineByLineId(@ApiParam(value = "行ID") @RequestParam Long lineId) {
        return ResponseEntity.ok(cashPaymentRequisitionHeadService.deleteLine(lineId));
    }
    /**
     * @apiDescription 该接口用于App单独删除行,App不支持DELETE方法
     * @api {DELETE} /api/cash/prepayment/requisitionHead/deleteLineById 【预付款】删除行
     * @apiGroup PrepaymentService
     * @apiParam {Long} lineId 预付款单行id
     * @apiSuccessExample {json} 成功返回值:
     * {true}
     */
    @GetMapping("/deleteLineById")
    @ApiOperation(value = "App单独删除行,App不支持DELETE方法", notes = "App单独删除行,App不支持DELETE方法 开发:cbc")
    public ResponseEntity<Boolean> appDeleteLineByLineId(@ApiParam(value = "行ID") @RequestParam Long lineId) {
        return ResponseEntity.ok(cashPaymentRequisitionHeadService.deleteLine(lineId));
    }

    /**
     * @apiDescription 该接口用于查询收款方信息
     * @api {GET} /api/cash/prepayment/requisitionHead/getReceivablesByName 【预付款】查询收款方
     * @apiGroup PrepaymentService
     * @apiParam {String} name 收款方名称
     * @apiParam {String} code 收款方代码
     * @apiParam {int} empFlag 收款方类别 ： 1001-员工，1002-供应商，1003-员工和供应商
     * @apiSuccessExample {json} 成功返回值:
     * [
     * {
     * "id": "989079047211982848",
     * "code": "002",
     * "name": "汉得",
     * "job": null,
     * "department": null,
     * "bankInfos": [
     * {
     * "number": "00",
     * "bankCode": "102100017005",
     * "bankName": "中国工商银行股份有限公司北京荣华中路支行",
     * "primary": true,
     * "bankNumberName": "00"
     * },
     * {
     * "number": "1233211234567",
     * "bankCode": "102100001580",
     * "bankName": "中国工商银行股份有限公司北京哈德门支行",
     * "primary": false,
     * "bankNumberName": "XXX"
     * },
     * {
     * "number": "678",
     * "bankCode": "102100020500",
     * "bankName": "中国工商银行股份有限公司北京中海凯旋支行",
     * "primary": false,
     * "bankNumberName": "435"
     * },
     * {
     * "number": "62012345678",
     * "bankCode": "1.0379705061",
     * "bankName": "中国农业银行渭南三贤路分理处",
     * "primary": false,
     * "bankNumberName": "32"
     * }
     * ],
     * "isEmp": false,
     * "sign": "989079047211982848_false"
     * },
     * {
     * "id": "989335447648555008",
     * "code": "0004",
     * "name": "融晶",
     * "job": null,
     * "department": null,
     * "bankInfos": [
     * {
     * "number": "62134567",
     * "bankCode": "102100014816",
     * "bankName": "中国工商银行股份有限公司北京花园东路支行",
     * "primary": true,
     * "bankNumberName": "XXXXX"
     * }
     * ],
     * "isEmp": false,
     * "sign": "989335447648555008_false"
     * }
     * ]
     */
    /*
     * empFlag:1001 员工
     *         1002 供应商
     *         1003 员工和供应商
     * */
    @ApiOperation(value = "根据name和code获取应收款项", notes = "根据name和code获取应收款项 开发:cbc")
    @GetMapping("/getReceivablesByNameAndCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    public ResponseEntity getReceivablesByNameAndCode(
            @ApiParam(value = "收款方名称") @RequestParam(value = "name",required = false) String name,
            @ApiParam(value = "收款方代码") @RequestParam(value = "code",required = false) String code,
            @ApiParam(value = "收款方类别") @RequestParam Integer empFlag,
            @ApiParam(value = "页面标记") @RequestParam Boolean pageFlag,
            @ApiIgnore Pageable pageable
    ) {
        Page page = PageUtil.getPage(pageable);
        Page<ReceivablesDTO> result = cashPaymentRequisitionHeadService.getReceivablesByNameAndCode(name, code, empFlag, pageFlag, page);
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", "" + result.getTotal());
        return new ResponseEntity<>(result.getRecords(), headers, HttpStatus.OK);

    }

    /**
     * @apiDescription 该接口用于查询收款方信息
     * @api {GET} /api/cash/prepayment/requisitionHead/getReceivablesByName 【预付款】查询收款方
     * @apiGroup PrepaymentService
     * @apiParam {String} name 收款方名称
     * @apiParam {int} empFlag 收款方类别 ： 1001-员工，1002-供应商，1003-员工和供应商
     * @apiSuccessExample {json} 成功返回值:
     * [
     * {
     * "id": "989079047211982848",
     * "code": "002",
     * "name": "汉得",
     * "job": null,
     * "department": null,
     * "bankInfos": [
     * {
     * "number": "00",
     * "bankCode": "102100017005",
     * "bankName": "中国工商银行股份有限公司北京荣华中路支行",
     * "primary": true,
     * "bankNumberName": "00"
     * },
     * {
     * "number": "1233211234567",
     * "bankCode": "102100001580",
     * "bankName": "中国工商银行股份有限公司北京哈德门支行",
     * "primary": false,
     * "bankNumberName": "XXX"
     * },
     * {
     * "number": "678",
     * "bankCode": "102100020500",
     * "bankName": "中国工商银行股份有限公司北京中海凯旋支行",
     * "primary": false,
     * "bankNumberName": "435"
     * },
     * {
     * "number": "62012345678",
     * "bankCode": "1.0379705061",
     * "bankName": "中国农业银行渭南三贤路分理处",
     * "primary": false,
     * "bankNumberName": "32"
     * }
     * ],
     * "isEmp": false,
     * "sign": "989079047211982848_false"
     * },
     * {
     * "id": "989335447648555008",
     * "code": "0004",
     * "name": "融晶",
     * "job": null,
     * "department": null,
     * "bankInfos": [
     * {
     * "number": "62134567",
     * "bankCode": "102100014816",
     * "bankName": "中国工商银行股份有限公司北京花园东路支行",
     * "primary": true,
     * "bankNumberName": "XXXXX"
     * }
     * ],
     * "isEmp": false,
     * "sign": "989335447648555008_false"
     * }
     * ]
     */
    /*
     * empFlag:1001 员工
     *         1002 供应商
     *         1003 员工和供应商
     * */
    @ApiOperation(value = "查询收款方信息", notes = "查询收款方信息 开发:cbc")
    @GetMapping("/getReceivablesByName")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    public ResponseEntity getReceivablesByName(
            @ApiParam(value = "收款方名称") @RequestParam String name,
            @ApiParam(value = "收款方类别") @RequestParam Integer empFlag,
            @ApiParam(value = "页面标志") @RequestParam Boolean pageFlag,
            @ApiIgnore Pageable pageable
    ) {
        Page page = PageUtil.getPage(pageable);
        Page<ReceivablesDTO> result = cashPaymentRequisitionHeadService.getReceivablesByName(name, empFlag, pageFlag, page);
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", "" + result.getTotal());
        return new ResponseEntity<>(result.getRecords(), headers, HttpStatus.OK);

    }


    /*根据单据oid查询单据信息*/
    @GetMapping("/getByOid")
    @ApiOperation(value = "根据单据oid查询单据信息", notes = "根据单据oid查询单据信息 开发:cbc")
    public ResponseEntity<CashPaymentRequisitionHeaderCO> selectByOid(@ApiParam(value = "单据oid") @RequestParam @NotNull String oid) {
        return ResponseEntity.ok(cashPaymentRequisitionHeadService.selectByOid(oid));
    }


    @PutMapping("/updateStatusByOid")
    @ApiOperation(value = "根据单据oid更新单据信息", notes = "根据单据oid更新单据信息 开发:cbc")
    public ResponseEntity updateByOid(
            @ApiParam(value = "单据oid") @RequestParam String oid,
            @ApiParam(value = "状态") @RequestParam int status,
            @ApiParam(value = "当前操作人id") @RequestParam Long userId,
            @ApiParam(value = "是否工作流") @RequestParam(value = "isWorkflow", required = false) Boolean isWorkflow
    ) {
        if (isWorkflow == null) {
            isWorkflow = false;
        }
        cashPaymentRequisitionHeadService.updateDocumentStatusByOid(oid, status, userId, isWorkflow);
        return ResponseEntity.ok().build();
    }

    /**
     * @apiDescription 更新预付款单据状态
     * @api {POST} /api/cash/prepayment/requisitionHead/updateStatus 【预付款】更新状态
     * @apiGroup PrepaymentService
     * @apiParam {int} status 状态
     * @apiParam {long} id 单据id
     * @apiParam {long} userId 当前操作人id
     * @apiParamExample {json} 请求参数:
     * {
     * <p>
     * }
     * @apiSuccessExample {json} 成功返回值(errorCode=0000):
     * {
     * message:""
     * errorCode:"0000"
     * bizErrorCode:""
     * }
     */
    /*预付款单修改状态--前台用*/
    @ApiOperation(value = "更新预付款单据状态", notes = "更新预付款单据状态 开发:cbc")
    @PostMapping("/updateStatus")
    public ResponseEntity updateStatus(
            @ApiParam(value = "状态") @RequestParam int status,
            @ApiParam(value = "单据id") @RequestParam Long id,
            @ApiParam(value = "当前操作人id") @RequestParam Long userId,
            @ApiParam(value = "同意评论") @RequestBody(required = false) Map<String, String> approvalRemark
    ) {
        cashPaymentRequisitionHeadService.updateStatus(status, id, approvalRemark, userId, false);
        return ResponseEntity.ok().build();
    }


    /**
     * @apiDescription 该接口用于根据头获取行信息, 分页
     * @api {get} /api/cash/prepayment/requisitionHead/getLineByHeadId 【预付款】头id获取行信息
     * @apiGroup PrepaymentService
     * @apiParam {long} headId 头id
     * @apiParam {int} [page] page
     * @apiParam {int} [size] size
     * @apiSuccessExample {json} 成功返回值
     * [
     * {
     * "id": "999569158114086914",
     * "paymentRequisitionHeaderId": "999569057459179522",
     * "refDocumentOid": "6bdfc6b0-ed2c-442f-8743-22cd522c28c5",
     * "refDocumentId": "60692",
     * "refDocumentCode": "EA00060692",
     * "refDocumentRemark": "测试",
     * "tenantId": "937506219191881730",
     * "companyId": "928",
     * "companyName": "清浅集团",
     * "partnerCategory": "EMPLOYEE",
     * "partnerId": "177601",
     * "partnerCode": "GH0001",
     * "accountName": "21212231321312312312",
     * "accountNumber": "1234123412341234123",
     * "bankBranchCode": "",
     * "bankBranchName": "",
     * "requisitionPaymentDate": "2018-05-24T16:37:40+08:00",
     * "paymentMethodCategory": "OFFLINE_PAYMENT",
     * "paymentMethodName": "线下",
     * "cshTransactionClassId": "987170657266958337",
     * "cashFlowId": null,
     * "cashFlowCode": null,
     * "amount": 3,
     * "currency": "CNY",
     * "exchangeRate": 1,
     * "functionAmount": 3,
     * "description": "333",
     * "contractId": "981065690934300673",
     * "contractNumber": "CON20180400002",
     * "contractLineNumber": "1",
     * "versionNumber": 1,
     * "partnerName": "清浅",
     * "contractLineId": "981065728586567681",
     * "dueDate": "2018-04-01",
     * "paymentReqTypeId": "971319709767041026",
     * "typeName": "14984测试预付款单",
     * "cshTransactionClassName": "14984测试预付款现金事务类型",
     * "isEnabled": true,
     * "isDeleted": false,
     * "createdBy": "177601",
     * "createdDate": "2018-05-24T16:33:53+08:00"
     * }
     * ]
     */
    @ApiOperation(value = "根据头获取行信息, 分页", notes = "根据头获取行信息, 分页 开发:cbc")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    @GetMapping("/getLineByHeadId")
    public ResponseEntity<List<CashPaymentRequisitionLineCO>> selectLineByHeadId(@ApiParam(value = "头ID") @RequestParam("headId") Long headId, @ApiIgnore Pageable pageable) throws URISyntaxException {
        Page page = PageUtil.getPage(pageable);
        List<CashPaymentRequisitionLineCO> list = cashPaymentRequisitionHeadService.selectLineByHeadId(headId, page);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cash/prepayment/requisitionHead/getLineByHeadId");
        return new ResponseEntity(list, headers, HttpStatus.OK);
    }

    /**
     * @apiDescription 该接口用于根据头获取头信息
     * @api {get} /api/cash/prepayment/requisitionHead/getHeadById 【预付款】头id获取头
     * @apiGroup PrepaymentService
     * @apiParam {long} headId 头id
     * @apiSuccessExample {json} 成功返回值
     * {
     * "id": "999569057459179522",
     * "tenantId": "937506219191881730",
     * "companyId": "928",
     * "companyName": "清浅集团",
     * "unitId": "625575",
     * "employeeId": "177601",
     * "employeeName": "清浅",
     * "requisitionNumber": "PREPAYMENT201805240064",
     * "requisitionDate": "2018-05-24T16:37:21+08:00",
     * "paymentReqTypeId": "971319709767041026",
     * "typeName": "14984测试预付款单",
     * "writedAmount": null,
     * "description": "333",
     * "attachmentNum": null,
     * "status": 1002,
     * "approvalDate": null,
     * "approvedBy": null,
     * "auditFlag": null,
     * "auditDate": null,
     * "advancePaymentAmount": 3,
     * "versionNumber": 1,
     * "isEnabled": true,
     * "isDeleted": false,
     * "createdDate": "2018-05-24T16:33:29+08:00",
     * "createdBy": 177601,
     * "createByName": "清浅",
     * "lastUpdatedDate": "2018-05-24T16:33:29+08:00",
     * "lastUpdatedBy": 177601,
     * "attachmentOid": null,
     * "attachmentOids": null,
     * "attachments": null,
     * "applicationOid": "6bdfc6b0-ed2c-442f-8743-22cd522c28c5",
     * "formOid": "31bf840c-0c4c-4509-baee-b7b09eb74a50",
     * "unitOid": "41b7be80-04b8-4527-9073-e2cf95c9b914",
     * "empOid": "6bdfc6b0-ed2c-442f-8743-22cd522c28c5",
     * "documentOid": "c80ace17-6b95-4d03-80e0-cd80781a75f6",
     * "documentType": 801003,
     * "ifWorkflow": true,
     * "paymentMethod": "线下",
     * "paymentMethodCode": "OFFLINE_PAYMENT",
     * "ifApplication": true,
     * "submitDate": "2018-05-24T16:48:36+08:00",
     * "checkBy": null,
     * "unitName": "预算部",
     * "stringSubmitDate": null
     * }
     */
    @ApiOperation(value = "根据头获取头信息", notes = "根据头获取头信息 开发:cbc")
    @GetMapping("/getHeadById")
    public ResponseEntity<CashPaymentRequisitionHeaderCO> getHeadById(@ApiParam(value = "id") @RequestParam Long id) {
        CashPaymentRequisitionHead head = cashPaymentRequisitionHeadService.selectById(id);
        CashPaymentRequisitionHeaderCO dto = new CashPaymentRequisitionHeaderCO();
        if (head != null) {
            CompanyCO companyCO = hcfOrganizationInterface.getCompanyById(head.getCompanyId());
            if(companyCO != null) {
                head.setCurrency(companyCO.getBaseCurrency());
            }
            dto = cashPaymentRequisitionHeaderAdapter.toDTO(head);
            return ResponseEntity.ok(dto);
        }
        return null;

    }

    /**
     * @apiDescription 根据单据oid批量查询预付款单信息
     * @api {post} /api/cash/prepayment/requisitionHead/getHeadById 【预付款】oid查询
     * @apiGroup PrepaymentService
     * @apiParamExample {json} 请求参数:
     * [2cbc0a46-9bdf-497a-ad56-444e9eab09e0]
     * @apiSuccessExample {json} 成功返回值
     * [{
     * "id": "999569057459179522",
     * "tenantId": "937506219191881730",
     * "companyId": "928",
     * "companyName": "清浅集团",
     * "unitId": "625575",
     * "employeeId": "177601",
     * "employeeName": "清浅",
     * "requisitionNumber": "PREPAYMENT201805240064",
     * "requisitionDate": "2018-05-24T16:37:21+08:00",
     * "paymentReqTypeId": "971319709767041026",
     * "typeName": "14984测试预付款单",
     * "writedAmount": null,
     * "description": "333",
     * "attachmentNum": null,
     * "status": 1002,
     * "approvalDate": null,
     * "approvedBy": null,
     * "auditFlag": null,
     * "auditDate": null,
     * "advancePaymentAmount": 3,
     * "versionNumber": 1,
     * "isEnabled": true,
     * "isDeleted": false,
     * "createdDate": "2018-05-24T16:33:29+08:00",
     * "createdBy": 177601,
     * "createByName": "清浅",
     * "lastUpdatedDate": "2018-05-24T16:33:29+08:00",
     * "lastUpdatedBy": 177601,
     * "attachmentOid": null,
     * "attachmentOids": null,
     * "attachments": null,
     * "applicationOid": "6bdfc6b0-ed2c-442f-8743-22cd522c28c5",
     * "formOid": "31bf840c-0c4c-4509-baee-b7b09eb74a50",
     * "unitOid": "41b7be80-04b8-4527-9073-e2cf95c9b914",
     * "empOid": "6bdfc6b0-ed2c-442f-8743-22cd522c28c5",
     * "documentOid": "c80ace17-6b95-4d03-80e0-cd80781a75f6",
     * "documentType": 801003,
     * "ifWorkflow": true,
     * "paymentMethod": "线下",
     * "paymentMethodCode": "OFFLINE_PAYMENT",
     * "ifApplication": true,
     * "submitDate": "2018-05-24T16:48:36+08:00",
     * "checkBy": null,
     * "unitName": "预算部",
     * "stringSubmitDate": null
     * }]
     */
    @ApiOperation(value = "根据单据oid批量查询预付款单信息", notes = "根据单据oid批量查询预付款单信息 开发:cbc")
    @PostMapping(value = "/getHeadersByDocumentOids")
    public ResponseEntity<List<CashPaymentRequisitionHeaderCO>> selectHeadersByDocumentIds(@ApiParam(value = "oid") @RequestBody CashPrepaymentOIDInfoDTO oids) {
        return ResponseEntity.ok(cashPaymentRequisitionHeadService.selectHeadersByDocumentOids(oids.getOids()));
    }


    /*根据条件查询预付款单信息
     * */
    @ApiOperation(value = "根据条件查询预付款单信息", notes = "根据条件查询预付款单信息 开发:cbc")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    @PostMapping(value = "/get/header/by/input")
    public ResponseEntity<Page<CashPaymentRequisitionHeaderCO>> selectHeadersByInput(@ApiParam(value = "oid") @RequestBody CashPrepaymentQueryDTO cashPrepaymentQueryDTO,
                                                                                     @ApiIgnore Pageable pageable) throws ParseException {
        Page page = PageUtil.getPage(pageable);
        Page<CashPaymentRequisitionHeaderCO> res = cashPaymentRequisitionHeadService.selectHeadersByInput(cashPrepaymentQueryDTO, page);
        return ResponseEntity.ok(res);
    }

    /*推送单据到支付平台
     * */
    @ApiOperation(value = "推送单据到支付平台", notes = "推送单据到支付平台 开发:cbc")
    @GetMapping(value = "/push/by/head/id")
    public ResponseEntity<Boolean> pushToPayment(@ApiParam(value = "头id") @RequestParam Long headId) {
        CashPaymentRequisitionHead head = cashPaymentRequisitionHeadService.selectById(headId);
        Boolean b = cashPaymentRequisitionHeadService.pushToPayment(head);
        return ResponseEntity.ok(b);
    }


    /*
     *根据合同编号查询预付款单头行信息--合同模块需要的接口--分页
     * */
    @ApiOperation(value = "根据合同编号查询预付款单头行信息--合同模块需要的接口--分页", notes = "根据合同编号查询预付款单头行信息--合同模块需要的接口--分页 开发:cbc")
    @GetMapping("/get/by/contract/id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    public ResponseEntity<List<CashPaymentParamCO>> getPrepaymentByContractId(@ApiParam(value = "合同id") @RequestParam(required = false) Long contractId,@ApiIgnore Pageable pageable) throws URISyntaxException {

        Page mybatispage = PageUtil.getPage(pageable);
        List<CashPaymentParamCO> list = cashPaymentRequisitionHeadService.getByContractId(contractId, mybatispage);
        HttpHeaders totalHeader = PageUtil.getTotalHeader(mybatispage);

        return new ResponseEntity(list,totalHeader,HttpStatus.OK);
    }

    /*判断头是否包含行信息
     * */
    @ApiOperation(value = "判断头是否包含行信息", notes = "判断头是否包含行信息 开发:cbc")
    @GetMapping("/if/has/line")
    public ResponseEntity<Boolean> getHeadHasLine(@ApiParam(value = "oid") @RequestParam(value = "oid") String oid) {
        return ResponseEntity.ok(cashPaymentRequisitionHeadService.HeadHasLine(oid));
    }


    /**
     * @apiDescription 该接口用于根据头编号查询头行信息
     * @api {get} /api/cash/prepayment/requisitionHead/get/by/{code}?page=&size= 【预付款】编号查询头行
     * @apiGroup PrepaymentService
     * @apiParam {string} code 预付款单头编号
     * @apiSuccessExample {json} 成功返回值
     * [{
     * head:{
     * },
     * line:[{},{}]
     * }]
     */
    @ApiOperation(value = "根据头编号查询头行信息", notes = "根据头编号查询头行信息 开发:cbc")
    @GetMapping("/get/by/{code}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    public ResponseEntity getCashPaymentRequisitionByHeadCode(@PathVariable String code,@ApiIgnore Pageable pageable) throws URISyntaxException {
        Page page = PageUtil.getPage(pageable);
        CashPaymentParamCO result = cashPaymentRequisitionHeadService.getHeadAndLineByCode(code, page);
        HttpHeaders headers = PageUtil.generateHttpHeaders(page, "/api/cash/prepayment/requisitionHead/get/by/" + code);
        return new ResponseEntity(result, headers, HttpStatus.OK);
    }


    /**
     * @apiDescription 该接口用于预付款财务查询
     * @api {get} /api/cash/prepayment/requisitionHead/get/head/by/query
     * @apiGroup PrepaymentService
     * @apiParam {long} companyId 单据公司
     * @apiParam {string}  requisitionNumber 单据编号
     * @apiParam {long} typeId 预付款类型id
     * @apiParam {int} status 状态
     * @apiParam {long} unitId 单据部门
     * @apiParam {long} applyId 申请人id
     * @apiParam {string} applyDateFrom 申请日期从
     * @apiParam {string} applyDateTo 申请日期到
     * @apiParam {double} amountFrom 金额从
     * @apiParam {double} amountTo 金额到
     * @apiParam {double} noWriteAmountFrom 未核销金额从
     * @apiParam {double} noWriteAmountTo 未核销金额到
     * @apiParam {string} remark 备注
     * @apiParam pageable
     */
    @ApiOperation(value = "预付款财务查询", notes = "预付款财务查询 开发:cbc")
    @GetMapping("/get/head/by/query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    public ResponseEntity<List<CashPaymentRequisitionHead>> getHeadByQuery(
            @ApiParam(value = "单据公司") @RequestParam(value = "companyId", required = false) Long companyId,
            @ApiParam(value = "账套id") @RequestParam(value = "setOfBooksId", required = false) Long setOfBooksId,
            @ApiParam(value = "单据编号") @RequestParam(value = "requisitionNumber", required = false) String requisitionNumber,
            @ApiParam(value = "预付款类型id") @RequestParam(value = "typeId", required = false) Long typeId,
            @ApiParam(value = "状态") @RequestParam(value = "status", required = false) Integer status,
            @ApiParam(value = "单据部门") @RequestParam(value = "unitId", required = false) Long unitId,
            @ApiParam(value = "申请人id") @RequestParam(value = "applyId", required = false) Long applyId,
            @ApiParam(value = "申请日期从") @RequestParam(value = "applyDateFrom", required = false) String applyDateFrom,
            @ApiParam(value = "申请日期到") @RequestParam(value = "applyDateTo", required = false) String applyDateTo,
            @ApiParam(value = "金额从")@RequestParam(value = "amountFrom", required = false) Double amountFrom,
            @ApiParam(value = "金额到") @RequestParam(value = "amountTo", required = false) Double amountTo,
            @ApiParam(value = "未核销金额从") @RequestParam(value = "noWriteAmountFrom", required = false) Double noWriteAmountFrom,
            @ApiParam(value = "未核销金额到") @RequestParam(value = "noWriteAmountTo", required = false) Double noWriteAmountTo,
            @ApiParam(value = "备注") @RequestParam(value = "remark", required = false) String remark,
            @ApiParam(value = "现金事务类型") @RequestParam(value = "reptypeId",required = false) Long reptypeId,
            @ApiIgnore Pageable pageable
    ) throws URISyntaxException {

        Page page = PageUtil.getPage(pageable);

        //string转datetime
        ZonedDateTime dateFrom = null;
        ZonedDateTime dateTo = null;
        if (!org.springframework.util.StringUtils.isEmpty(applyDateFrom)) {
            dateFrom = DateUtil.stringToZonedDateTime(applyDateFrom);
        }
        //结束日期不为空，则+1
        if (!org.springframework.util.StringUtils.isEmpty(applyDateTo)) {
            dateTo = DateUtil.string2ZonedDateTimeAddOne(applyDateTo);
        }
        Page<CashPaymentRequisitionHead> headByQuery = cashPaymentRequisitionHeadService.getHeadByQuery(
                companyId,setOfBooksId, requisitionNumber, typeId, status, unitId, applyId, dateFrom, dateTo, amountFrom, amountTo, noWriteAmountFrom, noWriteAmountTo, remark,false, page
        );

        HttpHeaders headers = PageUtil.generateHttpHeaders(page, "/api/cash/prepayment/requisitionHead/get/head/by/query");
        return new ResponseEntity<>(headByQuery.getRecords(), headers, HttpStatus.OK);
    }

    /**
     * @apiDescription 该接口用于预付款财务查询(数据权限)
     * @api {get} /api/cash/prepayment/requisitionHead/get/head/by/query
     * @apiGroup PrepaymentService
     * @apiParam {long} companyId 单据公司
     * @apiParam {string}  requisitionNumber 单据编号
     * @apiParam {long} typeId 预付款类型id
     * @apiParam {int} status 状态
     * @apiParam {long} unitId 单据部门
     * @apiParam {long} applyId 申请人id
     * @apiParam {string} applyDateFrom 申请日期从
     * @apiParam {string} applyDateTo 申请日期到
     * @apiParam {double} amountFrom 金额从
     * @apiParam {double} amountTo 金额到
     * @apiParam {double} noWriteAmountFrom 未核销金额从
     * @apiParam {double} noWriteAmountTo 未核销金额到
     * @apiParam {string} remark 备注
     * @apiParam pageable
     */
    @ApiOperation(value = "预付款财务查询(数据权限)", notes = "预付款财务查询(数据权限) 开发:cbc")
    @GetMapping("/get/head/by/query/enable/dataAuth")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    public ResponseEntity<List<CashPaymentRequisitionHead>> getHeadByQueryDataAuth(
            @ApiParam(value = "单据公司")  @RequestParam(value = "companyId", required = false) Long companyId,
            @ApiParam(value = "账套id") @RequestParam(value = "setOfBooksId", required = false) Long setOfBooksId,
            @ApiParam(value = "单据编号")  @RequestParam(value = "requisitionNumber", required = false) String requisitionNumber,
            @ApiParam(value = "预付款类型id")  @RequestParam(value = "typeId", required = false) Long typeId,
            @ApiParam(value = "状态") @RequestParam(value = "status", required = false) Integer status,
            @ApiParam(value = "单据部门") @RequestParam(value = "unitId", required = false) Long unitId,
            @ApiParam(value = "申请人id") @RequestParam(value = "applyId", required = false) Long applyId,
            @ApiParam(value = "申请日期从") @RequestParam(value = "applyDateFrom", required = false) String applyDateFrom,
            @ApiParam(value = "申请日期到") @RequestParam(value = "applyDateTo", required = false) String applyDateTo,
            @ApiParam(value = "金额从") @RequestParam(value = "amountFrom", required = false) Double amountFrom,
            @ApiParam(value = "金额到") @RequestParam(value = "amountTo", required = false) Double amountTo,
            @ApiParam(value = "未核销金额从") @RequestParam(value = "noWriteAmountFrom", required = false) Double noWriteAmountFrom,
            @ApiParam(value = "未核销金额到") @RequestParam(value = "noWriteAmountTo", required = false) Double noWriteAmountTo,
            @ApiParam(value = "备注") @RequestParam(value = "remark", required = false) String remark,
            @ApiParam(value = "现金事务类型") @RequestParam(value = "reptypeId",required = false) Long reptypeId,
            @ApiIgnore Pageable pageable
    ) throws URISyntaxException {

        Page page = PageUtil.getPage(pageable);

        //string转datetime
        ZonedDateTime dateFrom = null;
        ZonedDateTime dateTo = null;
        if (!org.springframework.util.StringUtils.isEmpty(applyDateFrom)) {
            dateFrom = DateUtil.stringToZonedDateTime(applyDateFrom);
        }
        //结束日期不为空，则+1
        if (!org.springframework.util.StringUtils.isEmpty(applyDateTo)) {
            dateTo = DateUtil.string2ZonedDateTimeAddOne(applyDateTo);
        }
        Page<CashPaymentRequisitionHead> headByQuery = cashPaymentRequisitionHeadService.getHeadByQuery(
                companyId, setOfBooksId,requisitionNumber, typeId, status, unitId, applyId, dateFrom, dateTo, amountFrom, amountTo, noWriteAmountFrom, noWriteAmountTo, remark,true, page
        );

        HttpHeaders headers = PageUtil.generateHttpHeaders(page, "/api/cash/prepayment/requisitionHead/get/head/by/query");
        return new ResponseEntity<>(headByQuery.getRecords(), headers, HttpStatus.OK);
    }

    /**
     * @api {GET} /api/cash/prepayment/requisitionHead/approvals/filters
     * @apiDescription 获取未审批/已审批的预付款
     * @apiGroup PrepaymentService
     * @apiParam {String} fullName
     * @apiParam {String} businessCode
     * @apiParam {String} beginDate
     * @apiParam {boolean} finished
     * @apiParam {Long} typeId
     * @apiParam {String} endDate
     * @apiParam {Double} amountFrom
     * @apiParam {Double} amountTo
     * @apiParam {String} description
     * @apiParam {String} userOid
     * @apiParam {Pageable} pageable
     *
     * @author mh.z
     * @date 2019/02/19
     * @description 获取未审批/已审批的预付款
     */
    @ApiOperation(value = "获取未审批/已审批的预付款", notes = "获取未审批/已审批的预付款 开发:cbc")
    @RequestMapping(value = "/approvals/filters", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    public ResponseEntity<org.springframework.data.domain.Page<ApprovalEntityDTO>> getMyPrePaymentApprovalList(@ApiParam(value = "全称") @RequestParam(value = "fullName", required = false) String fullName,
                                                                                                               @ApiParam(value = "商务编码") @RequestParam(value = "businessCode", required = false) String businessCode,
                                                                                                               @ApiParam(value = "开始日期") @RequestParam(value = "beginDate", required = false) String beginDate,
                                                                                                               @ApiParam(value = "已完成") @RequestParam(value = "finished", required = false) boolean finished,
                                                                                                               @ApiParam(value = "类型ID") @RequestParam(value = "typeId", required = false) Long typeId,
                                                                                                               @ApiParam(value = "结束日期") @RequestParam(value = "endDate", required = false) String endDate,
                                                                                                               @ApiParam(value = "金额从") @RequestParam(value = "amountFrom", required = false) Double amountFrom,
                                                                                                               @ApiParam(value = "金额到") @RequestParam(value = "amountTo", required = false) Double amountTo,
                                                                                                               @ApiParam(value = "描述") @RequestParam(value = "description", required = false) String description,
                                                                                                               @ApiParam(value = "用户Oid") @RequestParam(value = "userOid", required = false) String userOid,
                                                                                                               @ApiIgnore Pageable pageable) throws URISyntaxException, ParseException {
        // 若参数是空字符串则转换成null
        fullName = StringUtils.isEmpty(fullName) ? null : fullName;
        businessCode = StringUtils.isEmpty(businessCode) ? null : businessCode;
        beginDate = StringUtils.isEmpty(beginDate) ? null : beginDate;
        endDate = StringUtils.isEmpty(endDate) ? null : endDate;
        description = StringUtils.isEmpty(description) ? null : description;
        userOid = StringUtils.isEmpty(userOid) ? null : userOid;

        // 过滤特殊字符
        if (businessCode != null) {
            businessCode = StringUtil.escapeSpecialCharacters(businessCode);
            businessCode = businessCode.toUpperCase();
        }

        if (fullName != null) {
            fullName = StringUtil.escapeSpecialCharacters(fullName);
        }

        // 获取未审批/已审批的预付款
        Page<CashPaymentRequisitionHeaderCO> page = cashPaymentRequisitionHeadService.listApprovalPrepayment(beginDate, endDate, finished, fullName, businessCode,
                typeId, amountFrom, amountTo, description, userOid, pageable);
        List<CashPaymentRequisitionHeaderCO> list = page.getRecords();

        List<ApprovalEntityDTO> result = new ArrayList<ApprovalEntityDTO>();
        // 组装数据（保持和历史结构一致）
        if (list.size() > 0) {
            list.stream().forEach(u -> {
                ApprovalEntityDTO approvalEntityDTO = new ApprovalEntityDTO();
                PrepaymentApprovalDTO prepaymentApprovalDTO = new PrepaymentApprovalDTO();

                // 申请人
                String applicationOid = u.getApplicationOid();
                if (applicationOid != null) {
                    ContactCO userCO = cashPaymentRequisitionHeadService.getUserByOid(applicationOid);
                    if (userCO != null) {
                        //申请人姓名
                        prepaymentApprovalDTO.setApplicantName(userCO.getFullName());
                    }
                    //申请人oid
                    prepaymentApprovalDTO.setApplicantOid(applicationOid);
                    //申请人
                    prepaymentApprovalDTO.setApplicantCode(userCO.getEmployeeCode());
                }

                //提交时间
                prepaymentApprovalDTO.setSubmittedDate(u.getCreatedDate());
                //币种暂时默认处理为CNY
                prepaymentApprovalDTO.setCurrencyCode("CNY");

                // 表单
                /*String formOid = u.getFormOid();
                if (formOid != null) {
                    ApprovalFormCO approvalFormCO = cashPaymentRequisitionHeadService.getApprovalFormByOid(formOid);
                    if (approvalFormCO != null) {
                        prepaymentApprovalDTO.setFormName(approvalFormCO.getFormName()); //设置表单名称
                    }
                    prepaymentApprovalDTO.setFormOid(formOid); //设置表单oid
                    prepaymentApprovalDTO.setFormType(String.valueOf(Constants.PREPAYMENT_DOCUMENT_TYPE)); //设置单据类型
                }*/

                //设置单据状态--和预算日记账的一样
                prepaymentApprovalDTO.setStatus(u.getStatus());
                //设置单据code
                prepaymentApprovalDTO.setPrepaymentCode(u.getRequisitionNumber());
                //设置金额
                prepaymentApprovalDTO.setTotalAmount(u.getAdvancePaymentAmount().doubleValue());
                //设置预付款单类型 id和预付款单类型名称
                prepaymentApprovalDTO.setPaymentReqTypeId(u.getPaymentReqTypeId());
                prepaymentApprovalDTO.setTypeName(u.getTypeName());
                prepaymentApprovalDTO.setDescription(u.getDescription());
                prepaymentApprovalDTO.setStringSubmitDate(u.getStringSubmitDate());
                //设置单据id
                prepaymentApprovalDTO.setId(u.getId());
                //设置单据oid
                approvalEntityDTO.setEntityOid(UUID.fromString(u.getDocumentOid()));
                approvalEntityDTO.setEntityType(Constants.PREPAYMENT_DOCUMENT_TYPE);
                approvalEntityDTO.setPrepaymentApprovalView(prepaymentApprovalDTO);
                result.add(approvalEntityDTO);
            });
        }

        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cash/prepayment/requisitionHead/approvals/filters");
        ResponseEntity responseEntity = new ResponseEntity(result, httpHeaders, HttpStatus.OK);
        return responseEntity;
    }

    /**
     * @api {GET} /api/cash/prepayment/requisitionHead/query/created 查询已创建预付款单的申请人
     */
    @ApiOperation(value = "查询已创建预付款单的申请人", notes = "查询已创建预付款单的申请人 开发:cbc")
    @GetMapping("/query/created")
    public List<ContactCO> listUsersByCreatedCashPaymentRequisitions(){
        return cashPaymentRequisitionHeadService.listUsersByCreatedCashPaymentRequisitions();
    }


    /**
     * 根据费用申请单头id ，查询从费用申请单详情页面新建的预付款单行
     *
     * @param refDocumentId
     * @param pageable
     * @return
     */
    @ApiOperation(value = "根据费用申请单头id，查询从费用申请单详情页面新建的预付款单行", notes = "分页查询预付款单行信息 开发:韩雪")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "refDocumentId", value = "费用申请单头id", dataType = "Long"),
            @ApiImplicitParam(name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页多少条", dataType = "int"),
    })
    @GetMapping("/query/prepayment/line/by/refDocumentId")
    public ResponseEntity<List<CashPaymentRequisitionLineCO>> pagePrepaymentLineByRefDocumentId(
            @ApiParam(value = "费用申请单头id") @RequestParam(value = "refDocumentId") Long refDocumentId,
            @ApiIgnore Pageable pageable
    ){
        Page page = PageUtil.getPage(pageable);
        Page<CashPaymentRequisitionLineCO> lineCOPage = cashPaymentRequisitionHeadService.pagePrepaymentLineByRefDocumentId(refDocumentId, page);
        HttpHeaders httpHeaders = PageUtil.getTotalHeader(lineCOPage);
        return new ResponseEntity<>(lineCOPage.getRecords(),httpHeaders,HttpStatus.OK);
    }


    /**
     * 根据申请单头ID获取关联的预付款单头
     *
     * @param applicationHeadId
     * @return
     */
    @ApiOperation(value = "根据申请单头ID获取关联的预付款单头", notes = "根据申请单头ID获取关联的预付款单头 开发:韩雪")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applicationHeadId", value = "申请单头ID", dataType = "Long")
    })
    @GetMapping("/get/by/applicationHeaderId")
    public CashPaymentRequisitionHeaderCO getCashPaymentRequisitionHeaderByApplicationHeaderId(
            @ApiParam(value = "申请单头ID") @RequestParam(value = "applicationHeadId") Long applicationHeadId){
        CashPaymentRequisitionHead head = cashPaymentRequisitionHeadService.getCashPaymentRequisitionHeaderByApplicationHeaderId(applicationHeadId);

        CashPaymentRequisitionHeaderCO co = new CashPaymentRequisitionHeaderCO();
        if (head != null) {
            CompanyCO companyCO = hcfOrganizationInterface.getCompanyById(head.getCompanyId());
            if(companyCO != null) {
                head.setCurrency(companyCO.getBaseCurrency());
            }
            co = cashPaymentRequisitionHeaderAdapter.toDTO(head);
            return co;
        }
        return null;
    }

    /**
     * 根据申请单头ID获取关联的预付款单头
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据申请单头ID获取关联的预付款单头", notes = "根据申请单头ID获取关联的预付款单头")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "申请单头ID", dataType = "List")
    })
    @PostMapping("/list/by/applicationHeaderIds")
    public List<CashPaymentRequisitionHead> listCashPaymentRequisitionHeaderByApplicationHeaderIds(@ApiParam(value = "申请单头ID") @RequestBody List<Long> ids){
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<CashPaymentRequisitionHead> headList = cashPaymentRequisitionHeadService.selectList(
                new EntityWrapper<CashPaymentRequisitionHead>().in("ref_document_id", ids)
        );
        return headList;
    }
}
