package com.helioscloud.atlantis.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.cloudhelios.atlantis.domain.VersionDomainObject;
import lombok.Data;

/**
 * Created by houyin.zhang@hand-china.com on 2018/8/21.
 * 语言启动记录表，只要启用，则该表会插入一条数据，并是enabled状态的
 */
@Data
@TableName("sys_language_enabled")
public class LanguageEnabled extends VersionDomainObject {
    @TableField("language")
    private String language;// 中文 zh_CN 英文 eu

}
