INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (1,'EXPENSE','费用模块',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (2,'PREPAYMENT','预付款模块',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (3,'PAYMENT','支付模块',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (4,'CONTACT','合同模块',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (5,'BUDGET','预算模块',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (6,'ACCOUNT','核算模块',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (7,'WORKSPACE','工作台模块',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (8,'WORKFLOW','工作流模块',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (9,'BASE','基础设置',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (10,'TEST1','启用模块1',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (11,'TEST2','启用模块2',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (12,'TEST3','禁用模块1',1,1);
INSERT INTO sys_parameter_module (id, module_code, module_name, created_by, last_updated_by) VALUES (13,'TEST4','禁用模块2',1,1);

INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by) VALUES(1,0,'EXPENSE',1,1);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by) VALUES(2,0,'PREPAYMENT',1,1);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by) VALUES(3,0,'PAYMENT',1,1);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by) VALUES(4,0,'CONTACT',1,1);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by) VALUES(5,0,'BUDGET',1,1);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by) VALUES(6,0,'ACCOUNT',1,1);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by) VALUES(7,0,'WORKSPACE',1,1);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by) VALUES(8,0,'WORKFLOW',1,1);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by) VALUES(9,0,'BASE',1,1);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by) VALUES(10,0,'TEST1',1,1);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by) VALUES(11,0,'TEST2',1,1);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by, enabled) VALUES(12,0,'TEST3',1,1,0);
INSERT INTO sys_para_module_status (id, tenant_id, module_code, created_by, last_updated_by, enabled) VALUES(13,0,'TEST4',1,1,0);


INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (1,'ORIGINAL_PERIOD','预算释放在原期间','BGT_REVERSE_PERIOD','0',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (2,'CURRENT_PERIOD','预算释放在当前期间','BGT_REVERSE_PERIOD','1',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (3,'SUBMIT_DATE','单据提交期间','BGT_OCCUPY_DATE','1',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (4,'EXPENSE_DATE','费用发生期间','BGT_OCCUPY_DATE','0',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (5,'BUDGET_CHECK_ERROR','预算校验错误','UNMAPPED_BUDGET_ITEM','0',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (6,'NO_BUDGET_CONTROL','不控制预算','UNMAPPED_BUDGET_ITEM','1',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (7,'ORIGINAL_PERIOD','预算释放在原期间','BGT_CLOSED_PERIOD','0',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (8,'CURRENT_PERIOD','预算释放在当前期间','BGT_CLOSED_PERIOD','1',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (9,'Y','启用数据权限','UNMAPPED_BUDGET_ITEM','1',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (10,'N','不启用数据权限','UNMAPPED_BUDGET_ITEM','0',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (11,'VALUE001','参数0101','CS001','0',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (12,'VALUE002','参数0102','CS001','1',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (13,'VALUE003','参数0201','CS002','0',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (14,'VALUE004','参数0202','CS002','1',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (15,'VALUE005','参数0301','CS003','0',1,1);
INSERT INTO sys_parameter_values (id, parameter_value_code, parameter_value_name, parameter_code, paramete_default_value, created_by, last_updated_by) VALUES (16,'VALUE006','参数0302','CS003','0',1,1);


INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (1,'BGT_REVERSE_PERIOD','单据反冲期间','BUDGET',1,1,'1001',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (2,'BGT_CLOSED_PERIOD','单据关闭期间','BUDGET',1,1,'1001',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (3,'BGT_OCCUPY_DATE','预算占用期间','BUDGET',1,1,'1001',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (4,'UNMAPPED_BUDGET_ITEM','未映射预算项目处理方式','BUDGET',1,1,'1001',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (5,'DATA_AUTHORITY','数据权限启用标志','BASE',0,0,'1001',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (6,'CS001','租户级参数1','TEST1',0,0,'1001',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (7,'CS002','账套级参数1','TEST1',1,0,'1001',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (8,'CS003','公司级参数1','TEST1',0,1,'1001',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (9,'CS004','租户账套级1','TEST1',1,0,'1002',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (10,'CS005','账套公司级1','TEST1',1,1,'1005',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (11,'CS006','租户公司级1','TEST1',0,1,'1003',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (12,'CS007','租户账套公司级1','TEST1',1,1,'1004',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (13,'CS008','租户级参数2','TEST1',0,0,'1002',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (14,'CS009','账套级参数2','TEST1',1,0,'1005',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (15,'CS010','公司级参数2','TEST1',0,1,'1003',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (16,'CS011','租户账套级2','TEST1',1,0,'1004',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (17,'CS012','测试不可见参数1','TEST2',1,1,'1004',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (18,'CS013','禁用参数1','TEST3',0,0,'1002',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (19,'CS014','禁用参数2','TEST3',1,0,'1005',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (20,'CS015','禁用参数3','TEST3',0,1,'1003',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (21,'CS016','禁用参数4','TEST3',1,0,'1004',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (22,'CS017','禁用参数5','TEST3',1,1,'1002',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (23,'CS018','禁用参数6','TEST3',0,1,'1005',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (24,'CS019','禁用参数7','TEST3',1,1,'1003',null,null,null,1,1);
INSERT INTO sys_parameter (id, parameter_code, parameter_name, module_code, sob_parameter, company_parameter, parameter_value_type, api_source_module, api, remark, created_by, last_updated_by) VALUES (25,'CS020','测试不可见参数2','TEST4',1,1,'1004',null,null,null,1,1);
