/**
 * Created by 13576 on 2018/1/26.
 */
import config from 'config';
import httpFetch from 'share/httpFetch';

export default {
  //获取预算日记账行上的总金额
  getToleAmount(id) {
    return httpFetch.get(
      `${config.budgetUrl}/api/budget/journals/get/Line/amount/by/header/id?id=${id}`
    );
  },

  //查询预算日记账头(预算日记账)
  getBudgetJournalHeader(params) {
    return httpFetch.get(`${config.budgetUrl}/api/budget/journals/query/headers/byInput`, params);
  },

  //查询预算日记账头(复核)
  getBudgetJournalReCheckHeader(params) {
    return httpFetch.get(`${config.budgetUrl}/api/budget/journals/query/headers`, params);
  },

  //根据预算日记账编码查询预算日记账头行
  getBudgetJournalHeaderLine(journalCode) {
    return httpFetch.get(`${config.budgetUrl}/api/budget/journals/query/${journalCode}`);
  },

  //根据预算日记账编码查询预算日记账头
  getBudgetJournalHeaderDetil(journalCode) {
    return httpFetch.get(
      `${config.budgetUrl}/api/budget/journals/query/head/by/id?id=${journalCode}`
    );
  },

  //根据预算日记账编码查询预算日记账行
  getBudgetJournalLineDetil(headerId, params) {
    return httpFetch.get(
      `${config.budgetUrl}/api/budget/journals/query/line/by/header/id?headerId=${headerId}`,
      params
    );
  },

  //新增预算日记账头行
  addBudgetJournalHeaderLine(params) {
    return httpFetch.post(`${config.budgetUrl}/api/budget/journals`, params);
  },

  //新增，更新预算日记账行
  addBudgetJournalLine(params) {
    return httpFetch.post(`${config.budgetUrl}/api/budget/journals/insertOrUpdateLine`, params);
  },

  //删除日记账行
  deleteBudgetJournalLine(params) {
    return httpFetch.delete(`${config.budgetUrl}/api/budget/journals/batch/lines`, params);
  },

  //删除日记账
  deleteBudgetJournal(id) {
    return httpFetch.delete(`${config.budgetUrl}/api/budget/journals/${id}`);
  },

  //提交日记账
  commitBudgetJournal(headerId) {
    return httpFetch.post(`${config.budgetUrl}/api/budget/journals/submitJournal/${headerId}`);
  },

  //根据预算日记账类型，获得预算表
  getStructureByBudgetJournalType(params) {
    return httpFetch.get(
      `${config.budgetUrl}/api/budget/journals/selectByJournalTypeAndCompany`,
      params
    );
  },

  //获得默认预算表
  getDefaultStructure(params) {
    return httpFetch.get(
      `${config.budgetUrl}/api/budget/journal/type/assign/structures/queryDefaultStructure`,
      params
    );
  },

  //获取预算日记账类型
  getFormOid(params) {
    return httpFetch.get(
      `${config.budgetUrl}/api/budget/journals/journalType/selectByInput`,
      params
    );
  },

  //根据attachmentOid，查询附件
  getFileByAttachmentOid(attachmentOid) {
    return httpFetch.get(`${config.baseUrl}/api/attachments/${attachmentOid}`);
  },

  //根据维度Id,获取维度
  getDimensionValue(params) {
    return httpFetch.get(`${config.mdataUrl}/api/dimension/${params.dimensionId}`);
  },

  //根据维值查维度
  getDimensionByStructureId(params) {
    return httpFetch.get(`${config.budgetUrl}/api/budget/journals/getLayoutsByStructureId`, params);
  },

  //获取币种
  getCurrency() {
    return httpFetch.get(`${config.mdataUrl}/api/currency/rate/company/standard/currency/getAll`);
  },

  //预算日记账撤回（非工作流）
  revocationJournal(id, headerIds) {
    return httpFetch.post(
      `${config.budgetUrl}/api/budget/journals/submit/return/by/headerIdsAndUserId?userId=${id}`,
      headerIds
    );
  },

  //预算日记账通过（非工作流）
  passJournal(operationDetail, params) {
    return httpFetch.post(
      `${config.budgetUrl}/api/budget/journals/balance/create?operationDetail=${operationDetail}`,
      params
    );
  },

  //预算日记账驳回（非工作流）
  rejectJournal(operationDetail, params) {
    return httpFetch.post(
      `${config.budgetUrl}/api/budget/journals/rejectJournal?operationDetail=${operationDetail}`,
      params
    );
  },
  //计算总金额，拼接上币种的
  getTotalCurrencyAmount(id) {
    return httpFetch.get(
      `${config.budgetUrl}/api/budget/journals/get/line/amount/group/by/header/id?id=${id}`
    );
  },
  //获取审批历史（工作流）
  getBudgetJournalApproveHistory(params) {
    return httpFetch.get(
      `${config.workflowUrl}/api/workflow/approval/history?entityType=${
        params.entityType
      }&entityOid=${params.entityOid}`
    );
  },
  /**
   * 根据预算日志账获取预算表信息
   * @param {Long} id
   */
  getBudgetStructureByJournalId(id) {
    return httpFetch.get(
      `${config.budgetUrl}/api/budget/journals/query/by/journal?journalId=${id}`
    );
  },
};