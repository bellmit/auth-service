package com.hand.hcf.app.workflow.workflow.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.mdata.base.util.OrgInformationUtil;
import com.hand.hcf.app.workflow.workflow.dto.*;
import com.hand.hcf.app.workflow.workflow.service.ApprovalHistoryService;
import com.hand.hcf.app.workflow.workflow.service.WorkFlowApprovalService;
import com.hand.hcf.app.workflow.workflow.service.WorkFlowDocumentRefService;
import com.hand.hcf.core.util.DateUtil;
import com.hand.hcf.core.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by houyin.zhang@hand-china.com on 2018/12/11.
 * 工作流方法调用 统一入口方法
 */
@RestController
@RequestMapping("/api/workflow")
public class WorkFlowApproveController {

    @Autowired
    private WorkFlowApprovalService workFlowApprovalService;

    @Autowired
    private ApprovalHistoryService approvalHistoryService;

    @Autowired
    private WorkFlowDocumentRefService workFlowDocumentRefService;


    /**
     * @api {post} /pass    审批通过
     * @apiDescription 审批通过
     * @apiName passWorkflow
     * @apiGroup Approval
     * @apiParamExample {json} Request-Example:
     * {"entities":[{"entityOid":"73173029-83c9-4d91-904f-ca241866d4b7","entityType":1001}]}
     * @apiSuccessExample {json} Success-Result
     * {"successNum":1,"failNum":0,"failReason":{}}
     */
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public ResponseEntity passWorkflow(@Valid @RequestBody ApprovalReqDTO approvalReqDTO) {
        ApprovalResDTO approvalResDTO = workFlowApprovalService.passWorkflow(OrgInformationUtil.getCurrentUserOid(), approvalReqDTO,approvalReqDTO.getFormOid());
        return ResponseEntity.ok(approvalResDTO);
    }

    /**
     * @api {post} /rejectWorkflow 审批驳回
     * @apiDescription 审批驳回
     * @apiName rejectWorkflow
     * @apiGroup Approval
     * @apiParamExample {json} Request-Example:
     * {"entities":[{"entityOid":"73173029-83c9-4d91-904f-ca241866d4b7","entityType":1001}]}
     * @apiSuccessExample {json} Success-Result
     * {"successNum":1,"failNum":0,"failReason":{}}
     */
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public ResponseEntity rejectWorkflow(@Valid @RequestBody ApprovalReqDTO approvalReqDTO) {
        ApprovalResDTO approvalResDTO = workFlowApprovalService.rejectWorkflow(OrgInformationUtil.getCurrentUserOid(), approvalReqDTO);
        return ResponseEntity.ok(approvalResDTO);
    }
    /**
     * @api {post} /withdraw    申请人撤回单据
     * @apiDescription 申请人撤回单据
     * @apiName withdrawWorkflow
     * @apiGroup Approval
     * @apiParamExample {json} Request-Example:
     * {"entities":[{"entityOid":"73173029-83c9-4d91-904f-ca241866d4b7","entityType":1001}]}
     * @apiSuccessExample {json} Success-Result
     * {"successNum":1,"failNum":0,"failReason":{}}
     */
    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public ResponseEntity withdrawWorkflow(@Valid @RequestBody ApprovalReqDTO approvalReqDTO) {
        ApprovalResDTO approvalResDTO = workFlowApprovalService.withdrawWorkflow(OrgInformationUtil.getCurrentUserOid(), approvalReqDTO);
        return ResponseEntity.ok(approvalResDTO);
    }


    /**
     * 【仪表盘】-我的单据
     * @param tabNumber
     * @return
     */
    @RequestMapping(value = "/my/document/{tabNumber}", method = RequestMethod.GET)
    public ResponseEntity<List<WorkflowDocumentDTO>> listMyDocument(@PathVariable Integer tabNumber){
        return ResponseEntity.ok(workFlowApprovalService.listMyDocument(tabNumber));
    }


    /**
     * 【仪表盘-】获取当前用户所有待审批的单据
     * @return
     */
    @RequestMapping(value = "/approvals/batchfilters", method = RequestMethod.GET)
    public ResponseEntity getApprovalDashboardDetailDTOList() {
        return ResponseEntity.ok(workFlowApprovalService.getApprovalDashboardDetailDTOList());
    }

    /**
     * @api {get} /api/workflow/approval/history
     * @apiDescription 获取审批历史
     * @apiName listApprovalHistory
     * @apiGroup Approval
     * @apiSuccessExample {json} Success-Result
     * [
     * {
     * "operation": 9001,
     * "operationType": 1000,
     * "lastModifiedDate": "2018-10-24 11:20:47",
     * "employeeId": "et1",
     * "employeeName": "et1",
     * "operationDetail": "支付金额：5",
     * "countersignType": null,
     * "operationRemark": null,
     * "approvalNodeName": null
     * }
     * ]
     *
     * @Author mh.z
     * @Date 2019/01/23
     * @Description 获取审批历史
     *
     * @param entityType 单据类型
     * @param entityOid 单据Oid
     * @return
     */
    @RequestMapping(value = "/approval/history", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WebApprovalHistoryDTO>> listApprovalHistory(@RequestParam("entityType") Integer entityType, @RequestParam("entityOid") UUID entityOid) {
        List<WebApprovalHistoryDTO> list = new ArrayList<WebApprovalHistoryDTO>();

        // 获取审批历史
        List<ApprovalHistoryDTO> approvalHistoryDtoList = approvalHistoryService.listApprovalHistory(entityType, entityOid);
        // 确定审批动作名称
        approvalHistoryService.approvalAction(list, approvalHistoryDtoList, approvalHistoryService);

        return ResponseEntity.ok(list);
    }

    /**
     * @api {get} /api/workflow/document/approvals/filters/{entityType}
     * @apiDescription 获取已审批未审批的单据
     * @apiName pageApprovalDocument
     * @apiGroup Approval
     * @apiParam {String} [documentNumber] 单据编号
     * @apiParam {String} [documentName] 单据名称
     * @apiParam {Long} [documentTypeId] 单据类型id
     * @apiParam {String} [currencyCode] 币种
     * @apiParam {String} [amountFrom] 最小金额
     * @apiParam {Double} [amountTo] 最大金额
     * @apiParam {String} [applicantOid] 申请人oid
     * @apiParam {String} [beginDate] 最小提交日期
     * @apiParam {String} [endDate] 最大提交日期
     * @apiParam {String} [description] 备注
     * @apiParam {boolean} finished true已审批，false未审批
     *
     * @author mh.z
     * @date 2019/03/06
     * @description 获取已审批未审批的单据
     */
    @RequestMapping(value = "/document/approvals/filters/{entityType}", method = RequestMethod.GET)
    public ResponseEntity<List<ApprovalDocumentDTO>> pageApprovalDocument(@PathVariable(value = "entityType", required = true) Integer entityType,
                                                                          @RequestParam(value = "documentNumber", required = false) String documentNumber,
                                                                          @RequestParam(value = "documentName", required = false) String documentName,
                                                                          @RequestParam(value = "documentTypeId", required = false) Long documentTypeId,
                                                                          @RequestParam(value = "currencyCode", required = false) String currencyCode,
                                                                          @RequestParam(value = "amountFrom", required = false) Double amountFrom,
                                                                          @RequestParam(value = "amountTo", required = false) Double amountTo,
                                                                          @RequestParam(value = "applicantOid", required = false) String applicantOidStr,
                                                                          @RequestParam(value = "beginDate", required = false) String beginDateStr,
                                                                          @RequestParam(value = "endDate", required = false) String endDateStr,
                                                                          @RequestParam(value = "description", required = false) String description,
                                                                          @RequestParam(value = "finished", required = true) boolean finished,
                                                                          Pageable pageable) {
        documentNumber = StringUtils.isEmpty(documentNumber) ? null : documentNumber;
        documentName = StringUtils.isEmpty(documentName) ? null : documentName;
        currencyCode = StringUtils.isEmpty(currencyCode) ? null : currencyCode;
        applicantOidStr = StringUtils.isEmpty(applicantOidStr) ? null : applicantOidStr;
        beginDateStr = StringUtils.isEmpty(beginDateStr) ? null : beginDateStr;
        endDateStr = StringUtils.isEmpty(endDateStr) ? null : endDateStr;
        description = StringUtils.isEmpty(description) ? null : description;

        // 最小提交日期
        ZonedDateTime beginDate = null;
        if (StringUtils.isNotEmpty(beginDateStr)) {
            beginDate = DateUtil.stringToZonedDateTime(beginDateStr);
        }

        // 最大提交日期
        ZonedDateTime endDate = null;
        if (StringUtils.isNotEmpty(endDateStr)) {
            endDate = DateUtil.stringToZonedDateTime(endDateStr);
            endDate = endDate.plusDays(1);
        }

        // 申请人
        UUID applicantOid = null;
        if (StringUtils.isNotEmpty(applicantOidStr)) {
            applicantOid = UUID.fromString(applicantOidStr);
        }

        // 当前用户就是审批人
        UUID approverOid = OrgInformationUtil.getCurrentUserOid();

        Page page = PageUtil.getPage(pageable);
        // 查询未审批已审批单据
        List<ApprovalDocumentDTO> list = workFlowDocumentRefService.pageApprovalDocument(approverOid, entityType, documentNumber, documentName, documentTypeId,
                currencyCode, amountFrom, amountTo, applicantOid, beginDate, endDate, description, finished, page);

        HttpHeaders headers = PageUtil.getTotalHeader(page);
        return new ResponseEntity<>(list, headers, HttpStatus.OK);
    }

    /**
     * 待办事项-待审批单据-单据列表
     * @param documentCategory 单据大类
     * @param documentTypeId 单据类型id
     * @param beginDateStr 提交日期从
     * @param endDateStr 提交日期至
     * @param amountFrom 本币金额从
     * @param amountTo 本币金额至
     * @param remark 备注
     * @param documentNumber 单据编号
     * @param pageable 分页信息
     * @return
     */
    @GetMapping("/getApprovalToPendList")
    public ResponseEntity<List<WorkFlowDocumentRefDTO>> getApprovalToPendList(@RequestParam(value = "documentCategory", required = false) Integer documentCategory,
                                                                              @RequestParam(value = "documentTypeId", required = false) Long documentTypeId,
                                                                              @RequestParam(value = "applicantName", required = false) String applicantName,
                                                                              @RequestParam(value = "beginDate", required = false) String beginDateStr,
                                                                              @RequestParam(value = "endDate", required = false) String endDateStr,
                                                                              @RequestParam(value = "amountFrom", required = false) Double amountFrom,
                                                                              @RequestParam(value = "amountTo", required = false) Double amountTo,
                                                                              @RequestParam(value = "remark", required = false) String remark,
                                                                              @RequestParam(value = "documentNumber", required = false) String documentNumber,
                                                                              Pageable pageable) {

        // 最小提交日期
        ZonedDateTime beginDate = null;
        if (StringUtils.isNotEmpty(beginDateStr)) {
            beginDate = DateUtil.stringToZonedDateTime(beginDateStr);
        }

        // 最大提交日期
        ZonedDateTime endDate = null;
        if (StringUtils.isNotEmpty(endDateStr)) {
            endDate = DateUtil.stringToZonedDateTime(endDateStr);
            endDate = endDate.plusDays(1);
        }

        Page mybatisPage = PageUtil.getPage(pageable);
        //获取待审批列表
        List<WorkFlowDocumentRefDTO> workFlowDocumentRefDTOListList = workFlowApprovalService.getApprovalToPendDeatil(documentCategory,documentTypeId,applicantName,beginDate,endDate,amountFrom, amountTo,remark,documentNumber,mybatisPage);

        HttpHeaders httpHeaders = PageUtil.getTotalHeader(mybatisPage);
        return new ResponseEntity<>(workFlowDocumentRefDTOListList, httpHeaders, HttpStatus.OK);
    }

    /**
     * 待办事项-待审批单据-分类信息
     * @param documentCategory 单据大类
     * @param documentTypeId 单据类型id
     * @param beginDate 提交日期从
     * @param endDate 提交日期至
     * @param amountFrom 本币金额从
     * @param amountTo 本币金额至
     * @param remark 备注
     * @param documentNumber 单据编号
     * @return
     */
    @GetMapping("/getApprovalToPendTotal")
    public List<ApprovalDashboardDetailDTO> getApprovalToPendTotal(@RequestParam(value = "documentCategory", required = false) Integer documentCategory,
                                                                   @RequestParam(value = "documentTypeId", required = false) Long documentTypeId,
                                                                   @RequestParam(value = "applicantName", required = false) String applicantName,
                                                                   @RequestParam(value = "beginDate", required = false) String beginDateStr,
                                                                   @RequestParam(value = "endDate", required = false) String endDateStr,
                                                                   @RequestParam(value = "amountFrom", required = false) Double amountFrom,
                                                                   @RequestParam(value = "amountTo", required = false) Double amountTo,
                                                                   @RequestParam(value = "remark", required = false) String remark,
                                                                   @RequestParam(value = "documentNumber", required = false) String documentNumber) {

        // 最小提交日期
        ZonedDateTime beginDate = null;
        if (StringUtils.isNotEmpty(beginDateStr)) {
            beginDate = DateUtil.stringToZonedDateTime(beginDateStr);
        }

        // 最大提交日期
        ZonedDateTime endDate = null;
        if (StringUtils.isNotEmpty(endDateStr)) {
            endDate = DateUtil.stringToZonedDateTime(endDateStr);
            endDate = endDate.plusDays(1);
        }

        //获取单据类别和数量列表
        List<ApprovalDashboardDetailDTO> approvalDashboardDetailDTOList = workFlowApprovalService.getApprovalToPendTotal(documentCategory,documentTypeId,applicantName,beginDate,endDate,amountFrom, amountTo,remark,documentNumber);
        return approvalDashboardDetailDTOList;
    }


    /**
     * 待办事项-被退回单据/未完成单据
     * @param tabNumber tabNumber=1(被退回的单据) tabNumber=2(未完成的单据)
     * @param documentCategory 单据大类
     * @param documentTypeId 单据类型id
     * @param beginDateStr 提交日期从
     * @param endDateStr 提交日期至
     * @param amountFrom 本币金额从
     * @param amountTo 本币金额至
     * @param lastApproverOidStr 当前审批人oid
     * @param approvalNodeName 当前审批节点名称
     * @param remark 备注
     * @param documentNumber 单据编号
     * @return
     */
    @GetMapping("/my/document/detail/{tabNumber}")
    public ResponseEntity<List<WorkFlowDocumentRefDTO>> listMyDocumentDetail(@PathVariable Integer tabNumber,
                                                                             @RequestParam(value = "documentCategory", required = false) Integer documentCategory,
                                                                             @RequestParam(value = "documentTypeId", required = false) Long documentTypeId,
                                                                             @RequestParam(value = "applicantName", required = false) String applicantName,
                                                                             @RequestParam(value = "beginDate", required = false) String beginDateStr,
                                                                             @RequestParam(value = "endDate", required = false) String endDateStr,
                                                                             @RequestParam(value = "amountFrom", required = false) Double amountFrom,
                                                                             @RequestParam(value = "amountTo", required = false) Double amountTo,
                                                                             @RequestParam(value = "lastApproverOid", required = false) String lastApproverOidStr,
                                                                             @RequestParam(value = "approvalNodeName", required = false) String approvalNodeName,
                                                                             @RequestParam(value = "remark", required = false) String remark,
                                                                             @RequestParam(value = "documentNumber", required = false) String documentNumber,
                                                                             @RequestParam(value = "page",defaultValue = "0") int page,
                                                                             @RequestParam(value = "size",defaultValue = "10") int size){


        // 最小提交日期
        ZonedDateTime beginDate = null;
        if (StringUtils.isNotEmpty(beginDateStr)) {
            beginDate = DateUtil.stringToZonedDateTime(beginDateStr);
        }

        // 最大提交日期
        ZonedDateTime endDate = null;
        if (StringUtils.isNotEmpty(endDateStr)) {
            endDate = DateUtil.stringToZonedDateTime(endDateStr);
            endDate = endDate.plusDays(1);
        }

        // 驳回人
        UUID lastApproverOid = null;
        if (StringUtils.isNotEmpty(lastApproverOidStr)) {
            lastApproverOid = UUID.fromString(lastApproverOidStr);
        }

        Page mybatisPage = PageUtil.getPage(page, size);

        //获取待审批列表
        List<WorkFlowDocumentRefDTO> list = workFlowApprovalService.listMyDocumentDetail(tabNumber,documentCategory,documentTypeId,applicantName,beginDate,endDate,amountFrom, amountTo,lastApproverOid,approvalNodeName,remark,documentNumber,mybatisPage);

        HttpHeaders httpHeaders = PageUtil.getTotalHeader(mybatisPage);
        return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
    }

}
