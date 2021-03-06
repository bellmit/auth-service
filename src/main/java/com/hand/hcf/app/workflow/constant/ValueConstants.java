package com.hand.hcf.app.workflow.constant;

public final class ValueConstants {
    /** 表单类型值列表代码 */
    public static final String SYS_CODE_FORM_TYPE = "SYS_APPROVAL_FORM_TYPE";

    //workflow
    public static final String WORKFLOW_APPROVAL_SPLIT = ":";

    public static final String COUNTERSIGN_APPROVER = "countersignApprover"; //受会签规则影响
    public static final String APPROVER = "approver"; //不受会签规则影响
    public static final String APPORTIONMENT_DEPARTMENTS = "apportionmentDepartments"; //分摊的部门
    public static final String APPORTIONMENT_COST_CENT_ITEMS = "apportionmentCostCentItems"; //分摊的成本中心
    public static final String APPROVAL_CHAIN_NOTICE_FLAG = "#";//只通知标记


    private ValueConstants() {
    }
}
