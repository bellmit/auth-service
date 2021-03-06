package com.hand.hcf.app.payment.utils;

public interface RespCode {

    //SYS
    String SYS_REQUEST_FREQUENCY_TOO_FAST = "SYS_REQUEST_FREQUENCY_TOO_FAST"; // 请求频率过快!

    String SYS_ERROR = "SYS_ERROR";   //系统错误
    String SYS_COLUMN_SHOULD_BE_EMPTY = "SYS_COLUMN_SHOULD_BE_EMPTY"; // 列应该为空
    String SYS_COLUMN_SHOULD_NOT_BE_EMPTY = "SYS_COLUMN_SHOULD_NOT_BE_EMPTY"; // 列不应该为空
    String SYS_DATASOURCE_CANNOT_FIND_OBJECT = "SYS_DATASOURCE_CANNOT_FIND_OBJECT"; //数据库找不到对象
    String SYS_DATA_NOT_EXISTS = "SYS_DATA_NOT_EXISTS";  //数据不存在
    String SYS_ID_NULL = "SYS_ID_NULL";// id 不应该为空
    String SYS_ID_NOT_NULL = "SYS_ID_NOT_NULL";// id 应该为空
    String SYS_VERSION_NUMBER_CHANGED = "SYS_VERSION_NUMBER_CHANGED";//版本号不一致
    String SYS_PERIOD_NOT_FOUND = "SYS_PERIOD_NOT_FOUND";//获取期间详细信息失败
    String SYS_VALUE_OF_VALUELIST_NOT_FOUND = "SYS_VALUE_OF_VALUELIST_NOT_FOUND"; // 未获取到相应值列表的值
    String SYS_PARAMETER_FORMAT_ERROR = "SYS_PARAMETER_FORMAT_ERROR"; //参数格式有误

    /**
     * 用户相关
     */
    String SYS_TENANT_INFO_NOT_EXISTS = "SYS_TENANT_INFO_NOT_EXISTS";   //租户信息为空
    String SYS_USER_INFO_NOT_EXISTS = "SYS_USER_INFO_NOT_EXISTS";    //用户信息为空
    String SYS_COMPANY_INFO_NOT_EXISTS = "SYS_COMPANY_INFO_NOT_EXISTS";     //公司信息为空
    String SYS_EMPLOYEE_INFO_NOT_EXISTS = "SYS_EMPLOYEE_INFO_NOT_EXISTS";     //员工信息为空
    String SYS_UNIT_INFO_NOT_EXITS = "SYS_UNIT_INFO_NOT_EXITS";  //部门信息为空


    /*银行定义*/
    String PAYMENT_UNIVERSAL_BANK_CODE_NOT_UNIQUE = "PAYMENT_UNIVERSAL_BANK_CODE_NOT_UNIQUE";//通用银行代码不唯一!
    String PAYMENT_USER_DEFINED_BANK_CODE_NOT_UNIQUE = "PAYMENT_USER_DEFINED_BANK_CODE_NOT_UNIQUE";//自定义银行代码不唯一!
    String PAYMENT_UNIVERSAL_BANK_CODE_EN_NOT_UNIQUE = "PAYMENT_UNIVERSAL_BANK_CODE_EN_NOT_UNIQUE";//通用银行英文代码不唯一!
    String PAYMENT_USER_DEFINED_BANK_CODE_EN_NOT_UNIQUE = "PAYMENT_USER_DEFINED_BANK_CODE_EN_NOT_UNIQUE";//自定义银行英文代码不唯一!
    String PAYMENT_USER_DEFINED_BANK_CODE_NOT_REPEAT_WITH_UNIVERSAL_BANK = "PAYMENT_USER_DEFINED_BANK_CODE_NOT_REPEAT_WITH_UNIVERSAL_BANK";//不能与通用银行代码重复!


    /*现金流量项*/
    String PAYMENT_CASH_FLOW_ITEM_ALREADY_EXISTS = "PAYMENT_CASH_FLOW_ITEM_ALREADY_EXISTS";//该现金流量项数据已经存在!
    String PAYMENT_CASH_FLOW_ITEM_NOT_EXIST = "PAYMENT_CASH_FLOW_ITEM_NOT_EXIST";//该现金流量项数据不存在!
    String PAYMENT_CASH_FLOW_ITEM_NOT_ALLOWED_TO_REPEAT = "PAYMENT_CASH_FLOW_ITEM_NOT_ALLOWED_TO_REPEAT";//同一账套下现金流量项代码不允许重复!

    //RespCode.PAYMENT_COMPANY_BANK_PAYMENT_NOT_EXIT
    /*付款方式*/
    String PAYMENT_PAYMENT_METHOD_NOT_SYSTEM_VALUE = "PAYMENT_PAYMENT_METHOD_NOT_SYSTEM_VALUE";//付款方式值列表不合法
    String PAYMENT_PAYMENT_METHOD_IS_NULL = "PAYMENT_PAYMENT_METHOD_IS_NULL";//付款方式类型为空
    String PAYMENT_PAYMENT_METHOD_CODE_EXIT = "PAYMENT_PAYMENT_METHOD_CODE_EXIT";//付款方式代码重复
    String PAYMENT_COMPANY_BANK_PAYMENT_NOT_EXIT = "PAYMENT_COMPANY_BANK_PAYMENT_NOT_EXIT";//公司银行账户的付款方式不存在


    /*现金事务分类*/
    String PAYMENT_CASH_TRANSACTION_CLASS_ALREADY_EXISTS = "PAYMENT_CASH_TRANSACTION_CLASS_ALREADY_EXISTS";//该现金事务分类数据已经存在!
    String PAYMENT_CASH_TRANSACTION_CLASS_NOT_EXIST = "PAYMENT_CASH_TRANSACTION_CLASS_NOT_EXIST";//该现金事务分类数据不存在!
    String PAYMENT_CASH_TRANSACTION_CLASS_NOT_ALLOWED_TO_REPEAT = "PAYMENT_CASH_TRANSACTION_CLASS_NOT_ALLOWED_TO_REPEAT";//同一账套下现金事务分类代码不允许重复!
    /*现金事务分类关联现金流量项*/
    String PAYMENT_CASH_DEFAULT_FLOW_ITEM_ALREADY_EXISTS = "PAYMENT_CASH_DEFAULT_FLOW_ITEM_ALREADY_EXISTS";//该现金事务分类关联现金流量项数据已经存在!
    String PAYMENT_CASH_DEFAULT_FLOW_ITEM_NOT_EXIST = "PAYMENT_CASH_DEFAULT_FLOW_ITEM_NOT_EXIST";//该现金事务分类关联现金流量项数据不存在!
    String PAYMENT_CASH_DEFAULT_FLOW_ITEM_NOT_ALLOWED_TO_REPEAT = "PAYMENT_CASH_DEFAULT_FLOW_ITEM_NOT_ALLOWED_TO_REPEAT";//同一现金事务分类下的现金流量项不允许重复!

    //RespCode.PAYMENT_PAY_COMPANY_BANK_INFO_NULL
    /*支付*/
    String PAYMENT_SUBMIT_ERROR = "PAYMENT_SUBMIT_ERROR"; //提交时发生异常
    String PAYMENT_PAY_ERROR = "PAYMENT_PAY_ERROR";     //支付异常
    String PAYMENT_PAYMENT_DETAIL_NOT_EXISTS = "PAYMENT_PAYMENT_DETAIL_NOT_EXISTS";//支付信息不存在
    String PAYMENT_WRITE_OFF_AMOUNT_ERROR = "PAYMENT_WRITE_OFF_AMOUNT_ERROR";//核销金额超出可用范围!
    String PAYMENT_COMPANY_BANK_INFO_ERROR = "PAYMENT_COMPANY_BANK_INFO_ERROR";//付款银行账户信息对接查询失败
    String PAYMENT_COMMIT_AMOUNT_WRONGFUL = "PAYMENT_COMMIT_AMOUNT_WRONGFUL";//已提交金额大于总金额
    String PAYMENT_INCORRECT_DATA_TRANSMISSION = "PAYMENT_INCORRECT_DATA_TRANSMISSION";//detailId和版本号数据传输不正确
    String PAYMENT_NON_CANCELLATION_PAYMENT_OF_REFUND_DATA = "PAYMENT_NON_CANCELLATION_PAYMENT_OF_REFUND_DATA";//退票数据不可取消支付
    String PAYMENT_WRITE_OFF_INFO_INCOMPLETE = "PAYMENT_WRITE_OFF_INFO_INCOMPLETE";//核销信息不完整
    String PAYMENT_TOTAL_AMOUNT_ERROR = "PAYMENT_TOTAL_AMOUNT_ERROR";//总金额不能小于等于零
    String PAYMENT_WRITE_OFF_AMOUNT_NOT_LESS_ZERO = "PAYMENT_WRITE_OFF_AMOUNT_NOT_LESS_ZERO";//已核销金额不能小于零
    String PAYMENT_WRITE_OFF_AMOUNT_NOT_GREATER_SUM = "PAYMENT_WRITE_OFF_AMOUNT_NOT_GREATER_SUM";//已核销金额不能大于总金额
    String PAYMENT_COMPANY_ID_NOT_EXISTS = "PAYMENT_COMPANY_ID_NOT_EXISTS";//未获取到付款公司
    String PAYMENT_PARTNER_CATEGORY_ERROR = "PAYMENT_PARTNER_CATEGORY_ERROR";//收款方类型错误
    String PAYMENT_METHOD_CATEGORY_ERROR = "PAYMENT_METHOD_CATEGORY_ERROR";//付款方式类型错误
    String PAYMENT_TRANSACTION_TYPE_ERROR = "PAYMENT_TRANSACTION_TYPE_ERROR";//现金事务类型错误
    String PAYMENT_CURRENCY_NOT_EXISTS = "PAYMENT_CURRENCY_NOT_EXISTS";//币种不存在
    String PAYMENT_ACP_REQUISITION_SOURCE_INFO_NOT_NULL = "PAYMENT_ACP_REQUISITION_SOURCE_INFO_NOT_NULL";//付款单对应的来源单据信息不能为空
    String PAYMENT_ACP_REQUISITION_SOURCE_INFO_NOT_EXISTS = "PAYMENT_ACP_REQUISITION_SOURCE_INFO_NOT_EXISTS";//不存在付款单对应的来源单据信息
    String PAYMENT_CREATE_BY_NOT_EXISTS = "PAYMENT_CREATE_BY_NOT_EXISTS";//创建人不能为空
    String PAYMENT_GET_PAYMENT_METHOD_ERROR = "PAYMENT_GET_PAYMENT_METHOD_ERROR";//获取付款方式异常
    String PAYMENT_PAY_COMPANY_BANK_INFO_NULL = "PAYMENT_PAY_COMPANY_BANK_INFO_NULL";//根据付款银行账号获取信息失败
    String PAYMENT_PAYEE_COMPANY_BANK_INFO_NULL = "PAYMENT_PAYEE_COMPANY_BANK_INFO_NULL";//根据收款银行账号获取信息失败
    String PAYMENT_CURRENCY_AMOUNT_EXCEEDS_AMOUNT = "PAYMENT_CURRENCY_AMOUNT_EXCEEDS_AMOUNT";//本次支付的金额超过可支付金额
    String PAYMENT_NOT_SAME_CURRENCY_CAN_NOT_PAY_CONCURRENTLY = "PAYMENT_NOT_SAME_CURRENCY_CAN_NOT_PAY_CONCURRENTLY"; //不同币种不可同时支付
    String PAYMENT_PAYING_NOT_ALLOW = "PAYMENT_PAYING_NOT_ALLOW";//只有支付中的数据允许此操作
    String PAYMENT_FAILURE_ACCOUNTING_MODULE = "PAYMENT_FAILURE_ACCOUNTING_MODULE";//往核算模块发送数据失败!因为{}
    String PAYMENT_DETAIL_STATUS_NOT_ALLOW = "PAYMENT_DETAIL_STATUS_NOT_ALLOW";//数据状态不允许反冲
    String PAYMENT_DETAIL_BACK_FLASH_HAS = "PAYMENT_DETAIL_BACK_FLASH_HAS";//此条数据已有反冲或退款数据
    String PAYMENT_DETAIL_FLASH_NOT_ALLOW_SUBMIT = "PAYMENT_DETAIL_FLASH_NOT_ALLOW_SUBMIT";//只有状态是1001且是反冲的数据允许提交
    String PAYMENT_DETAIL_SUBMIT_ALLOW = "PAYMENT_DETAIL_SUBMIT_ALLOW";//只有提交的单据允许复核和驳回
    String PAYMENT_AMOUNT_MUST_GREATER_ZERO = "PAYMENT_AMOUNT_MUST_GREATER_ZERO"; // 本次支付金额必须大于0
    String PAYMENT_REQUISITION_NOT_CREATE_PAYMENT_DETAIL = "PAYMENT_REQUISITION_NOT_CREATE_PAYMENT_DETAIL"; //该报账单行信息没有生成支付明细数据


    /*支付退款*/
    String PAYMENT_PAY_REFUND_AMOUNT_ERROR = "PAYMENT_PAY_REFUND_AMOUNT_ERROR";//可退款金额大于本次退款金额
    String PAYMENT_DETAIL_UPDATE_ALLOW = "PAYMENT_DETAIL_UPDATE_ALLOW";//只有编辑中和拒绝的单据才允许操作
    String PAYMENT_DETAIL_DELETE_ALLOW = "PAYMENT_DETAIL_DELETE_ALLOW";//只允许操作类型为退款的数据才能进行该操作
    String PAYMENT_DETAIL_PASS_ALLOW = "PAYMENT_DETAIL_PASS_ALLOW";//只允许对状态为复核中的数据进行复核
    String PAYMENT_DETAIL_REJECT_ALLOW = "PAYMENT_DETAIL_REJECT_ALLOW";//只允许对状态为复核中的数据进行驳回
    String PAYMENT_DETAIL_NOT_REFUND = "PAYMENT_DETAIL_NOT_REFUND";//该数据已经进行退款或者反冲，不允许退款
    String PAYMENT_PAY_REFUND_SUBMITED_ERROR = "PAYMENT_PAY_REFUND_SUBMITED_ERROR";//该数据正在进行反冲，不允许进行退款操作

    /*支付反冲*/
    String PAYMENT_NOT_ALLOW_BACKLASH = "PAYMENT_NOT_ALLOW_BACKLASH";//已有数据反冲或退款，不允许反冲
    String PAYMENT_OID_IS_NOT_NULL = "PAYMENT_OID_IS_NOT_NULL";// 单据OID和实体类型不能为空
    String PAYMENT_LOGS_CREATE_FAILURE = "PAYMENT_LOGS_CREATE_FAILURE";// 创建支付日志记录失败
    String PAYMENT_LOGS_DELETE_FAILURE = "PAYMENT_LOGS_DELETE_FAILURE";// 删除支付日志记录失败
    String PAYMENT_DRAWEE_NAME_FOUND_ERROR = "PAYMENT_DRAWEE_NAME_FOUND_ERROR";//获取付款出纳信息异常
    String PAYMENT_BACK_FLASH_AMOUT_GREATER_NORMAL_AMOUNT = "PAYMENT_BACK_FLASH_AMOUT_GREATER_NORMAL_AMOUNT";//本次反冲金额大于可反冲金额

    /**
     * 付款申请单
     */
    String PAYMENT_ACP_ACP_REQUISITION_NOT_EXISTS = "PAYMENT_ACP_ACP_REQUISITION_NOT_EXISTS";//付款申请单不存在
    String PAYMENT_ACP_ACP_REQUISITION_OPERATE = "PAYMENT_ACP_ACP_REQUISITION_OPERATE";//只允许对状态为{}的付款申请单进行{}
    String PAYMENT_ACP_ACP_REQUISITION_TYPE_EXISTS = "PAYMENT_ACP_ACP_REQUISITION_TYPE_EXISTS";//同一账套下，同一付款申请单类型代码不运行重复
    String PAYMENT_ACP_REQUISITION_EXPREPORT_NOT_EXISTS = "PAYMENT_ACP_REQUISITION_EXPREPORT_NOT_EXISTS";//关联的报账单行信息不存在
    String PAYMENT_ACP_REQUISITION_CONTRACT_NOT_EXISTS = "PAYMENT_ACP_REQUISITION_CONTRACT_NOT_EXISTS"; // 关联的报账单所关联的合同信息不存在
    String PAYMENT_ACP_EXP_REPORT_RELATION_AMOUNT_SMALL = "PAYMENT_ACP_EXP_REPORT_RELATION_AMOUNT_SMALL";//本次关联金额必须大于0
    String PAYMENT_ACP_CASH_DATA_NOT_UNIQUE = "PAYMENT_ACP_CASH_DATA_NOT_UNIQUE";//关联的报账单行信息在通用支付数据内存在多条记录
    String PAYMENT_ACP_RELATION_AMOUNT_TOO_BIG = "PAYMENT_ACP_RELATION_AMOUNT_TOO_BIG";// 关联金额超过计划付款行总金额

    /**
     * 核销相关
     */
    String PAYMENT_WRITE_OFF_AMOUNT_GT_UNWRITE = "PAYMENT_WRITE_OFF_AMOUNT_GT_UNWRITE";     //核销金额超过未核销金额
    String PAYMENT_WRITE_OFF_AMOUNT_GT_DOUCUMENT_AMOUNT = "PAYMENT_WRITE_OFF_AMOUNT_GT_DOUCUMENT_AMOUNT";  //核销金额超过计划付款行金额
    String PAYMENT_WRITE_OFF_MODULE_RETURN_PAYMENT_FAIL = "PAYMENT_WRITE_OFF_MODULE_RETURN_PAYMENT_FAIL";   //核销平台回写支付平台失败
    String PAYMENT_WRITE_OFF_AMOUNT_GT_UN_WRITE_OFF_AMOUNT = "PAYMENT_WRITE_OFF_AMOUNT_GT_UN_WRITE_OFF_AMOUNT";  //核销金额不得大于未核销金额!
    String PAYMENT_WRITE_OFF_PREPAYMENT_REQUISITION_NOT_FOUND = "PAYMENT_WRITE_OFF_PREPAYMENT_REQUISITION_NOT_FOUND";  //核销数据生成凭证分录失败，未获取到预付款单相关信息
    String PAYMENT_WRITE_OFF_PUBLIC_EXPORT_NOT_FOUND = "PAYMENT_WRITE_OFF_PUBLIC_EXPORT_NOT_FOUND";  //核销数据生成凭证分录失败，未获取到报账单相关信息!
    String PAYMENT_WRITE_OFF_REVERSE_AMOUNT_TOO_BIG = "PAYMENT_WRITE_OFF_REVERSE_AMOUNT_TOO_BIG";    //本次核销反冲金额不得大于可核销反冲金额!
    String PAYMENT_WRITE_OFF_REVERSE_REPETITIVE_OPERATION = "PAYMENT_WRITE_OFF_REVERSE_REPETITIVE_OPERATION";    //请勿重复操作!
    String PAYMENT_WRITE_OFF_REVERSE_ERROR = "PAYMENT_WRITE_OFF_REVERSE_ERROR";    //只能作废拒绝的反冲记录!
    String PAYMENT_WRITE_OFF_PLEASE_CREATE_ACCOUNT = "PAYMENT_WRITE_OFF_PLEASE_CREATE_ACCOUNT";     //请先生成凭证!
    String PAYMENT_WRITE_OFF_TRY_APPROVED_AGAIN = "PAYMENT_WRITE_OFF_TRY_APPROVED_AGAIN";        //单据已审核，请勿重复操作!
    String PAYMENT_WRITE_OFF_CANNOT_DELETE = "PAYMENT_WRITE_OFF_CANNOT_DELETE";        //该核销记录不允许删除！

    /**
     * 锁
     **/
    String PAYMENT_ERROR_LOCKKEY_FAILURE = "PAYMENT_ERROR_LOCKKEY_FAILURE";// 获取lockKey 失败，确保Object能拿到唯一ID

    String PAYMENT_ERROR_CALLBACK_FAILURE = "PAYMENT_ERROR_CALLBACK_FAILURE";// 回调函数执行异常

    String PAYMENT_ERROR_GET_LOCKKEY_FAILURE = "PAYMENT_ERROR_GET_LOCKKEY_FAILURE"; // 资源已被加锁

    String PAYMENT_ERROR_LOCK_FAILURE = "PAYMENT_ERROR_LOCK_FAILURE";// 加锁失败

    /**
     * 核销状态相关
     */
    String WRITE_OFF_STATUS_N = "write.off.status.n";
    String WRITE_OFF_STATUS_P = "write.off.status.p";
    String WRITE_OFF_STATUS_Y = "write.off.status.y";
    String WRITE_OFF_RESERVED_STATUS_N = "write.off.reserved.status.n";
    String WRITE_OFF_RESERVED_STATUS_P = "write.off.reserved.status.p";
    String WRITE_OFF_RESERVED_STATUS_Y = "write.off.reserved.status.y";
    String WRITE_OFF_RESERVED_APPROVED = "write.off.reserved.approved";
    String WRITE_OFF_RESERVED_REJECTED = "write.off.reserved.rejected";

    //付款公司迁移过来的

    String PAYMENT_PRIORITY_ALREADY_EXISTS = "PAYMENT_PRIORITY_ALREADY_EXISTS";//优先级已经存在
    String PAYMENT_PRIORITIES_ARE_NOT_MODIFIABLE = "PAYMENT_PRIORITIES_ARE_NOT_MODIFIABLE";//优先级不可修改
    String PAYMENT_DOCUMENT_TYPE_NO_DATA_FOUND = "PAYMENT_DOCUMENT_TYPE_NO_DATA_FOUND";//单据类型没有找到

    String COMPANY_BANK_NOT_FOUND = "COMPANY_BANK_NOT_FOUND";//公司银行账户不存在
    String COMPANY_BANK_CODE_OR_NUM = "COMPANY_BANK_CODE_OR_NUM";//公司银行账户账号重复
    String COMPANY_BANK_CODE_OR_NUM_LENGTH_MORE_THEN_LIMIT = "COMPANY_BANK_CODE_OR_NUM_LENGTH_MORE_THEN_LIMIT";//公司银行账户名称或银行账户账号长度超限制
    String ACCOUNT_NUMBER_IS_ALLOWED_TO_ENTER_LETTERS_OR_NUMBERS = "ACCOUNT_NUMBER_IS_ALLOWED_TO_ENTER_LETTERS_OR_NUMBERS";//只允许帐号输入字母或数字

    /*公司公司银行付款方式
     * */
    String PAYMENT_METHOD_NOT_FOUNT = "PAYMENT_METHOD_NOT_FOUNT";//公司银行付款方式没找到
    String PAYMENT_METHOD_EXIT = "PAYMENT_METHOD_EXIT";//该银行账户已存在该付款方式
    String COMPANY_BANK_AUTH_EXIT = "COMPANY_BANK_AUTH_EXIT";
    String UPDATE_SIZE_TOO_BIG = "UPDATE_SIZE_TOO_BIG";//只允许更新一条

    String CHINESE_CHARACTERS_ARE_NOT_ALLOWED = "CHINESE_CHARACTERS_ARE_NOT_ALLOWED";
    String ENCODING_CANNOT_CONTAIN_ILLEGAL_CHARACTERS = "ENCODING_CANNOT_CONTAIN_ILLEGAL_CHARACTERS";
    String ENCODING_LENGTH_SHOULD_NOT_EXCEED_36_BITS = "ENCODING_LENGTH_SHOULD_NOT_EXCEED_36_BITS";

    String PAYMENT_TRANSFER_FUND_SERVICE_ERROR = "PAYMENT_TRANSFER_FUND_SERVICE_ERROR";//调用资金模块发生错误！
}
