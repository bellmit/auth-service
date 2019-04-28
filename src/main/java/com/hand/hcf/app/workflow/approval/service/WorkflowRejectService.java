package com.hand.hcf.app.workflow.approval.service;

//import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.hand.hcf.app.core.exception.BizException;
import com.hand.hcf.app.workflow.approval.constant.ErrorConstants;
import com.hand.hcf.app.workflow.approval.dto.WorkflowInstance;
import com.hand.hcf.app.workflow.approval.dto.WorkflowNode;
import com.hand.hcf.app.workflow.approval.dto.WorkflowRule;
import com.hand.hcf.app.workflow.approval.dto.WorkflowTask;
import com.hand.hcf.app.workflow.approval.dto.WorkflowUser;
import com.hand.hcf.app.workflow.approval.implement.WorkflowRejectInstanceAction;
import com.hand.hcf.app.workflow.approval.implement.WorkflowRejectNodeAction;
import com.hand.hcf.app.workflow.approval.implement.WorkflowRejectTaskAction;
import com.hand.hcf.app.workflow.approval.util.WorkflowResult;
import com.hand.hcf.app.workflow.domain.WorkFlowDocumentRef;
import com.hand.hcf.app.workflow.dto.ApprovalReqDTO;
import com.hand.hcf.app.workflow.dto.ApprovalResDTO;
import com.hand.hcf.app.workflow.service.WorkFlowDocumentRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

/**
 * 工作流驳回逻辑
 * @author mh.z
 * @date 2019/04/07
 */
@Service
public class WorkflowRejectService {
    @Autowired
    private WorkFlowDocumentRefService workFlowDocumentRefService;

    @Autowired
    private WorkflowBaseService workflowBaseService;

    @Autowired
    private WorkflowMainService workflowMainService;

    @Autowired
    private WorkflowApprovalNotificationService workflowApprovalNotificationService;

    @Autowired
    private WorkflowActionService workflowActionService;

    /**
     * 驳回审批
     * @version 1.0
     * @author mh.z
     * @date 2019/04/07
     *
     * @param userOid 审批人oid
     * @param approvalReqDTO 要审批驳回的单据
     * @return 审批的结果
     */
    //@LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public ApprovalResDTO rejectWorkflow(UUID userOid, ApprovalReqDTO approvalReqDTO) {
        ApprovalResDTO approvalResDTO = new ApprovalResDTO();
        approvalResDTO.setSuccessNum(0);
        approvalResDTO.setFailNum(0);

        List<ApprovalReqDTO.Entity> entityList = approvalReqDTO.getEntities();
        if (CollectionUtils.isEmpty(entityList)) {
            return approvalResDTO;
        }

        Integer entityType = null;
        UUID entityOid = null;
        String approvalText = approvalReqDTO.getApprovalTxt();

        for (ApprovalReqDTO.Entity entity : entityList) {
            entityType = entity.getEntityType();
            entityOid = UUID.fromString(entity.getEntityOid());
            // 驳回审批
            doRejectWorkflow(entityType, entityOid, userOid, approvalText);
            // 累加成功数
            approvalResDTO.setSuccessNum(approvalResDTO.getSuccessNum() + 1);
        }

        return approvalResDTO;
    }

    /**
     * 驳回审批
     * @version 1.0
     * @author mh.z
     * @date 2019/04/07
     *
     * @param entityType 单据大类
     * @param entityOid 单据oid
     * @param userOid 审批人oid
     * @param approvalText 审批意见
     */
    protected void doRejectWorkflow(Integer entityType, UUID entityOid, UUID userOid, String approvalText) {
        Assert.notNull(entityType, "entityType null");
        Assert.notNull(entityOid, "entityOid null");
        Assert.notNull(userOid, "userOid null");

        // 查找要驳回的实例
        WorkFlowDocumentRef workFlowDocumentRef = workFlowDocumentRefService
                .getByDocumentOidAndDocumentCategory(entityOid, entityType);

        WorkflowInstance instance = new WorkflowInstance(workFlowDocumentRef);
        WorkflowUser user = new WorkflowUser(userOid);
        WorkflowRejectTaskAction action = new WorkflowRejectTaskAction(workflowActionService, instance, user, approvalText);

        // 对同个实例的操作不支持并发
        workflowBaseService.lockInstance(instance);
        // 驳回任务
        workflowMainService.runWorkflow(action);

        // 刷新实例
        workFlowDocumentRef = workFlowDocumentRefService.selectById(workFlowDocumentRef.getId());
        // 通知审批结果
        workflowApprovalNotificationService.sendMessage(workFlowDocumentRef);
    }

}
