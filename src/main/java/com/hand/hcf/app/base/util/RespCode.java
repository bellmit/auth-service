package com.hand.hcf.app.base.util;

/**
 * Created by houyin.zhang@hand-china.com on 2018/8/13.
 */
public interface RespCode extends com.hand.hcf.app.core.util.RespCode {
    String ROLE_CODE_NULL = "ROLE_CODE_NULL";//角色代码不允许为空
    String ROLE_NAME_NULL = "ROLE_NAME_NULL";// 角色名称不允许为空
    String CODE_NOT_UNION_IN_TENANT = "10012";// 当前租户已存在该角色代码
    String SYS_ID_NULL = "SYS_ID_NULL"; //id 不应该为空!
    String SYS_ID_NOT_NULL = "SYS_ID_NOT_NULL"; //ID 应该为空
    String SYS_DATASOURCE_CANNOT_FIND_OBJECT = "SYS_DATASOURCE_CANNOT_FIND_OBJECT"; //数据库不存在
    String SYS_READ_FILE_ERROR = "SYS_READ_FILE_ERROR"; // 读取文件异常 to
    String SYS_LOV_NOT_EXISTS = "SYS_LOV_NOT_EXISTS"; // lov不存在！

    String APPLICATION_CODE_NOT_BE_NULL = "APPLICATION_CODE_NOT_BE_NULL";// 应用代码不允许为空
    String APPLICATION_NAME_NOT_BE_NULL = "APPLICATION_NAME_NOT_BE_NULL";// 应用名称不允许为空

    String APPLICATION_CODE_EXISTS = "APPLICATION_CODE_EXISTS";// 应用代码已经存在
    String APPLICATION_NAME_EXISTS = "APPLICATION_NAME_EXISTS";// 应用名称已经存在


    String LOV_CODE_NOT_BE_NULL = "LOV_CODE_NOT_BE_NULL";// lov代码不允许为空
    String LOV_NAME_NOT_BE_NULL = "LOV_NAME_NOT_BE_NULL";// lov名称不允许为空

    String LOV_CODE_EXISTS = "LOV_CODE_EXISTS";// lov代码已经存在
    String LOV_NAME_EXISTS = "LOV_NAME_EXISTS";// lov名称已经存在



    String MENU_CODE_NULL = "MENU_CODE_NULL";//菜单代码不允许为空
    String MENU_NAME_NULL = "MENU_NAME_NULL";// 菜单名称不允许为空
    String CODE_NOT_UNION = "10022";// 菜单代码已经存在
    String HAVING_CHILD_MENU = "10023";// 菜单代码已经存在
    String MENU_FUNCTION_PARENT_MUST_BE_CATALOG = "MENU_FUNCTION_PARENT_MUST_BE_CATALOG";// 功能的上级菜单必须是目录
    String MENU_PARENT_CATALOG_ERROR = "10025";//功能只能添加到最底级的目录
    String ROOT_CATALOG_MUST_BE_CATALOG = "10026";//功能只能添加到最底级的目录
    String HAS_CHILD_CATALOG_CAN_NOT_BE_FUNCTION = "10027";//当前菜单存在子目录，不允许由目录变更为功能

    String USER_ROLE_EXISTS = "USER_ROLE_EXISTS"; // 用户角色组合已经存在
    String ROLE_MENU_EXISTS = "ROLE_MENU_EXISTS"; // 角色菜单组合已经存在

    String BUTTON_CODE_NULL = "BUTTON_CODE_NULL";//按钮代码不允许为空
    String BUTTON_CODE_NOT_UNION = "10051";// 按钮代码在菜单中已经存在
    String BUTTON_NAME_NULL = "BUTTON_NAME_NULL";//按钮名称不允许为空

    String ROLE_MENU_BUTTON_EXISTS = "10061"; // 角色菜单按钮组合已经存在

    String COMPONENT_NAME_NULL = "10071";// 组件名称不允许为空
    String COMPONENT_TYPE_INVALID = "10072";// 组件类型值无效,只能为1或2.

    String MODULE_CODE_NULL = "10081";// 模块代码不允许为空
    String MODULE_NAME_NULL = "10082";// 模块名称不允许为空
    String MODULE_CODE_NOT_UNION = "10083";// 模块代码已经存在

    String AUTH_FRONT_KEY_NULL = "AUTH_FRONT_KEY_NULL";// Key不允许为空
    String AUTH_MODULE_ID_NULL = "AUTH_MODULE_ID_NULL";// 必须关联模块
    String AUTH_FRONT_KEY_NOT_UNION = "AUTH_FRONT_KEY_NOT_UNION";// KEY必须唯一

    String REQUEST_CODE_NULL = "AUTH_REQUEST_CODE_NULL";// 请求名称不允许为空
    String REQUEST_INTERFACE_NULL = "AUTH_REQUEST_INTERFACE_NULL";// 请求必须关联接口

    String RESPONSE_CODE_NULL = "10120";// 响应名称不允许为空
    String RESPONSE_INTERFACE_NULL = "10121";// 响应必须关联接口

    String LANGUAGE_CODE_NULL = "AUTH_LANGUAGE_CODE_NULL";// 语言代码不允许为空
    String LANGUAGE_NAME_NULL = "AUTH_LANGUAGE_NAME_NULL";// 语言名称不允许为空
    String LANGUAGE_CODE_NOT_UNION = "AUTH_LANGUAGE_CODE_NOT_UNION";// 语言代码已经存在

    String ERROR_CODE_NULL = "AUTH_ERROR_CODE_NULL";// 报错代码不允许为空
    String ERROR_MESSAGE_NULL = "AUTH_ERROR_MESSAGE_NULL";// 报错信息不允许为空

    String MOBILE_VALIDATE_DUPLICATED_CODE = "220001";
    String MOBILE_VALIDATE_FORMAT_ERROR = "220002";

    String CODE_CANT_CHINESE="CODE_CANT_CHINESE";//编码不能包含汉字
    String CODE_CANT_ILLEGAL="CODE_CANT_ILLEGAL";//编码不能包含非法字符
    String CODE_LENGTH_LT_36="CODE_LENGTH_LT_36";//编码长度不能超过36位
    String CODE_CANT_MODIFIED="CODE_CANT_MODIFIED";//编码不允许修改


    String TENANT_NOT_EXIST = "TENANT_NOT_EXIST"; //租户不存在
    String TENANT_NAME_NULL = "TENANT_NAME_NULL"; //租户名称不能为空
    String TENANT_PROTOCOL_TITLE_TO0_LONG = "6060001";//自定义协议标题不能为空或超过50个字符
    String TENANT_PROTOCOL_CONTENT_TO0_LONG = "6060002";//自定义协议内容不能为空或超过10000个字符
    String TENANT_MULTI_PROTOCOL = "6060003"; //一个租户包含多个自定义协议
    String TENANT_ADMIN_PASSWORD_IS_ERROR = "TENANT_ADMIN_PASSWORD_IS_ERROR"; // 租户管理员两次密码输入不一致

    /**
     * 账户操作
     */
    String USER_ACTIVATION_TOKEN_NOT_EXISTS = "6052001";
    String USER_ACTIVATION_TOKEN_EXPIRED = "6052002";
    String USER_PASS_LENGTH_WRONG = "USER_PASS_LENGTH_WRONG";
    String USER_PASS_FORMAT_WRONG = "USER_PASS_FORMAT_WRONG";
    String USER_PASS_REPAT_WRONG = "USER_PASS_REPAT_WRONG";

    String USER_PASS_NOT_ALLOW_RESET = "USER_PASS_NOT_ALLOW_RESET";
    String ACCOUNT_6052006 = "6052006";
    String ACCOUNT_6052007 = "6052007";
    String ACCOUNT_6052008 = "6052008";
    String ACCOUNT_6052009 = "6052009";
    String ACCOUNT_6052010 = "6052010";
    String USER_OLD_PASS_WRONG = "USER_OLD_PASS_WRONG";
    String ACCOUNT_6052012 = "6052012";
    String ACCOUNT_6052013 = "6052013";
    String ACCOUNT_6052014 = "6052014";
    String ACCOUNT_6052015 = "6052015";
    String ACCOUNT_6052016 = "6052016";
    String ACCOUNT_6052017 = "6052017";
    String ACCOUNT_6052018 = "6052018";
    String ACCOUNT_6052019 = "6052019";

    String SYS_USER_LANG_IS_NULL="SYS_USER_LANG_IS_NULL";//用户语言不能为空
    String USER_NOT_EXIST = "USER_NOT_EXIST";
    String USER_NOT_ACTIVATE = "USER_NOT_ACTIVATE";//用户未激活
    String USER_ALREADY_ACTIVATED = "USER_ALREADY_ACTIVATED";

    String USER_LOGIN_NOT_NULL="USER_LOGIN_NOT_NULL";//用户名不能为空
    String USER_LOGIN_EXISTS="USER_LOGIN_EXISTS";//用户名已经存在
    String USER_NAME_NOT_NULL="USER_NAME_NOT_NULL";//用户名称不能为空
    String USER_ID_MUST_NULL="USER_ID_MUST_NULL";//用户ID必须为空
    String USER_ID_NOT_NULL="USER_ID_NOT_NULL";//用户ID不能为空
    String USER_OID_MUST_NULL="USER_OID_MUST_NULL";//用户OID必须为空
    String USER_OID_NOT_NULL="USER_OID_NOT_NULL";//用户OID不能为空
    String EMAIL_IS_NULL = "EMAIL_IS_NULL";//邮箱为空

    String EMAIL_FORMAT_ERROR = "EMAIL_FORMAT_ERROR";//邮箱格式错误
    String USER_EMAIL_EXISTS = "USER_EMAIL_EXISTS";
    String USER_EMAIL_NOT_EXISTS = "USER_EMAIL_NOT_EXISTS";
    String USER_MOBILE_EXISTS = "USER_MOBILE_EXISTS";


    String USER_SEND_SMS_TOO_MANY = "USER_SEND_SMS_TOO_MANY";



    //系统代码
    String SYS_CODE_CODE_IS_NULL = "SYS_CODE_CODE_IS_NULL"; // 系统代码的代码标识不允许为空
    String SYS_CODE_TYPE_NOT_ALLOW_UPDATE = "SYS_CODE_TYPE_NOT_ALLOW_UPDATE"; // 系统代码的类型不允许更新
    String SYS_CODE_NOT_EXISTS = "SYS_CODE_NOT_EXISTS"; // 系统代码不存在
    String SYS_CODE_CODE_IS_EXISTS = "SYS_CODE_CODE_IS_EXISTS"; // 系统代码的代码标识已经存在！
    String SYS_CODE_VALE_CODE_IS_EXISTS = "SYS_CODE_VALE_CODE_IS_EXISTS"; // 系统代码的值代码已经存在


    String SYS_READ_FILE_FAILED = "SYS_READ_FILE_FAILED";    //读取文件失败

    /**
     * 编码规则
     */
    String ID_NOT_ALLOWED_21001 = "21001";   //创建数据不允许有ID
    String ID_REQUIRED_21002 = "21002";   //更新数据ID必填
    String BUDGET_CODING_RULE_DETAIL_OPERATION = "6013022";//启用中的编码规则下的明细不能操作编码规则定义无应用公司时的固定字符值必须唯一!
    String BUDGET_CODING_RULE_IS_USED = "6013005";//编码规则已被引用!
    String BUDGET_CODING_RULE_OBJECT_CODE_NOT_UNIQUE = "6013001";//同一租户下的单据类别、单据类型、公司代码的组合只能有一个!
    String BUDGET_CODING_RULE_ONE_ENABLED = "6013003";//只能有一条启用的编码规则!
    String BUDGET_CODING_RULE_CODE_NOT_UNIQUE = "6013004";//同一编码规则定义下的编码规则代码不能重复!
    String BUDGET_CODING_RULE_ENABLED_EXCEPTION = "6013006";//编码规则启用多个或没有!
    String BUDGET_CODING_RULE_DETAIL_SEQUENCE_NOT_UNIQUE = "6013007";//同一编码规则下的序号不唯一!
    String BUDGET_CODING_RULE_DETAIL_NOT_FOUND = "6013008";//该编码规则下无编码规则明细！
    String BUDGET_CODING_RULE_DETAIL_SEQUENCE_NOT_FOUND = "6013012";//规则明细中不存在序列号，请先添加!
    String BUDGET_CODING_NOT_FOUND = "6013016";//该数据不存在
    String BUDGET_CODING_RULE_DETAIL_DATE_FORMAT_NOT_FOUND = "6013017";//该重置频率下编码规则明细必须有日期格式!
    String BUDGET_CODING_RULE_DETAIL_SEGMENT_TYPE_NOT_UNIQUE = "6013018";//同一编码规则下的参数名称不能重复!
    String BUDGET_CODING_RULE_DETAIL_SEGMENT_TYPE_40 = "6013019";//编码规则定义有应用公司必须建立一条公司代码明细!
    String BUDGET_CODING_RULE_DETAIL_SYNTHESIS_NOT_UNIQUE = "6013021";//当前租户下该编码规则生成出来的编号与其它编号重复!
    String BUDGET_CODING_RULE_DETAILSYNTHESIS = "6013023";//预期生成出来的编码规则号过长，请修改!
    String BUDGET_CODING_RULE_DETAIL_COMPANY_CODE = "6013024";//编码规则定义无应用公司时不能使用公司代码！
    String BUDGET_CODING_RULE_DETAIL_DATE_FORMAT_YEAR = "6013025";//为防止单号重复,每年频率下日期格式必须有年！
    String BUDGET_CODING_RULE_DETAIL_DATE_FORMAT_MONTH = "6013026";//为防止单号重复,每月频率下日期格式必须有年加月！
    String BUDGET_CODING_RULE_VALUE_COMPANY_CODE_NOT_FOUND = "6013014";//公司代码不能为空!
    String BUDGET_CODING_RULE_VALUE_DOCUMENT_TYPE_NOT_FOUND = "6013015";//单据类型代码不能为空!
    String BUDGET_CODING_RULE_OPERATION_DATE_FORMAT_EXCEPTION = "6013009";//日期格式为yyy-MM-dd!
    String BUDGET_CODING_RULE_OBJECT_NOT_ENABLED = "6013002";//没有启用的编码规则定义!
    String BUDGET_CODING_RULE_DATE_FORMAT_EXCEPTION = "6013010";//规则明细中日期参数格式不正确！
    String BUDGET_CODING_RULE_CURRENT_VALUE_OVERFLOW = "6013011";//序列号位数溢出!
    String BUDGET_CODING_RULE_ORDER_NUMBER_LENGTH_NO_MORE_THAN_30 = "6013013";//单据编号不能超过30!

    String DataFilteringUtil_29001 = "6029001";
    String DataFilteringUtil_29002 = "6029002";
    String DataFilteringUtil_29003 = "6029003";
    String DataFilteringUtil_29004 = "6029004";



    //数据权限
    String AUTH_DATA_AUTHORITY_CITED = "AUTH_DATA_AUTHORITY_CITED";        //该数据权限已被引用，不可删除！
    String AUTH_DATA_AUTHORITY_RULE_EXISTS = "AUTH_DATA_AUTHORITY_RULE_EXISTS";  //同一权限下，规则名称不能重复！
    String AUTH_DATA_AUTHORITY_RULE_DETAIL_VALUE_NONE = "AUTH_DATA_AUTHORITY_RULE_DETAIL_VALUE_NONE";   //数据权限为手工选择时，请至少选择一条数据！
    String AUTH_DATA_AUTHORITY_RULE_DETAIL_EXISTS = "AUTH_DATA_AUTHORITY_RULE_DETAIL_EXISTS";   //数据权限规则数据类型已存在，请勿重复保存！
    String AUTH_DATA_AUTHORITY_EXISTS = "AUTH_DATA_AUTHORITY_EXISTS";   //数据权限已存在，请勿重复保存！

    //数据权限参数配置
    String AUTH_DATA_AUTH_TABLE_PROPERTY_DATA_TYPE_EXISTS = "AUTH_DATA_AUTH_TABLE_PROPERTY_DATA_TYPE_EXISTS";//此参数类型在该表名下已经存在!
    String AUTH_DATA_AUTH_TABLE_PROPERTY_COLUMN_NAME_EXISTS = "AUTH_DATA_AUTH_TABLE_PROPERTY_COLUMN_NAME_EXISTS";//此参数名称在该表名下已经存在!

    //页面
    String PAGE_LIST_EXIST = "PAGE_LIST_EXIST";//当前页面已经存在!
    String PAGE_LIST_NOT_EXIST = "PAGE_LIST_NOT_EXIST";//当前页面不存在!
    String PAGE_LIST_PAGE_NAME_IS_NULL = "PAGE_LIST_PAGE_NAME_IS_NULL";//页面名称为空!
    String PAGE_LIST_FILE_PATH_IS_NULL = "PAGE_LIST_FILE_PATH_IS_NULL";//页面对应本地文件的地址为空!
    String PAGE_LIST_PAGE_ROUTER_IS_NULL = "PAGE_LIST_PAGE_ROUTER_IS_NULL";//页面路由为空!
    String PAGE_LIST_PAGE_ROUTER_REPEAT = "PAGE_LIST_PAGE_ROUTER_REPEAT";//该页面路由已经存在!
    String PAGE_LIST_PAGE_URL_IS_NULL = "PAGE_LIST_PAGE_URL_IS_NULL";//页面地址为空!
    //功能
    String FUNCTION_LIST_EXIST = "FUNCTION_LIST_EXIST";//当前功能已经存在!
    String FUNCTION_LIST_NOT_EXIST = "FUNCTION_LIST_NOT_EXIST";//当前功能不存在!
    String FUNCTION_LIST_FUNCTION_NAME_IS_NULL = "FUNCTION_LIST_FUNCTION_NAME_IS_NULL";//功能名称为空!
    String FUNCTION_LIST_FUNCTION_ROUTER_IS_NULL = "FUNCTION_LIST_FUNCTION_ROUTER_IS_NULL";//功能路由为空!
    String FUNCTION_LIST_SEQUENCE_NUMBER_IS_NULL = "FUNCTION_LIST_SEQUENCE_NUMBER_IS_NULL";//功能优先级为空!
    String FUNCTION_LIST_FUNCTION_ROUTER_REPEAT = "FUNCTION_LIST_FUNCTION_ROUTER_REPEAT";//该功能路由已经存在!
    String FUNCTION_LIST_APPLICATION_ID_IS_NULL = "FUNCTION_LIST_APPLICATION_ID_IS_NULL";//功能应用ID为空!
    String FUNCTION_LIST_HAVE_BEEN_ALLOCATED_PAGES = "FUNCTION_LIST_HAVE_BEEN_ALLOCATED_PAGES";//该功能已经分配了页面，若想删除该功能，请先取消分配!
    //功能页面关联
    String FUNCTION_PAGE_RELATION_NOT_EXIST = "FUNCTION_PAGE_RELATION_NOT_EXIST";//该功能页面关系不存在!
    String FUNCTION_PAGE_RELATION_EXIST = "FUNCTION_PAGE_RELATION_EXIST";//该功能页面关系已经存在!
    //目录
    String CONTENT_LIST_EXIST = "CONTENT_LIST_EXIST";//当前目录已经存在!
    String CONTENT_LIST_NOT_EXIST = "CONTENT_LIST_NOT_EXIST";//当前目录不存在!
    String CONTENT_LIST_PARENT_CONTENT_NOT_EXIST = "CONTENT_LIST_PARENT_CONTENT_NOT_EXIST";//该目录的上级目录不存在!
    String CONTENT_LIST_CONTENT_NAME_IS_NULL = "CONTENT_LIST_CONTENT_NAME_IS_NULL";//目录名称为空!
    String CONTENT_LIST_CONTENT_ROUTER_IS_NULL = "CONTENT_LIST_CONTENT_ROUTER_IS_NULL";//目录路由为空!
    String CONTENT_LIST_ICON_IS_NULL = "CONTENT_LIST_ICON_IS_NULL";//目录图标为空!
    String CONTENT_LIST_SEQUENCE_NUMBER_IS_NULL = "CONTENT_LIST_SEQUENCE_NUMBER_IS_NULL";//目录优先级为空!
    String CONTENT_LIST_CONTENT_ROUTER_REPEAT = "CONTENT_LIST_CONTENT_ROUTER_REPEAT";//该目录路由已经存在!
    //目录功能关联
    String CONTENT_FUNCTION_RELATION_NOT_EXIST = "CONTENT_FUNCTION_RELATION_NOT_EXIST";//该目录功能关系不存在!
    String CONTENT_FUNCTION_RELATION_EXIST = "CONTENT_FUNCTION_RELATION_EXIST";//该目录功能关系已经存在!

    //角色关联功能
    String ROLE_FUNCTION_NOT_EXIST = "ROLE_FUNCTION_NOT_EXIST";//该角色功能关系不存在!
    String ROLE_FUNCTION_EXIST = "ROLE_FUNCTION_EXIST";//该角色功能关系已经存在!

    //服务端多语言
    String SERVE_LOCALE_NOT_EXIST = "SERVE_LOCALE_NOT_EXIST";//该服务端多语言不存在!
    String SERVE_LOCALE_EXIST = "SERVE_LOCALE_EXIST";//该服务端多语言已经存在!
    String SERVE_LOCALE_KEY_CODE_NOT_ALLOWED_TO_REPEAT = "SERVE_LOCALE_KEY_CODE_NOT_ALLOWED_TO_REPEAT";//服务端多语言界面key值重复!
    //中控多语言
    String FRONT_LOCALE_NOT_EXIST = "FRONT_LOCALE_NOT_EXIST";//该中控多语言不存在!
    String FRONT_LOCALE_EXIST = "FRONT_LOCALE_EXIST";//该中控多语言已经存在!
    String FRONT_LOCALE_KEY_CODE_NOT_ALLOWED_TO_REPEAT = "FRONT_LOCALE_KEY_CODE_NOT_ALLOWED_TO_REPEAT";//中控多语言界面key值重复!
}
