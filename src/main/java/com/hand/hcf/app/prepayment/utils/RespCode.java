package com.hand.hcf.app.prepayment.utils;

public interface RespCode {

    /*通用*/
    String SYS_DATA_NOT_EXISTS = "SYS_DATA_NOT_EXISTS";  //数据不存在 *
    String SYS_ID_NULL= "SYS_ID_NULL";// id 不应该为空! *
    String SYS_ATTACHMENT_INFO_ERR = "SYS_ATTACHMENT_INFO_ERR";       //附加信息获取异常 *
    String SYS_CODE_TYPE_NOT_EXIT = "SYS_CODE_TYPE_NOT_EXIT";//系统值列表不存在  *


    /**用户相关*/
    String SYS_TENANT_INFO_NOT_EXISTS = "SYS_TENANT_INFO_NOT_EXISTS";   //租户信息为空
    String SYS_USER_INFO_NOT_EXISTS = "SYS_USER_INFO_NOT_EXISTS";    //用户信息为空
    String SYS_COMPANY_INFO_NOT_EXISTS = "SYS_COMPANY_INFO_NOT_EXISTS";     //公司信息为空
    String SYS_EMPLOYEE_INFO_NOT_EXISTS = "SYS_EMPLOYEE_INFO_NOT_EXISTS";     //员工信息为空
    String SYS_UNIT_INFO_NOT_EXITS = "SYS_UNIT_INFO_NOT_EXITS";  //部门信息为空

    /*预付款单类型*/
    String PREPAY_CASH_PAY_REQUISITION_TYPE_ALREADY_EXISTS = "PREPAY_CASH_PAY_REQUISITION_TYPE_ALREADY_EXISTS";//该预付款单类型数据已经存在!
    String PREPAY_CASH_PAY_REQUISITION_TYPE_NOT_EXIST = "PREPAY_CASH_PAY_REQUISITION_TYPE_NOT_EXIST";//该预付款单类型数据不存在!
    String PREPAY_CASH_PAY_REQUISITION_TYPE_NOT_ALLOWED_TO_REPEAT = "PREPAY_CASH_PAY_REQUISITION_TYPE_NOT_ALLOWED_TO_REPEAT";//同一账套下预付款单类型代码不允许重复!
    /*预付款单类型关联现金事务分类*/
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_TRANSACTIONCLASS_ALREADY_EXISTS = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_TRANSACTIONCLASS_ALREADY_EXISTS";//该预付款单类型关联现金事务分类数据已经存在!
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_TRANSACTIONCLASS_NOT_EXIST = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_TRANSACTIONCLASS_NOT_EXIST";//该预付款单类型关联现金事务分类数据不存在!
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_TRANSACTIONCLASS_NOT_ALLOWED_TO_REPEAT = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_TRANSACTIONCLASS_NOT_ALLOWED_TO_REPEAT";//同一预付款单类型下的现金事务分类不允许重复!
    /*预付款单类型关联公司*/
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_COMPANY_ALREADY_EXISTS = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_COMPANY_ALREADY_EXISTS";//该预付款单类型关联公司数据已经存在!
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_COMPANY_NOT_EXIST = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_COMPANY_NOT_EXIST";//该预付款单类型关联公司数据不存在!
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_COMPANY_NOT_ALLOWED_TO_REPEAT = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_COMPANY_NOT_ALLOWED_TO_REPEAT";//同一预付款单类型下的公司不允许重复!
    /*预付款单类型关联申请单类型*/
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_REQUISITIONTYPE_ALREADY_EXISTS = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_REQUISITIONTYPE_ALREADY_EXISTS";//该预付款单类型关联申请单类型数据已经存在!
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_REQUISITIONTYPE_NOT_EXIST = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_REQUISITIONTYPE_NOT_EXIST";//该预付款单类型关联申请单类型数据不存在!
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_REQUISITIONTYPE_NOT_ALLOWED_TO_REPEAT = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_REQUISITIONTYPE_NOT_ALLOWED_TO_REPEAT";//同一预付款单类型下的申请单类型不允许重复!
    /*预付款单类型关联部门*/
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_DEPARTMENT_ALREADY_EXISTS = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_DEPARTMENT_ALREADY_EXISTS";//该预付款单类型关联部门数据已经存在!
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_DEPARTMENT_NOT_EXIST = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_DEPARTMENT_NOT_EXIST";//该预付款单类型关联部门数据不存在!
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_DEPARTMENT_NOT_ALLOWED_TO_REPEAT = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_DEPARTMENT_NOT_ALLOWED_TO_REPEAT";//同一预付款单类型下的部门不允许重复!
    /*预付款单类型关联人员组*/
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_USER_GROUP_ALREADY_EXISTS = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_USER_GROUP_ALREADY_EXISTS";//该预付款单类型关联人员组数据已经存在!
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_USER_GROUP_NOT_EXIST = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_USER_GROUP_NOT_EXIST";//该预付款单类型关联人员组数据不存在!
    String PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_USER_GROUP_NOT_ALLOWED_TO_REPEAT = "PREPAY_CASHPAYREQUISITIONTYPE_ASSIGN_USER_GROUP_NOT_ALLOWED_TO_REPEAT";//同一预付款单类型下的人员组不允许重复!
    /*预付款单*/
    String PREPAY_USER_BANK_ERROR= "PREPAY_USER_BANK_ERROR";
    String PREPAY_CASHPAYREQUISITION_HEAD_LINE_AMOUNT_NOT_TRUE = "PREPAY_CASHPAYREQUISITION_HEAD_LINE_AMOUNT_NOT_TRUE";//预付款单头行金额不相等
    String PREPAY_CASHPAYREQUISITION_STATUS_NOT_ALLOW = "PREPAY_CASHPAYREQUISITION_STATUS_NOT_ALLOW";//只有提交的单据可以被通过或拒绝
    String PREPAY_CASHPAYREQUISITION_SUBMIT_ERROR = "PREPAY_CASHPAYREQUISITION_SUBMIT_ERROR";//只有新建或撤回或拒绝的单据允许提交
    String PREPAY_CASHPAYREQUISITION_RETURN_ERROR = "PREPAY_CASHPAYREQUISITION_RETURN_ERROR";//只有提交的单据才能撤回
    String PREPAY_CASHPAYREQUISITION_STATUS_ERROR = "PREPAY_CASHPAYREQUISITION_STATUS_ERROR";//此状态单据不允许此操作
    String PREPAY_SYSTEM_VALUE_PAYMENT_METHOD_ERROR= "PREPAY_SYSTEM_VALUE_PAYMENT_METHOD_ERROR";//付款方式系统值列表获取异常
    String PREPAY_PAYMENT_METHOD_ERROR = "PREPAY_PAYMENT_METHOD_ERROR";//付款方式不是指定的值
    String PREPAY_CASHPAYREQUISITION_HEAD_NOT_EXIT = "PREPAY_CASHPAYREQUISITION_HEAD_NOT_EXIT";//预付款单头不存在
    String PREPAY_GET_FLOW_ITEM_ERROR= "PREPAY_GET_FLOW_ITEM_ERROR";//根据现金事务分类获取现金流量项异常
    String PREPAY_HEAD_STATUS_ERROR = "PREPAY_HEAD_STATUS_ERROR";//获取预付款单操作类型值列表异常
    String PREPAY_CASHPAYREQUISITION_FORM_OID_NOT_EXIT= "PREPAY_CASHPAYREQUISITION_FORM_OID_NOT_EXIT";//走工作流需在预付款单类型维护表单
    String PREPAY_CASHPAYREQUISITION_HEAD_LINE_IS_NULL = "PREPAY_CASHPAYREQUISITION_HEAD_LINE_IS_NULL";//单据提交时么没有行信息
    String PREPAY_CONTRACT_INFO_ERROR = "PREPAY_CONTRACT_INFO_ERROR";//合同信息获取异常
    String PREPAY_CLASS_NAME_ERROR = "PREPAY_CLASS_NAME_ERROR";//现金事务分类信息获取异常
    String PREPAY_PREPAYMENT_CODE_ERROR = "PREPAY_PREPAYMENT_CODE_ERROR";//预付款单编号生成异常
    String PREPAY_GET_APPLICATION_INFO_ERROR = "PREPAY_GET_APPLICATION_INFO_ERROR";//预付款单行获取申请单信息失败
    String PREPAY_PREPAYMENT_TYPE_APPLICATION_ERROR = "PREPAY_PREPAYMENT_TYPE_APPLICATION_ERROR";//预付款单类型已关联申请单，请选择申请单
    String PREPAY_PREPAYMENT_LINE_AMOUNT_TOO_BIG = "PREPAY_PREPAYMENT_LINE_AMOUNT_TOO_BIG";//预付款行金额大于申请单可关联金额
    String PREPAY_CASH_REALTATION_SHOULD_NOT_EMPTY ="PREPAY_CASH_REALTATION_SHOULD_NOT_EMPTY";  //关联的现金事务不能为空
    /*预付款对接支付平台*/
    String PREPAY_TOTAL_AMOUNT_ERROR = "PREPAY_TOTAL_AMOUNT_ERROR";//总金额不能小于等于零
    String PREPAY_WRITTEN_OFF_AMOUNT_LESS_LEAN_ZERO = "PREPAY_WRITTEN_OFF_AMOUNT_LESS_LEAN_ZERO";//已核销金额不能小于零
    String PREPAY_WRITTEN_OFF_AMOUNT_MORE_LEAN_ZERO = "PREPAY_WRITTEN_OFF_AMOUNT_MORE_LEAN_ZERO";//已核销金额不能大于总金额
    String PREPAY_NO_PAY_COMPANY = "PREPAY_NO_PAY_COMPANY";//未获取到付款公司
    String PREPAY_RECEIVABLES_TYPE_ERROR = "PREPAY_RECEIVABLES_TYPE_ERROR";//收款方类型错误
    String PREPAY_PAYMENT_METHOD_TYPE_ERROR = "PREPAY_PAYMENT_METHOD_TYPE_ERROR";//付款方式类型错误
    String PREPAY_CASH_TYPE_ERROR = "PREPAY_CASH_TYPE_ERROR";//现金事务类型错误
    String PREPAY_CURRENCY_NOT_FOUND = "PREPAY_CURRENCY_NOT_FOUND";//币种不存
    // 在
    String PREPAY_SOURCE_DOCUMENT_INFO_NULL = "PREPAY_SOURCE_DOCUMENT_INFO_NULL";//付款单对应的来源单据信息不能为空
    String PREPAY_NOT_FOUND_SOURCE_DOCUMENT = "PREPAY_NOT_FOUND_SOURCE_DOCUMENT";//不存在付款单对应的来源单据信息
    String PREPAY_CREATE_IS_NULL= "PREPAY_CREATE_IS_NULL";//创建人不能为空
    String PREPAY_ONLY_PASS_CAN_PUSH = "PREPAY_ONLY_PASS_CAN_PUSH";//只有审批通过的单据才可以推送
    String PREPAY_PAY_PUSH_ERROR = "PREPAY_PAY_PUSH_ERROR";//推送支付平台异常

    /*预付款单对接合同*/
    String PREPAY_GET_CONTRACT_HEAD_AND_LINE_ERROR = "PREPAY_GET_CONTRACT_HEAD_AND_LINE_ERROR";//访问合同服务器错误
    String PREPAY_CON_CONTRACT_HEADER_ID_NOT_EXISTS = "PREPAY_CON_CONTRACT_HEADER_ID_NOT_EXISTS";//该合同头ID不存在!
    String PREPAY_CON_CONTRACT_LINE_ID_NOT_EXISTS = "PREPAY_CON_CONTRACT_LINE_ID_NOT_EXISTS";//该合同行ID不存在!
    String PREPAY_CON_CONTRACT_DOCUMENT_RELATION_DOCUMENT_TYPE_NOT_EXISTS = "PREPAY_CON_CONTRACT_DOCUMENT_RELATION_DOCUMENT_TYPE_NOT_EXISTS";//该单据类型不存在!
    String PREPAY_CON_CONTRACT_DOCUMENT_RELATION_AMOUNT_SMALL = "PREPAY_CON_CONTRACT_DOCUMENT_RELATION_AMOUNT_SMALL";//本次关联金额必须大于0!
    String PREPAY_CON_CONTRACT_DOCUMENT_RELATION_AMOUNT_LARGE = "PREPAY_CON_CONTRACT_DOCUMENT_RELATION_AMOUNT_LARGE";//关联金额超过合同行总金额!
    String PREPAY_CON_CONTRACT_DOCUMENT_RELATION_NOT_EXISTS = "PREPAY_CON_CONTRACT_DOCUMENT_RELATION_NOT_EXISTS";//该合同与业务单据关联数据不存在!
    String PREPAY_GET_BY_NUMBER_ERROR = "PREPAY_GET_BY_NUMBER_ERROR"; //通过合同编号获取单据失败
    String PREPAY_ERROR_REST_FOR_CONTRACT_MODULE = "PREPAY_ERROR_REST_FOR_CONTRACT_MODULE"; //调用合同模块异常！

    //预付款对接申请单
    String PREPAY_RE_APPLICATION_ERROR = "PREPAY_RE_APPLICATION_ERROR";//"释放申请单关联预付款异常"
    String PREPAY_CREATE_APPLICATION_ERROR = "PREPAY_CREATE_APPLICATION_ERROR";//创建申请单-预付款关联关系异常


    String PREPAY_PUBLIC_REPORT_SCHEDULE_STATUS = "PREPAY_PUBLIC_REPORT_SCHEDULE_STATUS";//计划付款行合同已暂挂,已取消,已完成
    String PREPAY_CONTRACT_STATUS_HOLD = "PREPAY_CONTRACT_STATUS_HOLD";//已暂挂
    String PREPAY_CONTRACT_STATUS_CANCEL = "PREPAY_CONTRACT_STATUS_CANCEL";//已取消
    String PREPAY_CONTRACT_STATUS_FINISH = "PREPAY_CONTRACT_STATUS_FINISH";//已完成

    String PAYMENT_PARTNER_CATEGORY_ERROR = "PAYMENT_PARTNER_CATEGORY_ERROR";//收款方类型错误
    String PAYMENT_METHOD_CATEGORY_ERROR = "PAYMENT_METHOD_CATEGORY_ERROR";//付款方式类型错误
    String PAYMENT_COMPANY_BANK_INFO_ERROR = "PAYMENT_COMPANY_BANK_INFO_ERROR";//付款银行账户信息对接查询失败
    String PAYMENT_PAYEE_COMPANY_BANK_INFO_NULL="PAYMENT_PAYEE_COMPANY_BANK_INFO_NULL";//根据收款银行账号获取信息失败

}
