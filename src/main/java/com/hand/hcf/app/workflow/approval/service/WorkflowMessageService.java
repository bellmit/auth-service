package com.hand.hcf.app.workflow.approval.service;

import com.hand.hcf.app.common.co.WorkflowMessageCO;
import com.hand.hcf.app.common.enums.DocumentOperationEnum;
import com.hand.hcf.app.common.event.WorkflowCustomRemoteEvent;
import com.hand.hcf.app.mdata.base.util.OrgInformationUtil;
import com.hand.hcf.app.workflow.domain.WorkFlowDocumentRef;
import com.hand.hcf.app.workflow.domain.WorkFlowEventLogs;
import com.hand.hcf.app.workflow.service.WorkFlowDocumentRefService;
import com.hand.hcf.app.workflow.service.WorkFlowEventLogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 工作流消息逻辑
 * @author mh.z
 * @date 2019/04/07
 */
@Service
public class WorkflowMessageService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowMessageService.class);

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private WorkFlowEventLogsService workFlowEventLogsService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private WorkFlowDocumentRefService workFlowDocumentRefService;

    /**
     * 发送消息
     * @author mh.z
     * @date 2019/04/09
     *
     * @param workFlowDocumentRef
     */
    public void sendMessage(WorkFlowDocumentRef workFlowDocumentRef) {
        Assert.notNull(workFlowDocumentRef, "workFlowDocumentRef null");
        Assert.notNull(workFlowDocumentRef.getStatus(), "workFlowDocumentRef.status null");
        Integer status = workFlowDocumentRef.getStatus();

        // 只发送通过/驳回/已撤销的消息
        if (!(DocumentOperationEnum.APPROVAL_PASS.getId().equals(status)
                || DocumentOperationEnum.APPROVAL_REJECT.getId().equals(status)
                || DocumentOperationEnum.WITHDRAW.getId().equals(status))) {
            return;
        }

        WorkflowCustomRemoteEvent workflowCustomRemoteEvent = createWorkflowCustomRemoteEvent(workFlowDocumentRef);
        // 记录到事件日志表
        WorkFlowEventLogs workFlowEventLogs = createWorkFlowEventLogs(workflowCustomRemoteEvent, workFlowDocumentRef);
        workFlowEventLogsService.createSysWorkflowEventLogs(workFlowEventLogs);

        workFlowDocumentRef.setEventId(workflowCustomRemoteEvent.getId());
        workFlowDocumentRef.setEventConfirmStatus(false);
        workFlowDocumentRefService.saveOrUpdate(workFlowDocumentRef);

        logger.info("[发布工作流事件消息]：" + workflowCustomRemoteEvent);
        applicationEventPublisher.publishEvent(workflowCustomRemoteEvent);
    }

    /**
     * @author mh.z
     * @date 2019/04/09
     *
     * @param workFlowDocumentRef
     * @return
     */
    protected WorkflowCustomRemoteEvent createWorkflowCustomRemoteEvent(WorkFlowDocumentRef workFlowDocumentRef) {
        Assert.notNull(workFlowDocumentRef, "workFlowDocumentRef null");

        WorkflowMessageCO workflowMessageCO = new WorkflowMessageCO();
        workflowMessageCO.setUserBean(OrgInformationUtil.getUser());
        workflowMessageCO.setEntityOid(workFlowDocumentRef.getDocumentOid());
        workflowMessageCO.setEntityType(workFlowDocumentRef.getDocumentCategory().toString());
        workflowMessageCO.setStatus(workFlowDocumentRef.getStatus());
        workflowMessageCO.setUserId(workFlowDocumentRef.getCreatedBy());
        workflowMessageCO.setDocumentId(workFlowDocumentRef.getDocumentId());
        workflowMessageCO.setApprovalText(workFlowDocumentRef.getRejectReason());
        workflowMessageCO.setRemark("单据编号:" + workFlowDocumentRef.getDocumentNumber());

        String originalSevice = applicationName + ":**";
        String destinationService = workFlowDocumentRef.getDestinationService();
        WorkflowCustomRemoteEvent workflowCustomRemoteEvent = new WorkflowCustomRemoteEvent(
                this, originalSevice, destinationService, workflowMessageCO);

        return workflowCustomRemoteEvent;
    }

    /**
     * @author mh.z
     * @date 2019/04/09
     *
     * @param workflowCustomRemoteEvent
     * @param workFlowDocumentRef
     * @return
     */
    protected WorkFlowEventLogs createWorkFlowEventLogs(WorkflowCustomRemoteEvent workflowCustomRemoteEvent,
                                                        WorkFlowDocumentRef workFlowDocumentRef) {
        Assert.notNull(workFlowDocumentRef, "workflowCustomRemoteEvent null");
        Assert.notNull(workFlowDocumentRef, "workFlowDocumentRef null");

        WorkFlowEventLogs workFlowEventLogs = new WorkFlowEventLogs();
        workFlowEventLogs.setEventId(workflowCustomRemoteEvent.getId());
        workFlowEventLogs.setDocumentOid(workFlowDocumentRef.getDocumentOid());
        workFlowEventLogs.setDocumentCategory(workFlowDocumentRef.getDocumentCategory());
        workFlowEventLogs.setDestinationService(workFlowDocumentRef.getDestinationService());
        workFlowEventLogs.setEventConfirmStatus(false);

        return workFlowEventLogs;
    }

}