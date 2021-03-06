package com.hand.hcf.app.base.userRole.enums;

import com.hand.hcf.app.core.enums.SysEnum;

/**
 * Flag类型的枚举
 */
public enum FlagEnum implements SysEnum {
    //创建  创建:1001，删除:1002
    CREATE(1001),
    //删除
    DELETE(1002);

    private Integer id;

    FlagEnum(Integer id) {
        this.id = id;
    }

    public static FlagEnum parse(Integer id) {
        for (FlagEnum typeEnum : FlagEnum.values()) {
            if (typeEnum.getId().equals(id)) {
                return typeEnum;
            }
        }
        return null;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
